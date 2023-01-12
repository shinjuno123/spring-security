package com.luv2code.springsecurity.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig {
	
	// add a reference to our security data source
	@Autowired
	private DataSource securityDataSource;
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		return new JdbcUserDetailsManager(securityDataSource);
	}
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http.authorizeRequests(configurur->
										configurur
												.antMatchers("/").hasRole("EMPLOYEE")
												.antMatchers("/leaders/**").hasRole("MANAGER")
												.antMatchers("/systems/**").hasRole("ADMIN"))
					
				
					.formLogin(configurer->
								configurer
										.loginPage("/showMyLoginPage")
										.loginProcessingUrl("/authenticateTheUser")
										.permitAll())
					.logout(configurer->
								configurer
										.permitAll())
					.exceptionHandling(configurer ->
					configurer
						.accessDeniedPage("/access-denied"))
					
					.build();
	}
	
	

}
