package com.leyou.client;

import com.leyou.pojo.Brand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface BrandClientServer {
    @RequestMapping(value = "brand/findBrandById",method = RequestMethod.GET)
    public Brand findBrandById(@RequestParam(value = "id" ,required = false) Long id);
}
