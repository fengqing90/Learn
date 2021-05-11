package cn.fq.common.bean;

import java.util.Date;

import lombok.Data;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/11 16:26
 */
@Data
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
