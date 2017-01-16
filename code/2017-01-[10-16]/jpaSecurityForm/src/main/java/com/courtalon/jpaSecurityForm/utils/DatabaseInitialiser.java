package com.courtalon.jpaSecurityForm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.courtalon.jpaSecurityForm.metier.Role;
import com.courtalon.jpaSecurityForm.metier.Utilisateur;
import com.courtalon.jpaSecurityForm.repositories.RoleRepository;
import com.courtalon.jpaSecurityForm.repositories.UtilisateurRepository;

@Service
public class DatabaseInitialiser implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (utilisateurRepository.count() == 0) {
			// la base est vide, creons les utilisateurs de base
			//BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			
			Role r1 = new Role(0, "ROLE_ADMIN");
			Role r2 = new Role(0, "ROLE_USER");
			r1 = roleRepository.save(r1);
			r2 = roleRepository.save(r2);
			
			// utilisateur par defaut
			// pour bonne pratique, il faudrait forcer
			// a reinitialiser le mot de passe a la première connection
			Utilisateur u1 = new Utilisateur(0,
											"admin",
											"admin@admin.org",
											myPasswordEncoder.encode("admin"),
											true);
			Utilisateur u2 = new Utilisateur(0,
					"patrick",
					"patrick@admin.org",
					myPasswordEncoder.encode("toto1234"),
					true);
			// admin a les 2 roles
			u1.getRoles().add(r1);
			u1.getRoles().add(r2);
			// patrick est seulement utilisateur
			u2.getRoles().add(r2);
			
			utilisateurRepository.save(u1);
			utilisateurRepository.save(u2);
		}
		
	}

}
