package cn.fengqing.learnmybatis.plus.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.fengqing.learnmybatis.model.User;

/**
 * // @Mapper 貌似可以代替：
 * // @Repository
 * // @MapperScan("cn.fengqing.learnmybatis.plus.mapper")
 *
 * @author fengqing
 * @date 2020/12/23 15:29
 */
// 在对应的Mapper上面继承基本的类 BaseMapper
// 代表持久层

// @Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    // 所有的CRUD操作都已经编写完成了
}