package learn.spring.创建顺序;

import org.springframework.beans.factory.FactoryBean;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/6/19 11:11
 */
@Slf4j
public class MyFactoryBean implements FactoryBean<MyFactoryBean> {
    public MyFactoryBean() {
        log.info("【MyFactoryBean】构造方法。");
    }

    @Override
    public MyFactoryBean getObject() throws Exception {
        MyFactoryBean myFactoryBean = new MyFactoryBean();
        log.info("【MyFactoryBean】getObject [{}]", myFactoryBean);
        return myFactoryBean;
    }

    @Override
    public Class<?> getObjectType() {
        log.info("【MyFactoryBean】getObjectType。");
        return MyFactoryBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
