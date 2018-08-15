package gs.log;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.spi.LocationAwareLogger;

class UcMonitorBuilder {

    /**
     * 默认的string代码
     */
    private static final String DEFAULT_STRING = "-";
    /**
     * 默认的起始时间
     */
    private static final long DEFAULT_START = -1l;
    /**
     * 成功标志位
     */
    private static final String RESULT_SUCCESS = "Y";
    /**
     * 失败标志位
     */
    private static final String RESULT_FAILURE = "N";

    /**
     * 关键字：code
     */
    private static final String KEY_CODE = "cd";
    /**
     * 关键字：start
     */
    private static final String KEY_START = "s";
    /**
     * 关键字：result
     */
    private static final String KEY_RESULT = "r";
    /**
     * 关键字：异常
     */
    private static final String KEY_EXCEPTION = "e";
    /**
     * 关键字：信息
     */
    private static final String KEY_MSG = "message";
    /**
     * 关键字：ip
     */
    private static final String KEY_IP = "i";
    /**
     * 关键字：级别
     */
    private static final String KEY_LEVEL = "l";
    /**
     * 关键字：fqcn
     */
    private static final String KEY_FQCN = "f";

    // DONE linjun 2016-04-05 这两个可能需要整合到一起去
    /**
     * 监控数据的容器
     */
    private final Map<String, Object> monitorParamMap = new HashMap<>();
    /**
     * 用户数据的容器
     */
    private final Map<String, Object> customerParamMap = new HashMap<>();

    UcMonitorBuilder() {
        super();
    }

    /**
     * 将监控数据和用户数据全部清空
     */
    protected void remove() {
        this.monitorParamMap.clear();
        this.customerParamMap.clear();
    }

    /**
     * 记录下起始日期
     *
     * @return
     */
    protected UcMonitorBuilder start() {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_START,
            System.currentTimeMillis());
        return this;
    }

    /**
     * @param fqcn
     * @return
     */
    protected UcMonitorBuilder fqcn(String fqcn) {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_FQCN, fqcn);
        return this;
    }

    /**
     * @param code
     * @return
     */
    protected UcMonitorBuilder code(String code) {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_CODE, code);
        return this;
    }

    /**
     * @return
     */
    protected UcMonitorBuilder failure() {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_RESULT,
            UcMonitorBuilder.RESULT_FAILURE);
        return this;
    }

    /**
     * @return
     */
    protected UcMonitorBuilder success() {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_RESULT,
            UcMonitorBuilder.RESULT_SUCCESS);
        return this;
    }

    /**
     * @param msg
     * @return
     */
    protected UcMonitorBuilder message(String msg) {
        this.customerParamMap.put(UcMonitorBuilder.KEY_MSG, msg);
        return this;
    }

    /**
     * @param ip
     * @return
     */
    protected UcMonitorBuilder ip(String ip) {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_IP, ip);
        return this;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    protected UcMonitorBuilder param(String key, Object value) {
        this.customerParamMap.put(key, value);
        return this;
    }

    /**
     * @param t
     * @return
     */
    protected UcMonitorBuilder throwed(Throwable t) {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_EXCEPTION, t);
        return this;
    }

    /**
     * @return
     */
    protected UcMonitorBuilder byInfo() {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.INFO_INT);
        return this;
    }

    /**
     * @return
     */
    protected UcMonitorBuilder byWarn() {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.WARN_INT);
        return this;
    }

    /**
     * @return
     */
    protected UcMonitorBuilder byError() {
        this.monitorParamMap.put(UcMonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.ERROR_INT);
        return this;
    }

    /**
     * @return the {@link #theCode}
     */
    protected String getTheCode() {
        return (String) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_CODE, UcMonitorBuilder.DEFAULT_STRING);
    }

    /**
     * @return the {@link #theCost}
     */
    protected long getTheCost() {
        long start = (long) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_START, UcMonitorBuilder.DEFAULT_START);
        long cost;
        if (start == -1) {
            cost = 0l;
        } else {
            cost = System.currentTimeMillis() - start;
        }
        return cost;
    }

    /**
     * @return the {@link #theResult}
     */
    protected String getTheResult() {
        return (String) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_RESULT, UcMonitorBuilder.RESULT_SUCCESS);
    }

    /**
     * @return the {@link #theException}
     */
    protected Throwable getTheException() {
        return (Throwable) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_EXCEPTION, null);
    }

    /**
     * @return the {@link #theMsg}
     */
    protected String getTheMsg() {
        return (String) this.customerParamMap.getOrDefault(
            UcMonitorBuilder.KEY_MSG, UcMonitorBuilder.DEFAULT_STRING);
    }

    /**
     * @return the {@link #theIp}
     */
    protected String getTheIp() {
        return (String) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_IP, UcMonitorBuilder.DEFAULT_STRING);
    }

    /**
     * @return the {@link #customerParamMap}
     */
    protected Map<String, Object> getParamMap() {
        return this.customerParamMap;
    }

    /**
     * @return the {@link #theLevel}
     */
    protected int getTheLevel() {
        return (int) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_LEVEL, LocationAwareLogger.INFO_INT);
    }

    /**
     * @return the {@link #theLevel}
     */
    protected String getTheFqcn() {
        return (String) this.monitorParamMap.getOrDefault(
            UcMonitorBuilder.KEY_FQCN, UcLogger.class.getName());
    }

}
