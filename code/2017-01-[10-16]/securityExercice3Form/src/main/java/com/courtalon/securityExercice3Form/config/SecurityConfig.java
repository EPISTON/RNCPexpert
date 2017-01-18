package com.courtalon.securityExercice3Form.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.courtalon.securityExercice3Form.utils.MyPasswordEncoder;


// @Configuration indique a spring que cette classe sert à configurer notre application
@Configuration
@EnableWebSecurity // activer la securité dédiée au web-app
@EnableGlobalMethodSecurity(prePostEnabled=true,proxyTargetClass=true) // activer la sécurité des méthodes
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuteurDetailsService auteurDetailsService;

	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(auteurDetailsService)
			.passwordEncoder(myPasswordEncoder);
	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**")
									.hasRole("ADMIN")
								.antMatchers("/posts/**")
									.hasRole("USER")
								.antMatchers("/public/**", "/", "/welcome", "/registerForm", "/register")
									.permitAll().and()
								.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.toString()))
										 .logoutSuccessUrl("/welcome")
										 .and()
								.formLogin().loginPage("/loginForm")
											.usernameParameter("nom")
											.passwordParameter("password")
											.loginProcessingUrl("/login")
								.and()
								.httpBasic();
	}

	
}
