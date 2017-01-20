package com.courtalon.BoutiqueServiceForm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;

@Service
public class ProduitServiceMetier {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	public List<Produit> listerBestProduit() {
		Iterable<Produit> allProduits = produitRepository.findAll();
		List<Produit> bestProduits = new ArrayList<>();
		int compteur = 0;
		for (Produit p : allProduits) {
			bestProduits.add(p);
			compteur++;
			if (compteur > 4)
				break;
		}
		return bestProduits;
	}
	
	public Produit trouverProduit(int id) {
		Produit p = produitRepository.findOne(id);
		if (p == null)
			throw new ProduitNotFoundExcepetion();
		return p;
	}
	
	
	public List<Produit> chercherProduit(double prixMin, double prixMax) {
		List<Produit> allProduits = new ArrayList<>();
		produitRepository.findAll().forEach(allProduits::add);
		
		return allProduits.stream()
						.filter(p -> p.getPrix() >= prixMin && p.getPrix() <= prixMax)
						.collect(Collectors.toList());
	}

	public Produit sauverProduit(Produit p) {
		if (p == null || p.getPrix() < 0
					  || p.getPoids() < 0 
					  || p.getNom() == null
					  || p.getNom().length() < 3) {
			throw new ProduitInvalideException();
		}
		return produitRepository.save(p);
	}
	
}
