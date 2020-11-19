package com.zhss.data.refill.center.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("data-refill-center-order")
public interface RefillOrderService extends RefillOrderApi {

}
