package com.zhss.eshop.purchase.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.schedule.api.ScheduleApi;

/**
 * 调度服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-schedule")
public interface ScheduleService extends ScheduleApi {

}
