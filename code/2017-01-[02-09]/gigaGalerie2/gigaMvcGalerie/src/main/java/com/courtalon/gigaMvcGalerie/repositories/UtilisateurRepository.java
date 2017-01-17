package com.courtalon.gigaMvcGalerie.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.gigaMvcGalerie.metier.Utilisateur;


public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
	
	Utilisateur findByUsername(String username);

}
