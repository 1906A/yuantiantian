package com.leyou.client;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface CategoryClientServer {
    @RequestMapping(value = "category/findCategoryById" ,method = RequestMethod.GET)
    public Category findCategoryById(@RequestParam(value = "id" ,required = false)Long categoryId);
}
