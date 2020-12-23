package cn.fengqing.learnmybatis.官方.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.fengqing.learnmybatis.官方.model.City;
import cn.fengqing.learnmybatis.官方.vo.CityVO;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/12/23 15:35
 */
@Mapper
public interface CityMapper {

    @Select("SELECT * FROM CITY WHERE state = #{state}")
    City findByState(@Param("state") String state);

    @Select("SELECT * FROM CITY WHERE id = ${id}")
    CityVO findAllById(Long id);

    @Select("SELECT id,name FROM CITY WHERE id = #{id}")
    CityVO findColumnById(Long id);

    City findById(Long id);
}