package learn.Bean生成表;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;

/**
 * https://www.yuque.com/sunchenbin/actable/nfz097
 * https://gitee.com/sunchenbin/mybatis-enhance/tree/master/mybatis-enhance-actable
 * 
 * @author fengqing
 * @date 2021/6/3 17:40
 */

@Configuration
@MapperScan("com.gitee.sunchenbin.mybatis.actable.dao.*")
@ComponentScan(
        basePackages = { "com.gitee.sunchenbin.mybatis.actable.manager.*" })
@EnableAutoConfiguration(exclude = HttpMessageConvertersAutoConfiguration.class)
// @PropertySource(
//         value = "file:C:\\W\\Workspace\\Eclipse Workspace\\Learn\\FQ\\src\\main\\java\\learn\\Bean生成表\\createTable.properties")
// @PropertySource(value = "classpath:/learn/Bean生成表/createTable.properties")
public class BeanCreateTableConfig {

    static class MyAnnotationConfigApplicationContext
            extends AnnotationConfigApplicationContext {
        public MyAnnotationConfigApplicationContext(
                Class<?>... componentClasses) {
            super(componentClasses);
        }

        @Override
        protected void initPropertySources() {
            Map<String, Object> source = new HashMap<>();
            source.put("actable.table.auto", "create");
            source.put("actable.database.type", "mysql");
            source.put("mybatis.mapper-locations",
                "classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml");

            source.put("actable.model.pack", "learn.Bean生成表.stockflow");
            source.put("spring.datasource.url",
                "jdbc:mysql://10.255.33.34:3306/xiaowuxian_platform?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
            source.put("spring.datasource.username", "test");
            source.put("spring.datasource.password", "test@123");
            MapPropertySource propertiesPropertySource = new MapPropertySource(
                "createTable", source);

            this.getEnvironment().getPropertySources()
                .addLast(propertiesPropertySource);
        }
    }

    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext applicationContext = new MyAnnotationConfigApplicationContext(
            BeanCreateTableConfig.class);
    }

    // @Bean
    // @Primary
    // public DataSourceProperties dataSourceProperties(
    //         DataSourceProperties properties)
    //         throws InvocationTargetException, IllegalAccessException {
    //     DataSourceProperties myProperties = new DataSourceProperties();
    //     BeanUtils.copyProperties(myProperties, properties);
    //     myProperties.setUrl(
    //         "jdbc:mysql://10.255.33.34:3306/xiaowuxian_platform?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
    //     myProperties.setUsername("test");
    //     myProperties.setPassword("test@123");
    //     return myProperties;
    // }

    // @Bean
    // public DataSource getDataSource() {
    //     DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    //     dataSourceBuilder.driverClassName("org.h2.Driver");
    //     dataSourceBuilder.url("jdbc:h2:mem:test");
    //     dataSourceBuilder.username("SA");
    //     dataSourceBuilder.password("");
    //     return dataSourceBuilder.build();
    // }
    // @Bean
    // public DataSource dataSource() {
    //     return new EmbeddedDatabaseBuilder().addScript("schema.sql").build();
    // }
    //
    // @Bean
    // public DataSourceTransactionManager transactionManager() {
    //     return new DataSourceTransactionManager(dataSource());
    // }

    // @Bean
    // public SqlSessionFactory sqlSessionFactory() throws Exception {
    //
    //     SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    //     sqlSessionFactoryBean.setDataSource(dataSource());
    //     PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    //     sqlSessionFactoryBean
    //         .setMapperLocations(resolver.getResources("classpath:/*.xml"));
    //     return sqlSessionFactoryBean.getObject();
    // }

}
