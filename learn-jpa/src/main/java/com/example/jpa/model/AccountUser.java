package com.example.jpa.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * KEP-TODO
 *
 * @ClassName AccountUser
 * @Author FengQing
 * @Date 2019/6/28 13:44
 */

@Data
@Entity
@Table(name = "account_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(callSuper = true)
public class AccountUser
        extends BaseModel {
    /**
     * 用户Key
     **/
    @Column(name = "user_key", nullable = false, unique = true)
    private String userKey;

    /**
     * 三方业务编号
     **/
    @Column(name = "ref_id", nullable = false)
    private String refId;

    /**
     * 渠道编号
     **/
    @Column(name = "channel_code", nullable = false)
    private String channelCode;

    /**
     * 用户类型
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    /**
     * 备注
     **/
    @Column(name = "remark")
    private String remark;


//    /**
//     * 主键
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    /**
//     * 创建时间
//     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "create_time", nullable = false)
//    private Date createTime;
//
//    /**
//     * 修改时间
//     */
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "update_time", nullable = false)
//    private Date updateTime;
//
//    /**
//     * 版本号
//     */
//    @Version
//    @Column(name = "version", nullable = false)
//    private Integer version;
}
