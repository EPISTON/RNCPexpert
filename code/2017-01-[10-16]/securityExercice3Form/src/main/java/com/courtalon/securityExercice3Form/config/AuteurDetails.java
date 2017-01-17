package com.courtalon.securityExercice3Form.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.courtalon.securityExercice3Form.metier.Auteur;
import com.courtalon.securityExercice3Form.metier.Role;

public class AuteurDetails implements UserDetails {

	private Auteur auteur;
	
	public AuteurDetails(Auteur auteur) {
		this.auteur = auteur;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// je pourrais tout a fait ne pas utiliser des roles
		// mais tout autre mecanisme pour indiquer les droits de mon compte
		return auteur.getRoles().stream()
								.map(r -> new SimpleGrantedAuthority(r.getRoleName()))
								.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return auteur.getPassword();
	}

	@Override
	public String getUsername() {
		return auteur.getNom();
	}

	@Override
	public boolean isAccountNonExpired() {return true;}

	@Override
	public boolean isAccountNonLocked() { return true;}

	@Override
	public boolean isCredentialsNonExpired() { return true;}

	@Override
	public boolean isEnabled() {
		return auteur.isEnabled();
	}

}
