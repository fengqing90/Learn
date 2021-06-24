package learn.servlet3.整合spring;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/21 16:15
 */

// 该配置类相当于Spring的配置文件
// Spring容器不扫描Controller，它是一个父容器

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@ComponentScan(value = "learn.servlet3.整合spring",
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = { Controller.class }) })
public class RootConfig {
}
