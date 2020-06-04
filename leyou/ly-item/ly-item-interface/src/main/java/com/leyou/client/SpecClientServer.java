package com.leyou.client;

import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecClientServer {
    @RequestMapping(value = "spec/specparamsByCid",method = RequestMethod.GET)
    public List<SpecParam> findSpecParamByCidAndSearching(@RequestParam(value = "cid") Long cid);
}
