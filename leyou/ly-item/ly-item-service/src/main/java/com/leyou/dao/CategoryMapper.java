package com.leyou.dao;

import com.leyou.pojo.Category;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface CategoryMapper extends Mapper<Category> {
    public Category findCate(long id);
}
