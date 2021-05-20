package cn.fq.product.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import cn.fq.common.enums.DataSourceType;
import cn.fq.product.config.datasource.DynamicDataSourceContextHolder;

/**
 * 【标记-1】 如果oauth2用jdbc保存相关数据，并且不是用主数据源，需要手动切换数据源。
 *
 * @author fengqing
 * @date 2021/5/19 20:44
 */
public class MyOAuth2AuthenticationProcessingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        DynamicDataSourceContextHolder
            .setDataSourceType(DataSourceType.OAUTH2.name());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
