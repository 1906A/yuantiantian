package com.leyou.client;

import com.leyou.pojo.Sku;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SkuClientServer {
    @RequestMapping(value = "sku/list",method = RequestMethod.GET)
    public List<Sku> fingSkuBySpuid(@RequestParam(value = "id" ,required = false) Long id);
}
