package com.courtalon.gigaMvcGalerie.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.courtalon.gigaMvcGalerie.metier.Image;


/*
 * ImageRepository contient les méthodes standard de PagingAndSortingRepository
 * adaptée aux images
 * + les methodes listées dans l'interface (ici aucune)
 * qui seront générée pas spring data
 * + des methodes custom indiquée dans l'interface ImageRepositoryCustom
 * spring data ne fournit pas ce code la
 * spring data cherchera une classe ImageRepositoryImpl (ecrit par nous) qui fournit ces methodes
 * 
 */

public interface ImageRepository  extends PagingAndSortingRepository<Image, Integer>, ImageRepositoryCustom
{

}
