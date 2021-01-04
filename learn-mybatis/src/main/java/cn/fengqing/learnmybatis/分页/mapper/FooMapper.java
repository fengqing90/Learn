package cn.fengqing.learnmybatis.分页.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/30 11:23
 */
@Mapper
public interface FooMapper {

    // @Select("select * from foo limit #{limit}")
    // Cursor<Foo> scan(@Param("limit") int limit);
}