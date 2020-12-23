package cn.fengqing.learnmybatis.官方.service;

import java.io.Serializable;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/23 16:06
 */
public abstract class BaseService<T, ID extends Serializable> {

    // @Resource
    // private Mapper<T> mapper;
    //
    // @Transactional
    // public T findById(ID id) {
    //     return this.mapper(id);
    // }
}
