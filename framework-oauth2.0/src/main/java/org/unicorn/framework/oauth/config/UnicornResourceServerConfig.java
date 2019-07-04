package org.unicorn.framework.oauth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.unicorn.framework.oauth.properties.OAuth2Properties;
import org.unicorn.framework.util.json.JsonUtils;

import java.util.List;

/**
 * @author xiebin
 * @since 1.0
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties({OAuth2Properties.class})
@Slf4j
public class UnicornResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Autowired
  private OAuth2Properties oauth2Properties;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> permitList=  oauth2Properties.getPermitAlls();
        permitList.add("/oauth/**");
        log.info("权限==="+JsonUtils.toJson(permitList));
        http.requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers(permitList.stream().toArray(String[]::new)).permitAll()
                .antMatchers("/**.html", "/**.js", "/**.css", "/**.ico", "/**.ttf").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/swagger**","/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(new UnicornAuthExceptionEntryPoint());
    }


}
