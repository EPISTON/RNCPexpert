package com.courtalon.securityExercice1Form.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.courtalon.securityExercice1Form.metier.Auteur;

public interface AuteurRepository extends PagingAndSortingRepository<Auteur, Integer> {

}
