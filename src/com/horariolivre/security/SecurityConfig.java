package com.horariolivre.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ComponentScan(value="com.horariolivre")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth
    		.authenticationProvider(authenticationProvider);
    }
    
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.csrf()
    			.disable()
    		.authorizeRequests()
    			.antMatchers("/acesso/erro-login").permitAll()
    			.antMatchers("/instala/app").permitAll()
    			.antMatchers("/bootstrap/**", "/extras/**", "/jquery/**").permitAll()
    			.anyRequest().authenticated()
    			.and()
			.formLogin()
				.loginPage("/acesso/login").permitAll()
				.loginProcessingUrl("/processaLogin").permitAll()
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new CustomAuthenticationSuccessHandler())
				.failureHandler(new CustomAuthenticationFailureHandler())
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/acesso/login").permitAll();
    }
    
}
