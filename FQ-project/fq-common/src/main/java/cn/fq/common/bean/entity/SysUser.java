package cn.fq.common.bean.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

/**
 * 用户信息
 *
 * @author fengqing
 * @date 2021/5/8 16:48
 */
@Data
public class SysUser implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private List<SysRole> roles = new ArrayList<>();

    public SysUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SysUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /** 账户是否过期 **/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** 账户是否锁定 **/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** 密码是否过期 **/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** 账户是否可用 **/
    @Override
    public boolean isEnabled() {
        return true;
    }
}
