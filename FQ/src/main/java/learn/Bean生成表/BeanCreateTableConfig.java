package learn.Bean生成表;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * https://www.yuque.com/sunchenbin/actable/nfz097
 *
 * @author fengqing
 * @date 2021/6/3 17:40
 */

@Configuration
@MapperScan("com.gitee.sunchenbin.mybatis.actable.dao.*")
@ComponentScan(
        basePackages = { "com.gitee.sunchenbin.mybatis.actable.manager.*" })
public class BeanCreateTableConfig {
}
