package com.courtalon.gigaMvcGalerie.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

	private BCryptPasswordEncoder pe;
	
	public MyPasswordEncoder() {
		pe = new BCryptPasswordEncoder();
	}
	
	
	@Override
	public String encode(CharSequence rawPassword) {
		return pe.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return pe.matches(rawPassword, encodedPassword);
	}

}
