package gs.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.slf4j.Log4jLoggerFactory;
import org.slf4j.Logger;
public final class UcLoggerFactory extends Log4jLoggerFactory {

    private static final UcLoggerFactory SINGLETON = new UcLoggerFactory();

    private UcLoggerFactory() {
        super();
    }

    @Override
    protected Logger newLogger(final String name, final LoggerContext context) {
        final String key = Logger.ROOT_LOGGER_NAME.equals(name) ? LogManager.ROOT_LOGGER_NAME
            : name;
        return new UcLogger(context.getLogger(key), name);
    }

    public static UcLogger getUcLogger(String loggerName) {
        return (UcLogger) UcLoggerFactory.SINGLETON.getLogger(loggerName);

    }

    public static UcLogger getUcLogger(Class<?> claz) {
        return UcLoggerFactory.getUcLogger(claz.getName());
    }

}