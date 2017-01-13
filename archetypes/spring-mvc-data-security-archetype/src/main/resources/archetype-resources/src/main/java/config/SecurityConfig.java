#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @Configuration indique a spring que cette classe sert à configurer notre application
@Configuration
@EnableWebSecurity // activer la securité dédiée au web-app
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// authentification avec des compte en mémoire
		// UNIQUEMENT POUR TEST, PAS EN VRAIS!!!
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("USER", "ADMIN").and()
			.withUser("vincent").password("toto1234").roles("USER");
		
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
