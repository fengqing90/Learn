package cn.fengqing.learnmybatis.分页.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import cn.fengqing.learnmybatis.model.Foo;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/30 11:23
 */
@Mapper
public interface FooMapper {

    @Select("select * from foo limit #{limit}")
    Cursor<Foo> scan(@Param("limit") int limit);
}