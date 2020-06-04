package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
@Component
public interface SkuMapper extends Mapper<Sku> {
    @Select("SELECT k.*,t.stock FROM tb_sku k,tb_spu p,tb_stock t WHERE k.spu_id=p.id AND k.id=t.sku_id AND p.id=#{spuId} AND `enable`=1")
    List<Sku> fingSkuBySpuid(Long spuId);
}
