package com.letsstartcoding.springbootrestapioauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("security authmanaBuilder ran");
//		auth.inMemoryAuthentication()
//				.withUser("atul").password("pass").authorities("ROLE_USER", "ROLE_ADMIN");
//			.withUser("eko01").password("$phoenix$").roles("VENDOR").and()
//			.withUser("micro01").password("maxx$01").roles("VENDOR").and()
//			.withUser("vuliv01").password("wire$01").roles("VENDOR").and()
//			.withUser("indusos01").password("cookie$01").roles("VENDOR").and()
//			.withUser("aankit").password("password").roles("ADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//        auth.jdbcAuthentication();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("config server");
//		httpSecurity.authorizeRequests().antMatchers("/secured").fullyAuthenticated();

		httpSecurity
					.authorizeRequests()
					.anyRequest().authenticated();
//		httpSecurity
//					.authorizeRequests()
//					.antMatchers("/audio-content/**").permitAll()
//					.antMatchers("/content/**").permitAll()
//					.antMatchers("/khabri/vendor/**").access("hasRole('VENDOR')")
//					.antMatchers("/khabri/newsService/v1.1.6/get").permitAll()
//					.antMatchers("/khabri/userService/**").permitAll()
//					.antMatchers("/khabri/**").permitAll()
//					.and().httpBasic();
		httpSecurity.csrf().disable();
	}

//	@Autowired
//	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService)
//				.passwordEncoder(encoder());
//	}

	@Bean
	public BCryptPasswordEncoder encoder(){
		System.out.println("security encoder ran");
		return new BCryptPasswordEncoder();
	}

//	@Override
//	protected UserDetailsService userDetailsService() {
//		return new InMemoryUserDetailsManager(User.withDefaultPasswordEncoder().username("atul").password("pass").build());
//	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println("security authmanagerbean ran");
		return super.authenticationManagerBean();
    }

}
