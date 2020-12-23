package cn.fengqing.learnmybatis.官方.model;

import lombok.Data;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/23 15:28
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Integer version;

}
