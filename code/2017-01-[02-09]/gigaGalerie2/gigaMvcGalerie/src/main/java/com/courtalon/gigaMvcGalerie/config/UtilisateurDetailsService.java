package com.courtalon.gigaMvcGalerie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.courtalon.gigaMvcGalerie.metier.Utilisateur;
import com.courtalon.gigaMvcGalerie.repositories.UtilisateurRepository;



@Component
public class UtilisateurDetailsService implements UserDetailsService {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utilisateur u = utilisateurRepository.findByUsername(username);
		if (u == null) {
			throw new UsernameNotFoundException("utilisateur inconnu");
		}
		// renvoie l'utilisateur emballé dans un UserDetails
		
		return new UtilisateurDetails(u);
	}

}
