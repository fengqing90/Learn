package cn.fq.oauth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.fq.oauth.bean.entity.SysUser;

/**
 * @author fengqing
 * @date 2021/5/8 17:13
 */
@Mapper
public interface SysUserMapper {

    @Select("select * from sys_user where username=#{username}")
    SysUser findByUsername(String username);
}
