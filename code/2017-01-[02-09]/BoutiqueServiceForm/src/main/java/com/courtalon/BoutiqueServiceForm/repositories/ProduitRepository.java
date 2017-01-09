package com.courtalon.BoutiqueServiceForm.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.courtalon.BoutiqueServiceForm.metier.Produit;

public interface ProduitRepository extends PagingAndSortingRepository<Produit, Integer> {

}
