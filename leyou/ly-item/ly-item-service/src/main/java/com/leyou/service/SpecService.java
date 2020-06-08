package com.leyou.service;

import com.leyou.dao.SpecMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecService {
    @Autowired
    private SpecMapper specMapper;
    @Autowired
    private SpecParamMapper specParamMapper;
    public void addSpec(SpecGroup specGroup) {
        specMapper.insert(specGroup);
    }

    public void update(SpecGroup specGroup) {
        specMapper.update(specGroup);
    }

    public List<SpecGroup> findByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        //根据分类ID查询规格参数组及组内参数列表
        List<SpecGroup> groups = specMapper.select(specGroup);
        groups.forEach(group->{
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(group.getId());
            group.setParams( specParamMapper.select(specParam));

        });
        return groups;
    }

    public void delGroup(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        specMapper.deleteGroup(specGroup);
    }

    public List<SpecParam> findSpecParam(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);

        return specParamMapper.select(specParam);
    }

    public void addSpecParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateSpecParam(specParam);
    }

    public void deleteSpecParam(Long id) {

        specParamMapper.deleteSpecParam(id);
    }

    public List<SpecParam> findSpecParamByCid(Long cid) {
        return specParamMapper.findSpecParamByCid(cid);
    }
    //根据三级分类cid和可搜索条件查询specparam
    public List<SpecParam> findSpecParamByCidAndSearching(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);
        return specParamMapper.select(specParam);
    }

    public List<SpecParam> findParamByCidAndGeneric(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setGeneric(false);
        return specParamMapper.select(specParam);
    }
}
