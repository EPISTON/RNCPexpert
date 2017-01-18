package com.courtalon.gigaMvcGalerie.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;

public class CsrfGrantingFilter implements Filter {

	Logger log = LogManager.getLogger(CsrfGrantingFilter.class);
	
	@Override
	public void destroy() {}

	/*
	 * le but de ce filtre est de fournir a angular 1 le jeton csrf
	 *  sous forme d'un cookie nommé "X-XSRF-TOKEN"
	 * 
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// on recupere le nom d'attribut du token csrf, puis le jeton lui-même
		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		String token = csrf.getToken();
		
		log.info("tokenA = " + token);
		
		if (token != null 
			&& ((HttpServletRequest)request).getRequestURI()
											.equals("gigaMvcGalerie/user")) {
			Cookie cookie = new Cookie("X-XSRF-TOKEN", token);
			cookie.setPath("/");
			((HttpServletResponse)response).addCookie(cookie);
		}
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
