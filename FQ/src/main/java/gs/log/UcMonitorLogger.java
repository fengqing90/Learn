package gs.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.LoggerFactory;

/**
 * @author linjun
 */
public class UcMonitorLogger extends Log4jLogger {

    /**
     *
     */
    private static final long serialVersionUID = 611616789694499747L;

    /**
     * 默认的fqcn，即当前类名
     */
    private static final String DEFAULT_FQCN = UcMonitorLogger.class.getName();
    /**
     * 监控日志<br>
     * level^| time^|project^|method^|client_ip^|result^|code^|cost^|msg^|params
     */
    private static final String MONITOR_FORMAT = "{}^|{}^|{}^|{}^|";

    /**
     * 监控日志记录器
     */
    private static final Log4jLogger MONITOR_LOGGER = (Log4jLogger) LoggerFactory
        .getLogger("MONITOR");
    /**
     * 监控信息堆栈
     * <p>
     * 按照线程信息
     */
    private static final ThreadLocal<Map<String, UcMonitorBuilder>> MONITOR_BUILDER_MAPS = new ThreadLocal<>();

    /**
     * @param logger
     * @param name
     */
    UcMonitorLogger(ExtendedLogger logger, String name) {
        super(logger, name);
    }

    /**
     * 将 {@link #MONITOR_BUILDER_MAPS}中采集的数据信息输出到监控日志中
     *
     * @return
     */
    public UcMonitorLogger monitor() {

        // DONE linjun 2016-06-16 用户指定的数据，追加到格式和参数后面
        UcMonitorBuilder currentMonitor = UcMonitorLogger.find();

        StringBuilder format = new StringBuilder(UcMonitorLogger.MONITOR_FORMAT);

        List<Object> objects = new ArrayList<>();
        objects.add(currentMonitor.getTheIp());
        objects.add(currentMonitor.getTheResult());
        objects.add(currentMonitor.getTheCode());
        objects.add(currentMonitor.getTheCost() == 0 ? "-" : currentMonitor
            .getTheCost());

        Map<String, Object> params = currentMonitor.getParamMap();
        params.forEach((k, v) -> {
            format.append(k).append(":{};");
            objects.add(v);
        });

        UcMonitorLogger.MONITOR_LOGGER.log(null, UcMonitorLogger.DEFAULT_FQCN,
            currentMonitor.getTheLevel(), format.toString(), objects.toArray(),
            currentMonitor.getTheException());

        // 移除当前数据
        UcMonitorLogger.MONITOR_BUILDER_MAPS.get().remove(
            currentMonitor.getTheFqcn());

        // 记录完成之后，重新初始化一次。
        currentMonitor.remove();
        return this;
    }

    /**
     * 从堆栈中找出当前线程中正在执行的方法对应的监控信息记录器。
     * <p>
     *
     * @return
     */
    private static UcMonitorBuilder find() {

        String fqcn = UcMonitorLogger.findFqcn();

        UcMonitorBuilder monitorBuilder = UcMonitorLogger.find(fqcn);

        return monitorBuilder;
    }

    /**
     * 找到调用当前类的那个线程堆栈信息，获取其类名+方法名。
     *
     * @return
     */
    private static String findFqcn() {

        String fqcn = UcMonitorLogger.DEFAULT_FQCN;

        StackTraceElement[] stackTraceElements = new Throwable()
            .getStackTrace();

        boolean isUcMonitorLogger = false;
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (UcMonitorLogger.DEFAULT_FQCN.equals(stackTraceElement
                .getClassName())) {
                isUcMonitorLogger = true;
            } else if (isUcMonitorLogger) {
                // 找到后即刻退出遍历，正常应该只会有三层
                fqcn = stackTraceElement.getClassName() + "#"
                    + stackTraceElement.getMethodName();
                break;
            }
        }

        return fqcn;
    }

    /**
     * 根据fqcn记录的信息，获取一个记录器
     *
     * @param fqcn
     * @return 保证非null
     */
    private static UcMonitorBuilder find(String fqcn) {
        Map<String, UcMonitorBuilder> builderMap = UcMonitorLogger.MONITOR_BUILDER_MAPS
            .get();

        if (builderMap == null) {
            builderMap = new HashMap<>();
            UcMonitorLogger.MONITOR_BUILDER_MAPS.set(builderMap);
        }

        UcMonitorBuilder currentMonitor = builderMap.getOrDefault(fqcn,
            new UcMonitorBuilder());
        currentMonitor.fqcn(fqcn);
        builderMap.put(fqcn, currentMonitor);

        return currentMonitor;
    }

    // [start] linjun 2016-5-4 THREAD-10273
    /*
     * 以下所有方法，都直接委托给了MONITOR_BUILDER
     */
    /**
     * 记录下当前方法开始执行的时间。
     * <p>
     * 用于在调用{@link #monitor()}方法时，计算执行用时。
     *
     * @return
     */
    public UcMonitorLogger start() {
        UcMonitorLogger.find().start();
        return this;
    }

    /**
     * 标记 {@link #monitor()}方法记录的信息需要发送短信、操作失败、以error级别输出
     *
     * @return
     */
    public UcMonitorLogger sms() {
        UcMonitorLogger.find().code(LogCode.ERROR_WITH_MSG.getCodeStr())
            .failure().byError();
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录的信息需要发送邮件、操作失败、以error级别输出
     *
     * @return
     */
    public UcMonitorLogger mail() {
        UcMonitorLogger.find().code(LogCode.ERROR_WITH_MAIL.getCodeStr())
            .failure().byError();
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录的动作代码
     *
     * @param code
     * @return
     */
    public UcMonitorLogger code(String code) {
        if (StringUtils.isNotBlank(code)) {
            UcMonitorLogger.find().code(code);
        }
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息中操作结果为“失败”
     *
     * @return
     */
    public UcMonitorLogger failure() {
        UcMonitorLogger.find().failure();
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息中操作结果为“成功”
     * <p>
     * 默认值就是成功
     *
     * @return
     */
    public UcMonitorLogger success() {
        UcMonitorLogger.find().success();
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息中需要输出的额外信息
     *
     * @param msg
     * @return
     */
    public UcMonitorLogger message(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            UcMonitorLogger.find().message(msg);
        }
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息中需要记录的ip地址
     *
     * @param ip
     * @return
     */
    public UcMonitorLogger ip(String ip) {
        if (StringUtils.isNotBlank(ip)) {
            UcMonitorLogger.find().ip(ip);
        }
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息中需要记录的额外参数
     * <p>
     * 这些参数会以^|key=value^|key=value的格式，追加在监控日志尾部
     *
     * @param key
     * @param value
     * @return
     */
    public UcMonitorLogger param(String key, Object value) {
        UcMonitorLogger.find().param(key, value);
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息中需要记录的异常
     *
     * @param t
     * @return
     */
    public UcMonitorLogger throwed(Throwable t) {
        UcMonitorLogger.find().throwed(t);
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息的日志级别为info
     *
     * @return
     */
    public UcMonitorLogger byInfo() {
        UcMonitorLogger.find().byInfo();
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息日志级别为warn
     *
     * @return
     */
    public UcMonitorLogger byWarn() {
        UcMonitorLogger.find().byWarn();
        return this;
    }

    /**
     * 标记{@link #monitor()}方法记录监控信息日志级别为error
     *
     * @return
     */
    public UcMonitorLogger byError() {
        UcMonitorLogger.find().byError();
        return this;
    }
    // [end] linjun 2016-5-4 THREAD-10273
}
