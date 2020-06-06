package com.leyou.controller;

import com.leyou.feign.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsDetailController {
    @Autowired
    SpuClient spuClient;
    @Autowired
    SkuClient skuClient;
    @Autowired
    SpecClient specClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;
    @RequestMapping("show")
    public String show(Model model){
        model.addAttribute("name","张三");
        return "hello";
    }
    @RequestMapping("item/{spuId}.html")

    public String showDetail(@PathVariable("spuId")Long spuId, Model model){
        Spu spu = spuClient.findSpuById(spuId);
        model.addAttribute("spu",spu);
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuid(spuId);
        model.addAttribute("spuDetail",spuDetail);
        List<Sku> skus = skuClient.fingSkuBySpuid(spuId);
        model.addAttribute("skus",skus);
        List<SpecGroup> specGroupList = specClient.findSpecGroup(spu.getCid3());
        model.addAttribute("specGroupList",specGroupList);
        List<Category> categoryList = categoryClient.findCategoryByCids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        model.addAttribute("categoryList",categoryList);
        //差尊规格参数
        List<SpecParam> specParamList = specClient.findParamByCidAndGeneric(spu.getCid3());
        //转换格式
        Map<Long, String> paramMap = new HashMap<>();
        specParamList.forEach(specParam -> {
            paramMap.put(specParam.getId(),specParam.getName());
        });
        model.addAttribute("paramMap",paramMap);
        Brand brand = brandClient.findBrandById(spu.getBrandId());
        model.addAttribute("brand",brand);
        return "item";
    }
}
