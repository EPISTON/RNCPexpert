package com.courtalon.securityExercice3Form.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.courtalon.securityExercice3Form.metier.Auteur;
import com.courtalon.securityExercice3Form.repositories.AuteurRepository;

@Component
public class AuteurDetailsService implements UserDetailsService {

	@Autowired
	private AuteurRepository auteurRepository;
	
	@Override
	public UserDetails loadUserByUsername(String nom) throws UsernameNotFoundException {
		Auteur a = auteurRepository.findByNom(nom);
		if (a == null) {
			throw new UsernameNotFoundException("auteur inconnu");
		}
		return new AuteurDetails(a);
	}

}
