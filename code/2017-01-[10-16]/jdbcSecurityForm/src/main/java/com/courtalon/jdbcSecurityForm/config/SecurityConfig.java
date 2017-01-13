package com.courtalon.jdbcSecurityForm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration indique a spring que cette classe sert à configurer notre application
@Configuration
@EnableWebSecurity // activer la securité dédiée au web-app
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.usersByUsernameQuery(
					"select username,password,enabled from utilisateur where username=?")
			.authoritiesByUsernameQuery(
					"select u.username, r.role from utilisateur u"
					+ " inner join utilisateur_role ur on u.id = ur.utilisateur_id "
					+ " inner join role r on r.id = ur.role_id"
					+ " where u.username=?")
			.dataSource(dataSource);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**")
									.hasRole("ADMIN")
								.antMatchers("/user/**")
									.hasRole("USER")
								.antMatchers("/public/**", "/")
									.permitAll().and()
								.logout().logoutUrl("/logout")
										 .logoutSuccessUrl("/public/")
										 .and()
								.formLogin().and()
								.httpBasic();
	}

	
}
