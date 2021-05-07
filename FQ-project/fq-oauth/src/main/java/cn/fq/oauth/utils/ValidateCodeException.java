package cn.fq.oauth.utils;

import org.springframework.security.core.AuthenticationException;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/7 16:05
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
