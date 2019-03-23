package org.unicorn.framework.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.unicorn.framework.oauth.handler.UnicornAccessDeniedHandler;

/**
 * @author xiebin
 */
@Configuration
@EnableAuthorizationServer
@ConditionalOnProperty(prefix = "unicorn.security.oauth2", name = "authorizationServer", havingValue = "true")
public abstract class UnicornAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .exceptionTranslator(customWebResponseExceptionTranslator)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService());

    }

    /**
     * 配置客户端一些信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.authenticationEntryPoint(new UnicornAuthExceptionEntryPoint())
                .accessDeniedHandler(new UnicornAccessDeniedHandler())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 用户明细服务
     *
     * @return
     */
    public abstract UserDetailsService userDetailsService();

    /**
     * 客户端明细服务
     *
     * @return
     */
    public abstract ClientDetailsService clientDetailsService();

}
