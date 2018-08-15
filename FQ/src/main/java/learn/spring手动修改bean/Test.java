package learn.spring手动修改bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class Test {
    @Autowired
    protected ApplicationContext applicationContext;

//    public static void main(String[] args) {
////      ConfigurableApplicationContext factory =  (ConfigurableApplicationContext) this.applicationContext;
////      factory.containsBean("loanProductAdaptor");
////      factory.removeBeanDefinition("loanProductAdaptor");
////      RootBeanDefinition aa = new RootBeanDefinition();
////      aa.setBeanClass(LoanProductAdaptorTest.class);
////      factory.registerBeanDefinition("loanProductAdaptor", aa);
//      //将applicationContext转换为ConfigurableApplicationContext
//      ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) this.applicationContext;
//
//      // 获取bean工厂并转换为DefaultListableBeanFactory
//      DefaultListableBeanFactory factory = (DefaultListableBeanFactory) configurableApplicationContext
//          .getBeanFactory();
//      System.out.println(factory.getBean("loanProductAdaptor").getClass());
//      System.out.println("@@@@@@@@@@@@containsBean('loanProductAdaptor') : "
//          + factory.containsBean("loanProductAdaptor"));
//      System.out.println("@before@");
//      System.out.println(factory.getBeanDefinitionCount());
//      System.out.println("@after@");
//      factory.removeBeanDefinition("loanProductAdaptor");
//      System.out.println("@@@@@@@@@@@@containsBean('loanProductAdaptor') : "
//          + factory.containsBean("loanProductAdaptor"));
//      System.out.println(factory.getBeanDefinitionCount());
//
//      // 通过BeanDefinitionBuilder创建bean定义
//      BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
////          .genericBeanDefinition(LoanProductAdaptorTest.class); TODO
////      .genericBeanDefinition(LoanProductAdaptor.class);
//      // 设置属性userAcctDAO,此属性引用已经定义的bean:userAcctDAO
//
//      // 注册bean
//      factory.registerBeanDefinition("loanProductAdaptor",
//          beanDefinitionBuilder.getRawBeanDefinition());
//
//      System.out.println(factory.getBean("loanProductAdaptor").getClass());
//      System.out.println(11);
//    }
}
