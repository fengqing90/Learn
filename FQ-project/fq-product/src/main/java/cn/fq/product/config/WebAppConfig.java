package cn.fq.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/6 21:00
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    /**
     * 添加静态资源访问
     * 
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("**.html")
            .addResourceLocations("classpath:/html/");
    }

}
