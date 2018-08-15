package learn. util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public final class SpringUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext = null;

    public SpringUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        SpringUtil.applicationContext = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtil.applicationContext;
    }

    public static Object getBean(String name) {
        return SpringUtil.getApplicationContext().getBean(name);
    }

}
