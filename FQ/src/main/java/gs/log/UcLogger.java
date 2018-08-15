package gs.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import gs.utils.JsonMapper;

public class UcLogger extends Log4jLogger {
    /**
    *
    */
    private static final long serialVersionUID = 4455644845030160930L;

    /**
     * 审计日志格式。
     * <p>
     * 前缀和监控日志一样<br>
     * 但最后多一个“{}”：以json格式的日志信息结尾
     */
    private static final String AUDIT_FORMAT = "{}^|{}^|{}^|{}^|{}^|{}";

    /**
     * 监控日志<br>
     * level^| time^|project^|method^|client_ip^|result^|code^|cost^|msg^|params
     */
    private static final String MONITOR_FORMAT = "{}^|{}^|{}^|{}^|{}";

    /**
     * 审计日志记录器
     * <p>
     * 要求Log4j配置中，有一个名为“AUDIT”的LOGGER配置。否则会直接用继承的LOGGER
     */
    private static final Log4jLogger AUDIT_LOGGER = (Log4jLogger) LoggerFactory
        .getLogger("AUDIT");

    /**
     * 监控信息构建器。
     * <p>
     * 线程独立信息。
     */
    private static final MonitorBuilder MONITOR_BUILDER = new MonitorBuilder();

    /**
     * @param logger
     * @param name
     */
    public UcLogger(ExtendedLogger logger, String name) {
        super(logger, name);
    }

    /**
     * @return
     */
    public UcLogger monitor() {

        if (this.isLogEnabled()) {

            // DONE linjun 2016-06-16 用户指定的数据，追加到格式和参数后面
            Map<String, Object> params = UcLogger.MONITOR_BUILDER.getParamMap();

            StringBuilder format = new StringBuilder(UcLogger.MONITOR_FORMAT);

            List<Object> objects = new ArrayList<>();
            objects.add(UcLogger.MONITOR_BUILDER.getTheIp());
            objects.add(UcLogger.MONITOR_BUILDER.getTheResult());
            objects.add(UcLogger.MONITOR_BUILDER.getTheCode());
            objects.add(UcLogger.MONITOR_BUILDER.getTheCost());

            objects.add(UcLogger.MONITOR_BUILDER.getTheMsg());
            params.forEach((k, v) -> {
                format.append("^|" + k + "={}");
                objects.add(v);
            });

            this.log(null, UcLogger.MONITOR_BUILDER.getTheFqcn(),
                UcLogger.MONITOR_BUILDER.getTheLevel(), format.toString(),
                objects.toArray(), UcLogger.MONITOR_BUILDER.getTheException());
        }

        // 记录完成之后，重新初始化一次。
        UcLogger.MONITOR_BUILDER.remove();
        return this;
    }

    private boolean isLogEnabled() {
        // DONE linjun 2016-04-05 判断当前日志等级是否可用
        boolean isLogEnabled;
        switch (UcLogger.MONITOR_BUILDER.getTheLevel()) {
            case LocationAwareLogger.TRACE_INT:
                isLogEnabled = this.isTraceEnabled();
                break;
            case LocationAwareLogger.DEBUG_INT:
                isLogEnabled = this.isDebugEnabled();
                break;
            case LocationAwareLogger.INFO_INT:
                isLogEnabled = this.isInfoEnabled();
                break;
            case LocationAwareLogger.WARN_INT:
                isLogEnabled = this.isWarnEnabled();
                break;
            case LocationAwareLogger.ERROR_INT:
                isLogEnabled = this.isErrorEnabled();
                break;
            default:
                isLogEnabled = false;
                break;
        }
        return isLogEnabled;
    }

    /**
     * 记录审计日志。
     * <p>
     * THREAD-10273 linjun<br>
     * 记录完成后，会对{@link #MONITOR_BUILDER}中的数据做一次重新初始化。
     * <p>
     * 在这个方法中，{@link #fqcn(String)}的值会写入日志中，而不会用作{@link #AUDIT_LOGGER}的fqcn。<br>
     * {@link #AUDIT_LOGGER}的fqcn是UcLogger.class.getName() =======<br>
     * THREAD-10273 审计日志，初稿
     *
     * @param obj
     *        审计日志实体。
     */
    public void audit(Object obj) {

        UcLogger.AUDIT_LOGGER.log(null, UcLogger.MONITOR_BUILDER.getTheFqcn(),
            UcLogger.MONITOR_BUILDER.getTheLevel(), UcLogger.AUDIT_FORMAT,
            new Object[] { UcLogger.MONITOR_BUILDER.getTheIp(),
                UcLogger.MONITOR_BUILDER.getTheResult(),
                UcLogger.MONITOR_BUILDER.getTheCode(),
                UcLogger.MONITOR_BUILDER.getTheCost(),
                JsonMapper.nonNullMapper().toJson(obj) },
            null);

        // 记录完成之后，重新初始化一次。
        UcLogger.MONITOR_BUILDER.remove();
    }

    // [start] linjun 2016-5-4 THREAD-10273
    /*
     * 以下所有方法，都直接委托给了MONITOR_BUILDER
     */
    public UcLogger start() {
        UcLogger.MONITOR_BUILDER.start();
        return this;
    }

    public UcLogger sms() {
        UcLogger.MONITOR_BUILDER.sms();
        return this;
    }

    public UcLogger mail() {
        UcLogger.MONITOR_BUILDER.mail();
        return this;
    }

    public UcLogger code(String code) {
        if (StringUtils.isNotBlank(code)) {
            UcLogger.MONITOR_BUILDER.code(code);
        }
        return this;
    }

    public UcLogger cost(long cost) {
        UcLogger.MONITOR_BUILDER.cost(cost);
        return this;
    }

    public UcLogger failure() {
        UcLogger.MONITOR_BUILDER.failure();
        return this;
    }

    public UcLogger success() {
        UcLogger.MONITOR_BUILDER.success();
        return this;
    }

    public UcLogger message(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            UcLogger.MONITOR_BUILDER.message(msg);
        }
        return this;
    }

    public UcLogger ip(String ip) {
        if (StringUtils.isNotBlank(ip)) {
            UcLogger.MONITOR_BUILDER.ip(ip);
        }
        return this;
    }

    public UcLogger param(String key, Object value) {
        UcLogger.MONITOR_BUILDER.param(key, value);
        return this;
    }

    public UcLogger throwed(Throwable t) {
        UcLogger.MONITOR_BUILDER.throwed(t);
        return this;
    }

    public UcLogger byInfo() {
        UcLogger.MONITOR_BUILDER.byInfo();
        return this;
    }

    public UcLogger byWarn() {
        UcLogger.MONITOR_BUILDER.byWarn();
        return this;
    }

    public UcLogger byError() {
        UcLogger.MONITOR_BUILDER.byError();
        return this;
    }

    public UcLogger fqcn(String fqcn) {
        if (StringUtils.isNotBlank(fqcn)) {
            UcLogger.MONITOR_BUILDER.fqcn(fqcn);
        }
        return this;
    }
    // [end] linjun 2016-5-4 THREAD-10273

}
