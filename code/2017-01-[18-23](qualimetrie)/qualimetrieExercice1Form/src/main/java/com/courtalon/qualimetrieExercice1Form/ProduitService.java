package com.courtalon.qualimetrieExercice1Form;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProduitService {

	private List<Produit> produits;
	
	
	public ProduitService() {
		produits = new ArrayList<>();
	}

	public void setAllProduit(List<Produit> produits) {
		this.produits = new ArrayList<>(produits);
	}
	
	public List<Produit> listerProduits() {
		return new ArrayList<Produit>(produits);
	}
	
	public List<Produit> listerProduitsDisponnible() {
		if (produits == null)
			return null;
		else
			return produits.stream().filter(p -> p.getStock() > 0)
								.collect(Collectors.toList());
	}
	
	public Produit saveProduit(int id, String nom, double prix, double poids, int stock) {
		Produit p = null;
		try {
			p = new Produit(id, nom, prix, poids, stock);
		} catch(RuntimeException ex) {
			throw new InvalidProduct("produit invalide", ex);
		}
		if (produits == null)
			produits = new ArrayList<>();
		produits.add(p);
		return p;
	}
	
}
