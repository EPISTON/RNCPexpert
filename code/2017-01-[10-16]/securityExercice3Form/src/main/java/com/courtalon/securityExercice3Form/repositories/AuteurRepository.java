package com.courtalon.securityExercice3Form.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.securityExercice3Form.metier.Auteur;

public interface AuteurRepository extends CrudRepository<Auteur, Integer> {

	Auteur findByNom(String nom);
	
}
