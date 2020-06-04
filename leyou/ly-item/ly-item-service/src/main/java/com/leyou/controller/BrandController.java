package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @RequestMapping("page")
    public Object findAll(@RequestParam("key") String key,@RequestParam("page") Integer page,
                          @RequestParam("rows") Integer rows,@RequestParam("sortBy")String sortBy,
                          @RequestParam("desc") Boolean desc){
        PageResult<Brand> pageResult= brandService.findAll(key,page,rows,sortBy,desc);
        return pageResult;
    }
    @RequestMapping("addOrEditBrand")
    public void add(Brand brand,@RequestParam("cids")List<Long>cids){
        if(brand.getId()==null){
            brandService.add(brand,cids);
        }else{
            brandService.update(brand,cids);
        }


    }
    @RequestMapping("deleteById/{id}")
    public void del(@PathVariable("id") long id){
        brandService.del(id);
    }
    @RequestMapping("bid/{id}")
    public List<Category> findById(@PathVariable("id") long id){
        return  brandService.findById(id);
    }
    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid") Long cid){
        return brandService.findBrandByCid(cid);
    }

    @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id") Long id){
         Brand brand=brandService.findBrandById(id);

        return brand;
    }
    }
