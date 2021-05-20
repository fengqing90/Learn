package cn.fq.product.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import cn.fq.common.enums.DataSourceType;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/18 11:30
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.product")
    public DataSource productSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.oauth2")
    public DataSource oauth2DataSource(DruidProperties druidProperties) {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(dataSource);
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource(DataSource productSource,
            DataSource oauth2DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.PRODUCT.name(), productSource);
        targetDataSources.put(DataSourceType.OAUTH2.name(), oauth2DataSource);
        return new DynamicDataSource(productSource, targetDataSources);

    }

    /**
     * 设置数据源
     *
     * @param targetDataSources
     *        备选数据源集合
     * @param sourceName
     *        数据源名称
     * @param beanName
     *        bean名称
     */
    // public void setDataSource(Map<Object, Object> targetDataSources,
    //         String sourceName, String beanName) {
    //     try {
    //         DataSource dataSource = SpringUtils.getBean(beanName);
    //         targetDataSources.put(sourceName, dataSource);
    //     } catch (Exception e) {
    //     }
    // }

    // @Bean
    // public DataSourceTransactionManager transactionManager() {
    //     return new DataSourceTransactionManager(dataSource());
    // }

    // @Bean
    // public SqlSessionFactory sqlSessionFactory() throws Exception {
    //     SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    //     sessionFactory.setDataSource(dataSource());
    //     return sessionFactory.getObject();
    // }

    // @Bean
    // public DataSource dataSource() {
    //     return DruidDataSourceBuilder.create().build();
    // }
}
