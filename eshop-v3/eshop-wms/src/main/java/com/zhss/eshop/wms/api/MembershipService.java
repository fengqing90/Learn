package com.zhss.eshop.wms.api;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zhss.eshop.membership.api.MembershipApi;

/**
 * 会员服务
 * @author zhonghuashishan
 *
 */
@FeignClient("eshop-membership")
public interface MembershipService extends MembershipApi {

}
