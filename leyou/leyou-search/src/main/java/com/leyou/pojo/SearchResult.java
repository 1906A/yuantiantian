package com.leyou.pojo;

import com.leyou.common.PageResult;

import java.util.List;
import java.util.Map;


public class SearchResult extends PageResult<Goods> {
    private List<Category> categoryList;
    private List<Brand> brandList;
    private  List<Map<String, Object>> paramList;

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categoryList, List<Brand> brandList, List<Map<String, Object>> paramList) {
        super(total, totalPage, items);
        this.categoryList = categoryList;
        this.brandList = brandList;
        this.paramList = paramList;
    }

    public List<Map<String, Object>> getParamList() {
        return paramList;
    }

    public void setParamList(List<Map<String, Object>> paramList) {
        this.paramList = paramList;
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Category> categoryList, List<Brand> brandList) {
        super(total, totalPage, items);
        this.categoryList = categoryList;
        this.brandList = brandList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }


    public SearchResult(Long total, List<Goods> items) {
        super(total, items);
    }

    public SearchResult(Long total) {
        super(total);
    }

    public SearchResult() {
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items) {
        super(total, totalPage, items);
    }
}
