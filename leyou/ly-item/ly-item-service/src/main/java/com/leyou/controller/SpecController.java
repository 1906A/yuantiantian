package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {
    @Autowired
    SpecService specService;
    @RequestMapping("group")
    public void addSpec(@RequestBody SpecGroup specGroup){
        if(specGroup.getId()!=null){
            specService.update(specGroup);
        }else{
            specService.addSpec(specGroup);
        }

    }
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroup(@PathVariable("cid") Long cid){
        return  specService.findByCid(cid);
    }
    @RequestMapping("group/{id}")
    public void del(@PathVariable("id") Long id){
          specService.delGroup(id);
    }
    @RequestMapping("params")
    public List<SpecParam> findSpecParamByGid(@RequestParam("gid") Long gid){
        return  specService.findSpecParam(gid);
    }
    @RequestMapping("specparams")
    public List<SpecParam> findSpecParamByCid(@RequestParam("cid") Long cid){

        return  specService.findSpecParamByCid(cid);
    }
    @RequestMapping("specparamsByCid")
    public List<SpecParam> findSpecParamByCidAndSearching(@RequestParam("cid") Long cid){

        return  specService.findSpecParamByCidAndSearching(cid);
    }
    @RequestMapping("param")
    public void editSpecParam(@RequestBody SpecParam specParam){
        if(specParam.getId()==null){
            specService.addSpecParam(specParam);
        }else{
            specService.updateSpecParam(specParam);
        }

    }
    @RequestMapping("param/{id}")
    public void deleteSpecParam(@PathVariable("id")Long id){
        specService.deleteSpecParam(id);
    }

}
