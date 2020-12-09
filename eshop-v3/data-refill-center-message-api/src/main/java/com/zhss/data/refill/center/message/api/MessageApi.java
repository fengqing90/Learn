package com.zhss.data.refill.center.message.api;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/18 18:21
 */
public interface MessageApi {
    Boolean send(String phoneNumber, String content);
}
