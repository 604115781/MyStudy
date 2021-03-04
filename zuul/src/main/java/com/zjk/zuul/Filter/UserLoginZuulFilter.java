package com.zjk.zuul.Filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/13 9:38
 */
@Component
public class UserLoginZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        /*设置过滤器类型为pre
        * pre:请求在被路由之前执行
        * routing：在路由请求时被调用
        * post：在routing和error过滤器之后调用
        * error：处理请求时发声错误调用*/
        return "pre";
    }


    @Override
    public int filterOrder() {
        /*设置执行优先级，数值越小优先级越高*/
        return 0;
    }


    @Override
    public boolean shouldFilter() {
        /*该过滤器需要执行*/
        return true;
    }


    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.setResponseBody("token is empty!");
            return null;
        }
        return null;
    }
}
