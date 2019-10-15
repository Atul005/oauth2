package com.letsstartcoding.springbootrestapioauth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${varun.oauth.tokenTimeout:3600}")
	private int expiration;

	@Autowired
    private DataSource dataSource;


	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("oauth encoder ran");
		return new BCryptPasswordEncoder();
	}

	@Bean
    public TokenStore tokenStore() {
		System.out.println("oauth tokenstore ran");
		return new JdbcTokenStore(dataSource);
    }

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
		System.out.println("oauth authserverendpoconfig ran");
		configurer.tokenStore(getTokenStore()).accessTokenConverter((getJwtAccessTokenConverter()));
		configurer.authenticationManager(authenticationManager);
		configurer.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		System.out.println("oauth clientdetailsservreconfig ran");
		/*
		clients.inMemory().withClient("varun").secret("secret").accessTokenValiditySeconds(expiration)
				.scopes("read", "write").authorizedGrantTypes("password", "refresh_token").resourceIds("resource");
				*/
		clients.jdbc(dataSource);
	}

	private TokenStore getTokenStore() {
		System.out.println("oauth tokenstore ran");
		return new JwtTokenStore(getJwtAccessTokenConverter());
	}

	private JwtAccessTokenConverter getJwtAccessTokenConverter() {
		System.out.println("oauth token converter ran");

		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("985");
		return accessTokenConverter;
	}
}
