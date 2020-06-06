package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Component
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> findCategory(Category category){
        return categoryMapper.select(category);
    }

    public Category findcate() {
        return categoryMapper.findCate(1);

    }


    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    public void edit(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    public void del(Category category) {
        categoryMapper.deleteByPrimaryKey(category);
    }

    public Category findCategoryById(Long categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    public List<Category> findCategoryByCids(List<Long> cids) {
        List<Category> categoryList=new ArrayList<>();
        cids.forEach(cid->{
            Category category = categoryMapper.selectByPrimaryKey(cid);
            categoryList.add(category);
        });
        return categoryList;
    }
}
