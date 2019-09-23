/**
 * Copyright(c) 2011-2017 by YouCredit Inc.
 * All Rights Reserved
 */
package com.example.jpa.services;

import com.example.jpa.model.BaseModel;
import com.example.jpa.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AbstractBaseService
 * @Author FengQing
 * @Date 2019/6/28 13:50
 */
public abstract class AbstractBaseService<T extends BaseModel, ID extends Serializable> implements IBaseService {

    @Autowired
    protected BaseRepository<T> baseRepository;

    protected T save(T entity) {
        Assert.notNull(entity, "对象");

        Date date = new Date();

        entity.setCreateTime(date);
        entity.setUpdateTime(date);

        return this.baseRepository.save(entity);
    }

    protected T update(T entity) {
        Assert.notNull(entity, "对象");
        Assert.notNull(entity.getId(), "主键");

        Date date = new Date();

        entity.setUpdateTime(date);

        return this.baseRepository.save(entity);
    }

    protected List<T> update(List<T> entities) {
        Assert.notNull(entities, "对象");
        return this.baseRepository.save(entities);
    }

    public T get(Long id) {
        Assert.notNull(id, "主键");
        return this.baseRepository.findOne(id);
    }

    public T load(Long id) {
        T entity = this.get(id);
        Assert.isNull(entity, "对象");
        return entity;
    }

    public List<T> findAll() {
        return this.baseRepository.findAll();
    }

    public List<T> findAll(Sort sort) {
        return this.baseRepository.findAll(sort);
    }

}
