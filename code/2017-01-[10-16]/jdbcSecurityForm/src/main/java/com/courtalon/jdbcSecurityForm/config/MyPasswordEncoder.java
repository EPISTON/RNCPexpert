package com.courtalon.jdbcSecurityForm.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

	private static final String SALT = "c712a83ece9a79db8fb21c64b84df39f";
	
	@Override
	public String encode(CharSequence rawPassword) {
		return DigestUtils.sha256Hex(rawPassword + SALT);
	
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return DigestUtils.sha256Hex(rawPassword + SALT).equalsIgnoreCase(encodedPassword);
	}

}
