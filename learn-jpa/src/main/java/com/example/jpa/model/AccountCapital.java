package com.example.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 账户表
 *
 * @ClassName AccountCapital
 * @Author FengQing
 * @Date 2019/6/25 17:54
 */
@Data
@Entity
@Table(name = "account_capital")
public class AccountCapital extends BaseModel {
    /**
     * 资金账Key
     **/
    private String capitalKey;
    /**
     * 账户类型
     **/
    private String capitalType;
    /**
     * 用户Key
     **/
    private String userKey;
    /**
     * 余额
     **/
    private String balance;
    /**
     * 冻结金额
     **/
    private String frozenBalance;
    /**
     * 主体
     **/
    private String subject;
    /**
     * 阶段
     **/
    private String stage;
    /**
     * 债权人
     **/
    private String creditor;
    /**
     * 资金项（子资金项）
     **/
    private String fundItem;
    /**
     * 资金方
     **/
    private String funder;
}
