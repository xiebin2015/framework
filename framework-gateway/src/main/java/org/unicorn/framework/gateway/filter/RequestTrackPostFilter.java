package org.unicorn.framework.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.unicorn.framework.base.base.UnicornContext;
import org.unicorn.framework.base.constants.UnicornConstants;
import org.unicorn.framework.gateway.properties.UnicornGatewaySecurityProperties;

/**
 * @author xiebin
 * 请求跟踪过滤器
 * 所有的资源请求在路由之前进行前置过滤 生成请求唯一id并传递给下游服务
 */
public class RequestTrackPostFilter extends ZuulFilter {

    /**
     * 过滤器的类型 post表示请求在路由之后被过滤
     *
     * @return 类型
     */
    @Override
    public String filterType() {
        return "error";
    }

    /**
     * 过滤器的执行顺序
     *
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否会被执行
     *
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤逻辑
     *
     * @return 过滤结果
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String ss = context.get(UnicornConstants.REQUEST_TRACK_HEADER_NAME).toString();
        context.remove(UnicornConstants.REQUEST_TRACK_HEADER_NAME);
        String id = UnicornContext.getValue(UnicornConstants.REQUEST_TRACK_HEADER_NAME);
        return null;
    }


}