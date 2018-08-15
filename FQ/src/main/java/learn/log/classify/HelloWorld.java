package learn.log.classify;

import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.LoggerFactory;

/**
 * 如果slf4j没有具体的实现或多个实现其绑定方法会自动根据规则进行绑定，详细见commons-logging 和 log4j 之间的关系
 * thread系统中的日志是自己写了 slf4j和apache日志实现类绑定类 并加入自定义的功能
 *
 * @author FengQing
 */
public class HelloWorld {

    // apache日志的具体实现类
    static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager
        .getLogger(HelloWorld.class);
    // org.apache.logging.log4j.core.Logger  接口 org.apache.logging.log4j.Logger

    // slf4j 是现在日志统一的接口，但他不做任何实现
    static org.slf4j.Logger logger2 = LoggerFactory.getLogger(HelloWorld.class);

    // 将slf4j和apache日志的具体实现类 进行绑定
    // 如果slf4j没有具体的实现或多个实现其绑定方法会自动根据规则进行绑定，详细见commons-logging 和 log4j 之间的关系
    static org.apache.logging.slf4j.Log4jLogger log4jLogger = (Log4jLogger) new org.apache.logging.slf4j.Log4jLoggerFactory()
        .getLogger(HelloWorld.class.getName());

    ///////////////// 用于旧日志过渡类
    //    org.apache.commons.logging.impl.Log4JLogger

    public static void main(String[] args) {
        HelloWorld.testTrace();
        HelloWorld.logger();
//        HelloWorld.logger2();
//        HelloWorld.log4jLogger();
    }

    public static void logger() {
        String hello = "Hello, World!";
        HelloWorld.logger.trace("TRACE: " + hello);
        HelloWorld.logger.debug("DEBUG: " + hello);
        HelloWorld.logger.info("INFO: " + hello);
        HelloWorld.logger.warn("WARN: " + hello);
        HelloWorld.logger.error("ERROR: " + hello);
        HelloWorld.logger.fatal("FATAL: " + hello);
    }

    public static void logger2() {
        String hello = "Hello, World!";
        HelloWorld.logger2.trace("TRACE: " + hello);
        HelloWorld.logger2.debug("DEBUG: " + hello);
        HelloWorld.logger2.info("INFO: " + hello);
        HelloWorld.logger2.warn("WARN: " + hello);
        HelloWorld.logger2.error("ERROR: " + hello);
    }

    public static void log4jLogger() {
        String hello = "Hello, World!";
        HelloWorld.log4jLogger.trace("TRACE: " + hello);
        HelloWorld.log4jLogger.debug("DEBUG: " + hello);
        HelloWorld.log4jLogger.info("INFO: " + hello);
        HelloWorld.log4jLogger.warn("WARN: " + hello);
        HelloWorld.log4jLogger.error("ERROR: " + hello);
    }

    public static void testTrace() {
        HelloWorld.logger.entry();
        HelloWorld.logger.exit(false);
    }

}
