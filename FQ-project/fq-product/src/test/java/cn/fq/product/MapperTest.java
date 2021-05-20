package cn.fq.product;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fq.product.mapper.SysUserMapper;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/8 17:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void cityService_findByState() {
        System.out.println(sysUserMapper.findByUsername("user"));
    }
}
