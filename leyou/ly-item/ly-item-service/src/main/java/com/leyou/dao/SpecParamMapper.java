package com.leyou.dao;


import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
@Component
public interface SpecParamMapper extends Mapper<SpecParam> {
    void deleteSpecParam(Long id);

    void updateSpecParam(SpecParam specParam);
    @Select("SELECT * FROM tb_spec_param p,tb_category c WHERE p.cid=c.id AND c.id=#{cid}")
    List<SpecParam> findSpecParamByCid(Long cid);
}
