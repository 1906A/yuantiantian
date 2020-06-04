package com.leyou.dao;

import com.leyou.pojo.SpuDetail;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@Component
@org.apache.ibatis.annotations.Mapper
public interface SpuDetailMapper extends Mapper<SpuDetail> {
    @Update("update tb_spu_detail set description=#{description},generic_spec=#{genericSpec},special_spec=#{specialSpec},packing_list=#{packingList},after_service=#{afterService} where spu_id=#{spuId}")
    void updateById(SpuDetail spuDetail);
}
