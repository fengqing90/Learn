package gs.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.slf4j.Log4jLoggerFactory;
import org.slf4j.Logger;

/**
 * 需要三个log4j2.xml配置： <li>
 * {@code <property name="MONITOR_PATTERN">%-5level^|%d
 * ISO8601}^|thread^|%class#%method:%L^|%m%n </property>}</li> <li>
 * {@code <Logger name="MONITOR" level="INFO" additivity="false"><AppenderRef ref="MonitorAsyncAppender" /></Logger>}
 * </li><li>
 * {@code   <Async name="MonitorAsyncAppender" includeLocation="true">...</Async>}
 * </li>
 *
 */
public final class UcMonitorLoggerFactory extends Log4jLoggerFactory {
    /**
     * 单例
     */
    private static final UcMonitorLoggerFactory SINGLETON = new UcMonitorLoggerFactory();

    private UcMonitorLoggerFactory() {
        super();
    }

    /**
     * 创建一个 {@link UcMonitorLogger}
     *
     * @see org.apache.logging.slf4j.Log4jLoggerFactory#newLogger(java.lang.String,
     *      org.apache.logging.log4j.spi.LoggerContext)
     */
    @Override
    protected Logger newLogger(final String name, final LoggerContext context) {
        final String key = Logger.ROOT_LOGGER_NAME.equals(name) ? LogManager.ROOT_LOGGER_NAME
            : name;
        return new UcMonitorLogger(context.getLogger(key), name);
    }

    /**
     * 获取一个监控用的日志记录器
     *
     * @param loggerName
     * @return
     */
    public static UcMonitorLogger getUcMonitorLogger(String loggerName) {
        return (UcMonitorLogger) UcMonitorLoggerFactory.SINGLETON
            .getLogger(loggerName);

    }

    /**
     * 获取一个监控用的日志记录器
     * <p>
     * 委托给 {@link #getUcMonitorLogger(String)}
     *
     * @param claz
     * @return
     */
    public static UcMonitorLogger getUcMonitorLogger(Class<?> claz) {
        return UcMonitorLoggerFactory.getUcMonitorLogger(claz.getName());
    }
}
