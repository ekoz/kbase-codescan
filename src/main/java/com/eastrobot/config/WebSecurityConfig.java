/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 上午11:21:47
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.admin.username}")
	private String adminUsername;
	@Value("${security.admin.password}")
	private String adminPassword;
	@Value("${security.user.username}")
	private String username;
	@Value("${security.user.password}")
	private String password;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/sample/**").access("hasRole('ADMIN')")
			.anyRequest().authenticated();
		//http://blog.csdn.net/u012373815/article/details/55047285
		http.csrf().disable();
		super.configure(http);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/DATAS/**");
		super.configure(web);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser(adminUsername).password(adminPassword).roles("ADMIN")
		.and()
		.withUser(username).password(password).roles("USER");
		
		//this.disableLocalConfigureAuthenticationBldr = true;
		//super.configure(auth);
	}
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
}
