package com.zhss.data.refill.center.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("data-refill-center-lottery")
public interface LotteryDrawService extends LotteryDrawApi {

}
