package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.feign.BrandClient;
import com.leyou.feign.CategoryClient;
import com.leyou.feign.SpecClient;
import com.leyou.pojo.*;
import com.leyou.repository.GoodsRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;
    @Autowired
    SpecClient specClient;
    @RequestMapping("page")
    public  Object findAll(@RequestBody SearchEntity searchEntity){
        System.out.println(searchEntity.getKey());
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // builder.withQuery(QueryBuilders.matchQuery("all", searchEntity.getKey()).operator(Operator.AND));
        //过滤filter查询条件
        BoolQueryBuilder builder1 = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("all", searchEntity.getKey()).operator(Operator.AND));
        if(searchEntity.getFilter()!=null && searchEntity.getFilter().size()>0){
            searchEntity.getFilter().keySet().forEach(key->{
                String filed="specs."+key+".keyword";
                if(key.equals("分类")){
                    filed="cid3";
                }else if(key.equals("品牌")){
                    filed="brandId";
                }
                builder1.filter(QueryBuilders.termQuery(filed,searchEntity.getFilter().get(key)));
            });
        }
        builder.withQuery(builder1);
        //分页查询
        builder.withPageable(PageRequest.of(searchEntity.getPage()-1,searchEntity.getSize()));
        //根据新品排序
        //builder.withSort(searchEntity.)
        //Page<Goods> page = goodsRepository.search(builder.build());
        // System.out.println(page.getTotalPages());

        String categoryName="categoryName";
        String brandName="brandName";
        builder.addAggregation(AggregationBuilders.terms(categoryName).field("cid3"));//聚合分类
        builder.addAggregation(AggregationBuilders.terms(brandName).field("brandId"));
        AggregatedPage<Goods>search= (AggregatedPage<Goods>) goodsRepository.search(builder.build());
        //构造分类信息 根据ID获取名称
        LongTerms categoryAgg = (LongTerms) search.getAggregation(categoryName);
        List<Category> categoryList = new ArrayList<>();
        categoryAgg.getBuckets().forEach(bucket -> {
            Long categoryId = (Long) bucket.getKey();
            //根据ID查找分类
            Category category = categoryClient.findCategoryById(categoryId);
            categoryList.add(category);
        });
        LongTerms brandAgg = (LongTerms) search.getAggregation(brandName);
        List<Brand> brandList = new ArrayList<>();
        brandAgg.getBuckets().forEach(bucket -> {
            Long brandId = (Long) bucket.getKey();
            //根据ID查找分类
            Brand brand = brandClient.findBrandById(brandId);

            brandList.add(brand);

        });
        //根据三级分类id查询规格参数
        List<Map<String,Object>> paramList = new ArrayList<>();
        //判断分类id不为空
        if (categoryList.size()==1){
            List<SpecParam> specParams = specClient.findSpecParamByCidAndSearching(categoryList.get(0).getId());
            specParams.forEach(specParam -> {
                //要分组的属性值
                String key = specParam.getName();//操作系统、内存、specParam中的name字段
                builder.addAggregation(AggregationBuilders.terms(key).field("specs."+/*"."+*/key+/*"."+*/".keyword"));
            });
            //重新查询返回数据
            AggregatedPage<Goods> search1 = (AggregatedPage<Goods>) goodsRepository.search(builder.build());
            //转换map，去获取桶内属性
            Map<String, Aggregation> aggregationMap = search1.getAggregations().asMap();
            //遍历聚合结果
            aggregationMap.keySet().forEach(mKey -> {

                //把品牌和分类的聚合名称排除掉
                if (!(mKey.equals(categoryName) || mKey.equals(brandName))){
                    //转换数据类型
                    StringTerms aggregation = (StringTerms) aggregationMap.get(mKey);
                    //封装到map对象
                    Map<String,Object> map = new HashMap<>();
                    map.put("key",mKey);
                    List<Map<String,String>> list = new ArrayList<>();
                    aggregation.getBuckets().forEach(bucket -> {
                        Map<String,String> valueMap = new HashMap<>();
                        valueMap.put("name",bucket.getKeyAsString());
                        list.add(valueMap);//对象属性没有id，所以只填写属性，封装到options中为对象
                    });
                    map.put("options",list);
                    //加入到获取参数的list中
                    paramList.add(map);
                }

            });
        }
        SearchResult PageResult = new SearchResult(search.getTotalElements(),  search.getTotalPages(), search.getContent(),categoryList,brandList,paramList);
        //System.out.println(searchEntity.getKey()+"----"+searchEntity.getPage());
        return PageResult;
    }
}
