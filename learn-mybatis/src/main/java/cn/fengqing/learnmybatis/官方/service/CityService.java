package cn.fengqing.learnmybatis.官方.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fengqing.learnmybatis.model.City;
import cn.fengqing.learnmybatis.官方.mapper.CityMapper;
import cn.fengqing.learnmybatis.官方.vo.CityVO;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/23 15:36
 */
@Service
public class CityService {

    @Resource
    private CityMapper cityMapper;

    @Transactional
    public City findByState(String state) {
        return this.cityMapper.findByState(state);
    }

    @Transactional
    public CityVO findAllById(Long id) {
        return this.cityMapper.findAllById(id);
    }

    @Transactional
    public CityVO findColumnById(Long id) {
        return this.cityMapper.findColumnById(id);
    }

    @Transactional
    public City findById(Long id) {
        return this.cityMapper.findById(id);
    }

}
