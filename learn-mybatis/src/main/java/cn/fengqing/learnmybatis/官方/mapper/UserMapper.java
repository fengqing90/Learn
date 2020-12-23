package cn.fengqing.learnmybatis.官方.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.fengqing.learnmybatis.官方.model.User;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/23 15:29
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findUserByName(@Param("name") String name);
}
