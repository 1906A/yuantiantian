package com.leyou.test;

import com.leyou.common.PageResult;
import com.leyou.feign.SpuClient;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SpuBo;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {
    @Autowired
    SpuClient spuClient;
    @Autowired
    GoodsService goodsService;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    GoodsRepository goodsRepository;
    @Test
    public void test1(){
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        PageResult<SpuBo> page = spuClient.findList("", 1, 200,2);
        page.getItems().forEach(spuBo -> {
            System.out.println(spuBo.getId());
            try {
                Goods goods = goodsService.convert(spuBo);
                goodsRepository.save(goods);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }
}
