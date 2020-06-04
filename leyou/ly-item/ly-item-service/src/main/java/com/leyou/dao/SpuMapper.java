package com.leyou.dao;

import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpuMapper extends tk.mybatis.mapper.common.Mapper<Spu> {

    List<SpuBo> findAllSup(@Param("saleable") Integer saleable, @Param("key") String key);
}
