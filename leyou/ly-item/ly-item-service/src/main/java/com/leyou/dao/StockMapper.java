package com.leyou.dao;

import com.leyou.pojo.Stock;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@org.apache.ibatis.annotations.Mapper
@Component
public interface StockMapper extends Mapper<Stock> {
}
