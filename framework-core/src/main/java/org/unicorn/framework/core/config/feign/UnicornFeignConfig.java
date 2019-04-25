package org.unicorn.framework.core.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

/**
 * @author xiebin
 */
@Configuration
@Slf4j
public class UnicornFeignConfig implements RequestInterceptor {
    /**
     * token 头部名
     */
    public static final String TOKEN_HEADER_NAME="Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String authorization = request.getHeader(TOKEN_HEADER_NAME);
            Map<String, Collection<String>> headerMap=requestTemplate.request().headers();
            if(!headerMap.containsKey(TOKEN_HEADER_NAME)){
                requestTemplate.header(TOKEN_HEADER_NAME, authorization);
            }
        } catch (Exception e) {
            log.error("requestTemplate设置错误", e);
        }
    }
}