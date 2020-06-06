package com.leyou.feign;

import com.leyou.client.CategoryClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface CategoryClient extends CategoryClientServer {
}