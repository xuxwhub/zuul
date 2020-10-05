package com.dunshan;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Gjing
 **/
@Component
@RefreshScope
public class GlobalFilter extends ZuulFilter {

    @Value("${zuul.biz.auth.enable}")
    private String authEnable;

    @Value("${zuul.biz.auth.token}")
    private String authToken;

    @Override
    public String filterType() {
        //设置过滤类型
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //设置过过滤器优先级
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        //是否需要过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if (!"1".equals(authEnable)) {
            return null;
        }

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token) ) {
            //返回错误信息
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            context.setResponseBody(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            context.setSendZuulResponse(false);
            return null;
        }
        if (!authToken.equals(token) ) {
            //返回错误信息
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            context.setResponseBody(HttpStatus.UNAUTHORIZED.getReasonPhrase() + "-token failed");
            context.setSendZuulResponse(false);
            return null;
        }

        return null;
    }
}