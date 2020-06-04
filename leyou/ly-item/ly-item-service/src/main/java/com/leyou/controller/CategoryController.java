package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("list")
    @ResponseBody
    public List<Category> list(@RequestParam("pid")Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return categoryService.findCategory(category);
    }
    @RequestMapping("findCategoryById")
    @ResponseBody
    public Category findCategoryById(@RequestParam("id")Long categoryId){

        return categoryService.findCategoryById(categoryId);
    }
    @RequestMapping("show")
    @ResponseBody
    public Category show(){

        return categoryService.findcate();
    }
    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody Category category){
        String result="SUCC";

        try {
            categoryService.add(category);
        }catch (Exception e){
            result="FAIL";
        }
        return result;
    }
    @RequestMapping("edit")
    @ResponseBody
    public String edit(@RequestBody Category category){
        String result="SUCC";
        try {
            categoryService.edit(category);
        }catch (Exception e){
            result="FAIL";
        }
        return result;
    }
    @RequestMapping("del")
    @ResponseBody
    public String del(@RequestBody Category category){
        String result="SUCC";
        try {
            categoryService.del(category);
        }catch (Exception e){
            result="FAIL";
        }
        return result;
    }
}
