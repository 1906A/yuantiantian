package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface BrandMapper extends tk.mybatis.mapper.common.Mapper<Brand> {

    List<Brand> findAll(@Param("key") String key, @Param("sortBy") String sortBy,@Param("desc") Boolean desc);

    @Insert("insert  into tb_category_brand values(#{cid},#{bid})")
    void addType(Long bid, Long cid);
    @Delete("delete  from tb_category_brand where brand_id=#{id}")
    void deleteCategory(long id);

    void deleteById(long id);
    @Select("SELECT * FROM tb_category_brand t,tb_category c WHERE t.category_id=c.id AND t.brand_id=#{bid}")
    List<Category> findById(long id);

    void update(Brand brand);
    @Select("SELECT b.* FROM tb_category_brand t,tb_brand b WHERE t.brand_id=b.id AND t.category_id=#{cid}")
    List<Brand> findBrandByCid(Long cid);
    @Select("select * from tb_brand where id=#{id}")
    Brand findBrandById(Long id);
}
