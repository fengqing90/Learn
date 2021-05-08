package cn.fq.oauth.service;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cn.fq.oauth.bean.entity.SysUser;
import cn.fq.oauth.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. 处理用户信息获取逻辑 UserDetailsService
 *
 * @author fengqing
 * @date 2021/5/6 14:45
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    //这里可以注入mapper或者repository的dao对象来实现数据校验逻辑操作
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.info("【登录】[{}]....", username);

        SysUser user = this.sysUserMapper.findByUsername(username);
        if (user == null) {
            throw new AuthenticationServiceException(
                "username=[" + username + "]，不存在！");
        }

        //这里密码应该从数据库中取出,暂时先使用加密生成
        String password = this.passwordEncoder.encode(user.getPassword());

        // return new User(username, password, true // 账户是否可用
        //     , true      // 账户是否过期 
        //     , true      // 密码是否过期
        //     , true      // 账户是否锁定
        //     , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        // return new MyOauthUser(username, password);
        return new SysUser(username, password);
    }
}
