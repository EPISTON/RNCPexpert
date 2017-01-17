package com.courtalon.gigaMvcGalerie.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.courtalon.gigaMvcGalerie.metier.Role;
import com.courtalon.gigaMvcGalerie.metier.Utilisateur;



public class UtilisateurDetails implements UserDetails {

	private Utilisateur utilisateur;
	
	public UtilisateurDetails(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role r : utilisateur.getRoles() ) {
			authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
		}
		return authorities;
	}
	
	@Override
	public String getPassword() {return utilisateur.getPassword();}

	@Override
	public String getUsername() {return utilisateur.getUsername();}

	@Override
	public boolean isAccountNonExpired() {return true;}

	@Override
	public boolean isAccountNonLocked() {return true;}

	@Override
	public boolean isCredentialsNonExpired() {return true;}

	@Override
	public boolean isEnabled() {return utilisateur.isEnabled();}

}
