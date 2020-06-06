package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SkuMapper skuMapper;
    public PageResult<SpuBo> findAll(String key, Integer page, Integer rows, Integer saleable) {
        PageHelper.startPage(page,rows);
        List<SpuBo> list=spuMapper.findAllSup(saleable, key);
        PageInfo<SpuBo> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    public void addGoods(SpuBo spuBo) {
        Date nowDate = new Date();
        //先添加spu
        Spu spu = new Spu();
        spu.setBrandId(spuBo.getBrandId());
        spu.setCid1(spuBo.getCid1());
        spu.setCid2(spuBo.getCid2());
        spu.setCid3(spuBo.getCid3());
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
        spu.setSaleable(false);
        spu.setSubTitle(spuBo.getSubTitle());
        spu.setTitle(spuBo.getTitle());
        spu.setValid(true);
        spuMapper.insert(spu);
        //在添加spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);
        //sku
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku->{
            sku.setCreateTime(nowDate);
            sku.setEnable(true);
            sku.setSpuId(spu.getId());
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
    }

    public SpuDetail findSpuDetailBySpuid(Long spuId) {
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuId);
        return  spuDetailMapper.selectOne(spuDetail);
    }

    public void updateGoods(SpuBo spuBo) {
        Date nowDate = new Date();
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(nowDate);
        spuBo.setSaleable(null);
        spuBo.setValid(null);
        spuMapper.updateByPrimaryKeySelective(spuBo);
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.updateById(spuDetail);
        //先删除再添加
        List<Sku> skus1 = spuBo.getSkus();
        skus1.forEach(sku ->{
            sku.setEnable(false);
            skuMapper.updateByPrimaryKey(sku);

            stockMapper.deleteByPrimaryKey(sku.getId());
        });
        //添加
        //sku
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku->{
            sku.setCreateTime(nowDate);
            sku.setEnable(true);
            sku.setSpuId(spuBo.getId());
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });

    }

    public void deleteGoodsById(Long id) {
        List<Sku> skus = skuMapper.fingSkuBySpuid(id);
        skus.forEach(sku->{
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);

            stockMapper.deleteByPrimaryKey(sku.getId());
        });
        spuDetailMapper.deleteByPrimaryKey(id);
        spuMapper.deleteByPrimaryKey(id);
    }

    public void upOrDown(Integer s, Long id) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(s==1?true:false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    public Spu findSpuById(Long id) {
       return spuMapper.selectByPrimaryKey(id);
    }
}

