package cn.fq.product.services;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cn.fq.common.annotation.DataSource;
import cn.fq.common.bean.entity.SysUser;
import cn.fq.common.enums.DataSourceType;
import cn.fq.product.mapper.SysRoleMapper;
import cn.fq.product.mapper.SysUserMapper;
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
    @Resource
    private SysRoleMapper sysRoleMapper;

    @DataSource(DataSourceType.OAUTH2)
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.info("【登录】[{}]....", username);

        // 用户
        SysUser user = this.sysUserMapper.findByUsername(username);
        if (user == null) {
            throw new AuthenticationServiceException(
                "username=[" + username + "]，不存在！");
        }

        // 角色
        user.setRoles(this.sysRoleMapper.findByUid(user.getId()));

        //这里密码应该从数据库中取出,暂时先使用加密生成
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        // return new User(username, password, true // 账户是否可用
        //     , true      // 账户是否过期 
        //     , true      // 密码是否过期
        //     , true      // 账户是否锁定
        //     , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

        // return new MyOauthUser(username, password);
        return user;
    }
}
