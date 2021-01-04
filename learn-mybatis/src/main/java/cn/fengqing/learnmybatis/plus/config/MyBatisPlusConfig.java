package cn.fengqing.learnmybatis.plus.config;

/**
 * @Mapper 貌似可以代替：
 * @Repository 和 @MapperScan("cn.fengqing.learnmybatis.plus.mapper")
 * @author fengqing
 * @date 2020/12/23 18:00
 */

// @MapperScan("cn.fengqing.learnmybatis.plus.mapper")
// @EnableTransactionManagement
// @Configuration
public class MyBatisPlusConfig {
    // // 注册乐观锁插件
    // @Bean
    // public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    //     return new OptimisticLockerInterceptor();
    // }
    //
    // // 分页插件
    // @Bean
    // public PaginationInterceptor paginationInterceptor() {
    //     return new PaginationInterceptor();
    // }
    //
    // // 逻辑删除组件！
    // @Bean
    // public ISqlInjector sqlInjector() {
    //     return new LogicSqlInjector();
    // }
    //
    // /**
    //  * SQL执行效率插件
    //  */
    // @Bean
    // @Profile({ "dev", "test" })// 设置 dev test 环境开启，保证我们的效率
    // public PerformanceInterceptor performanceInterceptor() {
    //     PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    //     performanceInterceptor.setMaxTime(100); //ms 设置sql执行的最大时间，如果超过了则不执行
    //     performanceInterceptor.setFormat(true);
    //     return performanceInterceptor;
    // }

}
