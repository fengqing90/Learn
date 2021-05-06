package cn.fq.oauth.bean;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * User类是Spring内置的一个类，实现了UserDetails接口，而这个接口是UserDetailSerice的子接口
 *
 * @author fengqing
 * @date 2021/5/6 14:55
 */
public class MyOauthUser extends User {

    public MyOauthUser(String username, String password) {
        super(username, password, true // 账户是否可用
            , true// 账户是否过期 
            , true // 密码是否过期
            , true // 账户是否锁定
            , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
