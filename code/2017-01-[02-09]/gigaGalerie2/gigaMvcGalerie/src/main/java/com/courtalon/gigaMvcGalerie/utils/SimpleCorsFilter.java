package com.courtalon.gigaMvcGalerie.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;



public class SimpleCorsFilter extends GenericFilterBean {
	private static Logger log = LogManager.getLogger(SimpleCorsFilter.class);
	 @Override
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;   
	        
	        //String origin = request.getRemoteHost();
	        log.info("cors h: " + request.getRemoteHost());
	        log.info("cors a: " + request.getRemoteAddr());
	        log.info("cors p: " + request.getRemotePort());
	        String origin =  request.getHeader("Origin");
	        log.info("cors o: " + origin);
	        if (origin == null)
	        	response.setHeader("Access-Control-Allow-Origin", "*");
	        else
	        	response.setHeader("Access-Control-Allow-Origin", origin);
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Range, Content-Disposition, Content-Type, Authorization, X-CSRF-TOKEN, X-XSRF-TOKEN");  
	        response.setHeader("Access-Control-Allow-Credentials", "true");
	        
	        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            chain.doFilter(req, res);
	        }           
	    }

}
