package com.zhss.data.refill.center.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("data-refill-center-activity")  
public interface CouponActivityService extends CouponActivityApi {

}
