package com.letsstartcoding.springbootrestapioauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableResourceServer
public class ResourseServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        System.out.println("resource server");
        //@formatter:off
        http
                .authorizeRequests()
                .antMatchers("/demo/").authenticated()
                .anyRequest().permitAll();

//        http.authorizeRequests().antMatchers("**/secured/**").authenticated();
//        http.authorizeRequests().antMatchers("/users-test/").fullyAuthenticated();
        //@formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        System.out.println("resource resserversecuconfig ran");

        config.tokenServices(tokenServices());
    }

    @Bean
    public TokenStore tokenStore() {
        System.out.println("resource tokenstore ran");

        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        System.out.println("resource token converter ran");

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("985"); // symmetric key
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        System.out.println("resource tokenservice ran");

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("resource encoder ran");

        return new BCryptPasswordEncoder();
    }
}
