package cn.fq.oauth.bean;

/**
 * @author fengqing
 * @date 2021/5/8 18:21
 */
public class Result<T> {

    private T result;

    private String errMsg;

    private Object[] errMsgParams;

    public Result(T result, String errMsg, Object[] errMsgParams) {
        this.result = result;
        this.errMsg = errMsg;
        this.errMsgParams = errMsgParams;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object[] getErrMsgParams() {
        return errMsgParams;
    }

    public void setErrMsgParams(Object[] errMsgParams) {
        this.errMsgParams = errMsgParams;
    }

    public static Result<Boolean> ok() {
        return new Result<>(true, null, null);
    }

    public static <T> Result<T> build(T result) {
        return new Result(result, null, null);
    }

    public static <T> Result<T> build(T result, String errMsg) {
        return new Result(result, errMsg, null);
    }

    public static <T> Result<T> build(String errMsg) {
        return new Result(null, errMsg, null);
    }

    public static <T> Result<T> buildWithParams(T result, String errMsg,
            Object... args) {
        return new Result(result, errMsg, args);
    }
}
