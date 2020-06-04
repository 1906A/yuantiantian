package com.leyou.dao;

import com.leyou.pojo.SpecGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SpecMapper extends tk.mybatis.mapper.common.Mapper<SpecGroup> {
    @Update("update tb_spec_group set cid=#{cid},name=#{name} where id=#{id}")
    void update(SpecGroup specGroup);
    @Delete("delete from tb_spec_group where id=#{id}")
    void deleteGroup(SpecGroup specGroup);
}
