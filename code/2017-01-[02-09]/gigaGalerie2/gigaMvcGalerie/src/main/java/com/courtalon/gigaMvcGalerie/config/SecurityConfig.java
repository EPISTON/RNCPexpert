package com.courtalon.gigaMvcGalerie.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.courtalon.gigaMvcGalerie.utils.MyPasswordEncoder;



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

	
	/*
	 * 
	 * par defaut , la protection csrf ne s'applique pas au requette GET
	 * si jamais vous l'activez pour celle-ci
	 * vous pouvez passer le jeton par un paramtre _csrf
	 * 
	 * on l'utilisera aussi pour les post
	 * 
	 * 
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] patterns = {"/user",
							"/logout",
							"/rest/images/**",
							"/rest/tags/**",
							"/rest/licenses/**",
							"/rest/assetsources/**"};
		HttpMethod[] verbs = {
							null,
							null,
							HttpMethod.GET,
							HttpMethod.GET,
							HttpMethod.GET,
							HttpMethod.GET};
		
		http.authorizeRequests().antMatchers("/user")
								.permitAll()
								.antMatchers("/**")
								.authenticated()
								.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
								.and()
			.csrf().disable().httpBasic() // desactive la protection csrf "standard"
					.and()
			.addFilterAfter(csrfFilter(patterns, verbs), FilterSecurityInterceptor.class)
			.addFilterAfter(new CsrfGrantingFilter(), CsrfFilter.class);
			
		// filtreSecurite (dernier de la chaine) --> csrfFilter personnalise
		//				  				--> csrfGrantingFilter (formatter pour angular)
		
	}
	
	private Filter csrfFilter(String[] patterns, HttpMethod[] verbs) {
		// je creer un nouveau filtre csrf utilisant mon depot csrf personnalisé
		CsrfFilter csrfFilter = new CsrfFilter(csrfTokenRepository());
		// je lui dit de n'estre actif que sur les urls autres que celles indquées
		csrfFilter.setRequireCsrfProtectionMatcher(csrfProtectionMatcher(patterns, verbs));
		// je retourne ce filtre
		return csrfFilter;
		
	}
	
	
	// le depot, la classe chargé de gérer les jeton csrf
	// on a besoin de la personnalisée pour choisir le nom du parametre header
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
	
	// renvoie/construit le filtre utilisé pour csrf
	private NoAntPathRequestMatcher csrfProtectionMatcher(String[] patterns,
															HttpMethod[] verbs) {
		return new NoAntPathRequestMatcher(patterns, verbs);
	}

	
}
