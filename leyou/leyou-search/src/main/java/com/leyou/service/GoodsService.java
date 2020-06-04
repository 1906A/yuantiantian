package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.feign.SkuClient;
import com.leyou.feign.SpecClient;
import com.leyou.feign.SpuClient;
import com.leyou.pojo.*;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
    @Autowired
    SkuClient skuClient;
    @Autowired
    SpecClient specClient;
    @Autowired
    SpuClient spuClient;
    ObjectMapper mapper=new ObjectMapper();
    public  Goods convert(SpuBo spuBo) throws Exception {
        Goods goods = new Goods();
        goods.setId(spuBo.getId());
        goods.setSubTitle(spuBo.getSubTitle());
        goods.setCreateTime(spuBo.getCreateTime());
        goods.setBrandId(spuBo.getBrandId());
        goods.setCid1(spuBo.getCid1());
        goods.setCid2(spuBo.getCid2());
        goods.setCid3(spuBo.getCid3());
        //根据spuid查询sku集合
        List<Sku> skus = skuClient.fingSkuBySpuid(spuBo.getId());
        List<Long> price = new ArrayList<>();
        skus.forEach(sku -> {
            price.add(sku.getPrice());
        });
        goods.setPrice(price);
        String skuList = mapper.writeValueAsString(skus);
        goods.setSkus(skuList);
        //根据三级分类cid和可搜索条件查询specparam
        List<SpecParam> specParamList = specClient.findSpecParamByCidAndSearching(spuBo.getCid3());
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuid(spuBo.getId());
        Map<String, Object> paramMap = new HashMap<>();
        Map<Long, Object> genericSpecMap = mapper.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>() {
        });
        specParamList.forEach(specParam -> {
            if(specParam.getGeneric()){

                String value = genericSpecMap.get(specParam.getId()).toString();
                if (specParam.getNumeric()){
                    value = chooseSegment(value, specParam);
                }
                paramMap.put(specParam.getName(),value);
            }else{
//                Map<Long, Object> genericSpecMap  = null;
//                try {
//                     genericSpecMap = mapper.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, Object>>() {
//                    });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String value = genericSpecMap.get(specParam.getId()).toString();
//
//                paramMap.put(specParam.getName(),value);
                paramMap.put(specParam.getName(), genericSpecMap.get(specParam.getId()));
            }
        });
        goods.setSpecs(paramMap);
        goods.setAll(spuBo.getTitle()+""+spuBo.getCname().replace("/","")+""+spuBo.getBname());
        return goods;
    }
    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
