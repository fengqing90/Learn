package cn.fq.oauth.bean.entity;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 权限
 *
 * @author fengqing
 * @date 2021/5/8 16:45
 */
@Data
public class SysRole implements GrantedAuthority {
    private Integer id;
    private String roleName;
    private String roleDesc;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
