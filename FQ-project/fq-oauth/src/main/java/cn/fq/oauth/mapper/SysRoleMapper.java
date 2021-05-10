package cn.fq.oauth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.fq.oauth.bean.entity.SysRole;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/8 16:57
 */
@Mapper
public interface SysRoleMapper {

    @Select("select r.id,r.role_name roleName ,r.role_desc roleDesc "
        + "FROM sys_role r,sys_user_role ur "
        + "WHERE r.id=ur.rid AND ur.uid=#{uid}")
    List<SysRole> findByUid(Integer uid);
}
