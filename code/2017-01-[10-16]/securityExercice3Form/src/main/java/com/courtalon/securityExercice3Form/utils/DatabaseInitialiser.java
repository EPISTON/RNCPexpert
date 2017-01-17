package com.courtalon.securityExercice3Form.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.courtalon.securityExercice3Form.metier.Auteur;
import com.courtalon.securityExercice3Form.metier.Role;
import com.courtalon.securityExercice3Form.repositories.AuteurRepository;
import com.courtalon.securityExercice3Form.repositories.RoleRepository;

@Service
public class DatabaseInitialiser implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private AuteurRepository auteurRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (roleRepository.count() == 0) {
			// la base est vide, creons les utilisateurs de base
			//BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
			
			Role r1 = new Role(0, "ROLE_ADMIN");
			Role r2 = new Role(0, "ROLE_USER");
			r1 = roleRepository.save(r1);
			r2 = roleRepository.save(r2);
			
			// utilisateur par defaut
			// pour bonne pratique, il faudrait forcer
			// a reinitialiser le mot de passe a la première connection
			Auteur a1 = new Auteur(0,
											"admin",
											"admin@admin.org",
											myPasswordEncoder.encode("admin1234"),
											true);
		
			// admin a les 2 roles
			a1.getRoles().add(r1);
			a1.getRoles().add(r2);
			auteurRepository.save(a1);
		}
		
	}

}
