package com.courtalon.gigaMvcGalerie.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.courtalon.gigaMvcGalerie.metier.Tag;



public interface TagRepository extends TagRepositoryCustom, PagingAndSortingRepository<Tag, Integer>
{
	public static final String UPLOADED = "system_uploaded";
	
	// ici, spring data analysera le nom de la méthode
	// et génrera automatiquement le code correspondant,
	// ici, une close where sur l'attribut libelle
	Tag findByLibelle(String libelle);
	// je veux une requette de recherche de tag par libelle (like)
	// libelleContaining -> like libelle
	// je veux filtrer les tags par propriété systeme
	// And -> SystemTag
	// l'argument Pageable continet les informations de pagination
	// le numero et taille de la page
	Page<Tag> findByLibelleContainingAndSystemTag(String libelle, boolean systemTag, Pageable page);
	Tag findByLibelleAndSystemTag(String libelle, boolean systemTag);
	Tag findByIdAndSystemTag(int id, boolean systemTag);
	Page<Tag> findBySystemTag(boolean systemTag, Pageable page);
	
	
}
