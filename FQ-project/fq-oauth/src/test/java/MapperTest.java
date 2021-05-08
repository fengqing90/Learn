import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cn.fq.oauth.FqOauthApplication;
import cn.fq.oauth.mapper.SysUserMapper;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/8 17:39
 */
@SpringBootTest(classes = FqOauthApplication.class)
public class MapperTest {

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void cityService_findByState() {
        System.out.println(sysUserMapper.findByUsername("user"));
    }
}
