package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Component
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;
    public PageResult<Brand> findAll(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        PageHelper.startPage(page,rows);
        List<Brand>list= brandMapper.findAll(key,sortBy,desc);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(list);
        return new PageResult<>(brandPageInfo.getTotal(),brandPageInfo.getList());
    }

    public void add(Brand brand, List<Long> cids) {
        brandMapper.insert(brand);

        cids.forEach(id->{
            brandMapper.addType(brand.getId(),id);
        });
    }

    public void del(long id) {
        Brand brand = new Brand();
        brand.setId(id);
        brandMapper.deleteById(id);
        brandMapper.deleteCategory(id);
    }

    public List<Category> findById(long id) {
        return brandMapper.findById(id);
    }

    public void update(Brand brand, List<Long> cids) {
        brandMapper.update(brand);
        brandMapper.deleteCategory(brand.getId());
        cids.forEach(id->{
            brandMapper.addType(brand.getId(),id);
        });

    }

    public List<Brand> findBrandByCid(Long cid) {
        return brandMapper.findBrandByCid(cid);
    }

    public Brand findBrandById(Long id) {
        return brandMapper.findBrandById(id);
    }
}
