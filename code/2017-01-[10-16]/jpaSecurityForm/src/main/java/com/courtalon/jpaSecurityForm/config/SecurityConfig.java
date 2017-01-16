package com.courtalon.jpaSecurityForm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.courtalon.jpaSecurityForm.utils.MyPasswordEncoder;

// @Configuration indique a spring que cette classe sert à configurer notre application
@Configuration
@EnableWebSecurity // activer la securité dédiée au web-app
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UtilisateurDetailsService utilisateurDetailsService;

	@Autowired
	private MyPasswordEncoder myPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(utilisateurDetailsService)
			.passwordEncoder(myPasswordEncoder);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**")
									.hasRole("ADMIN")
								.antMatchers("/user/**")
									.hasRole("USER")
								.antMatchers("/public/**", "/", "/login")
									.permitAll().and()
								.logout().logoutUrl("/logout")
										 .logoutSuccessUrl("/public/")
										 .and()
								.formLogin().loginPage("/public/login")
											.usernameParameter("username")
											.passwordParameter("password")
											.loginProcessingUrl("/login")
								.and() // on peut spécifier notre propre formulaire
								.httpBasic();
		
		
		/*
		 * quelques regles a respecter
		 *  -> les champs on un nom par defaut -> username, password
		 *  -> il y a aussi une url de soumission du formulaire par defaut /login post
		 *  
		 */
	}

	
}
