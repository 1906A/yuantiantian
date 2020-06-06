package com.leyou.client;


import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuBo;
import com.leyou.pojo.SpuDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface SpuClientServer {
    @RequestMapping(value ="/spu/page",method = RequestMethod.GET)
    public PageResult<SpuBo> findList(@RequestParam(value = "key",required = false) String key, @RequestParam(value = "page",required = false) Integer page,
                                      @RequestParam(value = "rows",required = false) Integer rows, @RequestParam(value ="saleable",required = false) Integer saleable);
    @RequestMapping(value = "spu/detail/{spuId}",method = RequestMethod.GET)
    public SpuDetail findSpuDetailBySpuid(@PathVariable(value = "spuId",required = false) Long spuId);
    @RequestMapping(value = "spu/findSpuById",method = RequestMethod.GET)
    public Spu findSpuById(@RequestParam("id")Long id);
}
