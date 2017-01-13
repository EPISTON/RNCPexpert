package com.courtalon.jpaSecurityForm.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.jpaSecurityForm.metier.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
	
	Utilisateur findByUsername(String username);

}
