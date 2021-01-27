package cn.fengqing.learnmybatis.generate.model.service.impl;

import cn.fengqing.learnmybatis.generate.model.entity.City;
import cn.fengqing.learnmybatis.generate.model.mapper.CityMapper;
import cn.fengqing.learnmybatis.generate.model.service.CityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fengqing
 * @since 2021-01-19
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

}
