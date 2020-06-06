package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuBo;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {
    @Autowired
    private SpuService spuService;

    @RequestMapping(value = "page",method = RequestMethod.GET)
    public PageResult<SpuBo> findAll(@RequestParam(value = "key",required = false) String key, @RequestParam(value = "page",required = false) Integer page, @RequestParam(value = "rows",required = false) Integer rows,
                                     @RequestParam(value ="saleable",required = false) Integer saleable){
        PageResult<SpuBo> pageResult= spuService.findAll(key,page,rows,saleable);
        return pageResult;
    }
    @RequestMapping("addOrEditGoods")
    public void addOrEditGoods(@RequestBody SpuBo spuBo) {
    if(spuBo.getId()!=null){
        spuService.updateGoods(spuBo);
    }else {
        spuService.addGoods(spuBo);
    }
    }

    //根据spuid查询detail
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuid(@PathVariable("spuId") Long spuId){
        return spuService.findSpuDetailBySpuid(spuId);

    }
    @RequestMapping("deleteById/{id}")
    public void deleteGoodsById(@PathVariable("id") Long id){
        spuService.deleteGoodsById(id);
    }
    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("id") Long id,@RequestParam("s") Integer s){
        spuService.upOrDown(s,id);
    }
    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("id")Long id){
        return spuService.findSpuById(id);
    }
}
