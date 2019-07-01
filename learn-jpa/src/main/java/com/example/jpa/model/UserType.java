package com.example.jpa.model;

/**
 * Created by zhoujiuping on 2016/12/7.
 */
public enum UserType {

    DEB_COM {
        @Override
        public String toString() {
            return "复式记账公司用户";
        }
    },
    SYSTEM {
        @Override
        public String toString() {
            return "系统用户";
        }
    },
    COMMON {
        @Override
        public String toString() {
            return "普通用户";
        }
    };

    @Override
    public abstract String toString();

}
