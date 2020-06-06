package com.leyou.client;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryClientServer {
    @RequestMapping(value = "category/findCategoryById" ,method = RequestMethod.GET)
    public Category findCategoryById(@RequestParam(value = "id" ,required = false)Long categoryId);
    @RequestMapping(value = "category/findCategoryByCids",method = RequestMethod.GET)
    public List<Category> findCategoryByCids(@RequestBody List<Long> cids);
}
