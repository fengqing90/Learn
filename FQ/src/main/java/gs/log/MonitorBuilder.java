package gs.log;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.spi.LocationAwareLogger;

/**
 * @author linjun
 */
class MonitorBuilder extends ThreadLocal<Map<String, Object>> {

    /**
     *
     */
    private static final long serialVersionUID = 1523492284710835722L;

    private static final String DEFAULT_STRING = "-";
    private static final long DEFAULT_START = -1l;
    private static final long DEFAULT_COST = 0l;
    private static final String RESULT_SUCCESS = "Y";
    private static final String RESULT_FAILURE = "N";

    private static final String KEY_CODE = "cd";
    private static final String KEY_COST = "ct";
    private static final String KEY_START = "s";
    private static final String KEY_RESULT = "r";
    private static final String KEY_EXCEPTION = "e";
    private static final String KEY_MSG = "m";
    private static final String KEY_IP = "i";
    private static final String KEY_LEVEL = "l";
    private static final String KEY_FQCN = "f";

    private Map<String, Object> paramMap = new WeakHashMap<>();

    private Map<String, Object> customerParamMap = new WeakHashMap<>();

    MonitorBuilder() {
        super();
    }

    @Override
    public void remove() {
        super.remove();
        this.paramMap.clear();
        this.customerParamMap.clear();
    }

    /**
     * 重写父类方法，确保始终返回一个有效实例。
     *
     * @see java.lang.ThreadLocal#initialValue()
     */
    @Override
    protected Map<String, Object> initialValue() {
        return this.customerParamMap;
    }

    public MonitorBuilder start() {
        this.paramMap.put(MonitorBuilder.KEY_START, System.currentTimeMillis());
        return this;
    }

    public MonitorBuilder fqcn(String fqcn) {
        this.paramMap.put(MonitorBuilder.KEY_FQCN, fqcn);
        return this;
    }

    public MonitorBuilder code(String code) {
        this.paramMap.put(MonitorBuilder.KEY_CODE, code);
        return this;
    }

    public MonitorBuilder mail() {
        this.code(LogCode.ERROR_WITH_MAIL.getCodeStr()).failure().byError();
        return this;
    }

    public MonitorBuilder sms() {
        this.code(LogCode.ERROR_WITH_MSG.getCodeStr()).failure().byError();
        return this;
    }

    public MonitorBuilder cost(long cost) {
        this.paramMap.put(MonitorBuilder.KEY_COST, cost);
        return this;
    }

    public MonitorBuilder failure() {
        this.paramMap.put(MonitorBuilder.KEY_RESULT,
            MonitorBuilder.RESULT_FAILURE);
        return this;
    }

    public MonitorBuilder success() {
        this.paramMap.put(MonitorBuilder.KEY_RESULT,
            MonitorBuilder.RESULT_SUCCESS);
        return this;
    }

    public MonitorBuilder message(String msg) {
        this.paramMap.put(MonitorBuilder.KEY_MSG, msg);
        return this;
    }

    public MonitorBuilder ip(String ip) {
        this.paramMap.put(MonitorBuilder.KEY_IP, ip);
        return this;
    }

    public MonitorBuilder param(String key, Object value) {
        this.customerParamMap.put(key, value);
        return this;
    }

    public MonitorBuilder throwed(Throwable t) {
        this.paramMap.put(MonitorBuilder.KEY_EXCEPTION, t);
        return this;
    }

    public MonitorBuilder byInfo() {
        this.paramMap.put(MonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.INFO_INT);
        return this;
    }

    public MonitorBuilder byWarn() {
        this.paramMap.put(MonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.WARN_INT);
        return this;
    }

    public MonitorBuilder byError() {
        this.paramMap.put(MonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.ERROR_INT);
        return this;
    }

    /**
     * @return the {@link #serialversionuid}
     */
    protected static long getSerialversionuid() {
        return MonitorBuilder.serialVersionUID;
    }

    /**
     * @return the {@link #theCode}
     */
    protected String getTheCode() {
        return (String) this.paramMap.getOrDefault(MonitorBuilder.KEY_CODE,
            MonitorBuilder.DEFAULT_STRING);
    }

    /**
     * @return the {@link #theCost}
     */
    protected long getTheCost() {
        long start = (long) this.paramMap.getOrDefault(
            MonitorBuilder.KEY_START, MonitorBuilder.DEFAULT_START);

        long cost;
        if (start == MonitorBuilder.DEFAULT_START) {
            cost = (long) this.paramMap.getOrDefault(MonitorBuilder.KEY_COST,
                MonitorBuilder.DEFAULT_COST);
        } else {
            cost = System.currentTimeMillis() - start;
        }

        return cost;
    }

    /**
     * @return the {@link #theResult}
     */
    protected String getTheResult() {
        return (String) this.paramMap.getOrDefault(MonitorBuilder.KEY_RESULT,
            MonitorBuilder.RESULT_SUCCESS);
    }

    /**
     * @return the {@link #theException}
     */
    protected Throwable getTheException() {
        return (Throwable) this.paramMap.getOrDefault(
            MonitorBuilder.KEY_EXCEPTION, null);
    }

    /**
     * @return the {@link #theMsg}
     */
    protected String getTheMsg() {
        return (String) this.paramMap.getOrDefault(MonitorBuilder.KEY_MSG,
            MonitorBuilder.DEFAULT_STRING);
    }

    /**
     * @return the {@link #theIp}
     */
    protected String getTheIp() {
        return (String) this.paramMap.getOrDefault(MonitorBuilder.KEY_IP,
            MonitorBuilder.DEFAULT_STRING);
    }

    /**
     * @return the {@link #paramMap}
     */
    protected Map<String, Object> getParamMap() {
        return this.customerParamMap;
    }

    /**
     * @return the {@link #theLevel}
     */
    protected int getTheLevel() {
        return (int) this.paramMap.getOrDefault(MonitorBuilder.KEY_LEVEL,
            LocationAwareLogger.INFO_INT);
    }

    /**
     * @return the {@link #theLevel}
     */
    protected String getTheFqcn() {
        return (String) this.paramMap.getOrDefault(MonitorBuilder.KEY_FQCN,
            UcLogger.class.getName());
    }
}