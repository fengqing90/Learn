package com.zhss.data.refill.center.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("data-refill-center-finance")
public interface AccountAmountService extends AccountAmountApi {

}
