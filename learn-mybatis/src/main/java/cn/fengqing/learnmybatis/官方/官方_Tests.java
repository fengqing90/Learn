package cn.fengqing.learnmybatis.官方;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cn.fengqing.learnmybatis.官方.model.City;
import cn.fengqing.learnmybatis.官方.service.CityService;
import cn.fengqing.learnmybatis.官方.vo.CityVO;

@SpringBootTest
class 官方_Tests {

    @Resource
    private CityService cityService;

    @Test
    void cityService_findByState() {
        System.out.println("***********");
        City cn = cityService.findByState("CN");
        System.out.println(cn.hashCode());
        System.out.println(cn);
    }

    @Test
    void cityService_findAllById() {
        System.out.println("***********");
        CityVO cn = cityService.findAllById(1L);
        System.out.println(cn.hashCode());
        System.out.println(cn);
    }

    @Test
    void cityService_findColumnById() {
        System.out.println("***********");
        CityVO cn = cityService.findColumnById(1L);
        System.out.println(cn.hashCode());
        System.out.println(cn);
    }

    @Test
    void cityService_findById() {
        System.out.println("***********");
        City cn = cityService.findById(1L);
        System.out.println(cn.hashCode());
        System.out.println(cn);
    }

}
