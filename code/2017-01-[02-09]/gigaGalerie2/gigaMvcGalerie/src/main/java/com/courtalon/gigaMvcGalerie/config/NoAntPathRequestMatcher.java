package com.courtalon.gigaMvcGalerie.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class NoAntPathRequestMatcher implements RequestMatcher {

	private final AndRequestMatcher andRequestMatcher;
	
	// url et verb exclus (non filtré)
	// ["rest/images", "rest/images/save",...]   ["GET", "POST",...]
	public NoAntPathRequestMatcher(String[] patterns, HttpMethod[] verbs) {
		List<RequestMatcher> negatifs = new ArrayList<>();
		int length = Math.min(patterns.length, verbs.length);
		for (int i = 0; i < length; i++) {
			if (verbs[i] == null) {
				// exclure pour tous les verbes
				negatifs.add(new NegatedRequestMatcher(
						new AntPathRequestMatcher(patterns[i])));
			}
			else {
				negatifs.add(new NegatedRequestMatcher(
						new AntPathRequestMatcher(patterns[i],
												  verbs[i].toString())));
			}
		}
		// creation du matcher final
		andRequestMatcher = new AndRequestMatcher(negatifs);
	}
	
	@Override
	public boolean matches(HttpServletRequest request) {
		return andRequestMatcher.matches(request);
	}
	
}
