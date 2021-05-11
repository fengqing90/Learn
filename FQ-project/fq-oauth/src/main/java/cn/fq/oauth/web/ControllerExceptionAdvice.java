package cn.fq.oauth.web;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/8 17:07
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvice {
    /**
     * 只有出现AccessDeniedException异常才调转403.jsp页面
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String exceptionAdvice() {
        log.info("【异常处理】");
        return "forward:/403.html";
    }
}
