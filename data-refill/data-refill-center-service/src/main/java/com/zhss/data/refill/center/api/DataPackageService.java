package com.zhss.data.refill.center.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("data-refill-center-package")
public interface DataPackageService extends DataPackageApi {

}
