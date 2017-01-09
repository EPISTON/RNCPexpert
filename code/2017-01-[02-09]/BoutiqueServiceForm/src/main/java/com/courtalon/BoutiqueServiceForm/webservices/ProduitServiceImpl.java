package com.courtalon.BoutiqueServiceForm.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;
import com.courtalon.lightspeedtransport.ExpeditionService;

@WebService(endpointInterface="com.courtalon.BoutiqueServiceForm.webservices.ProduitService")
public class ProduitServiceImpl implements ProduitService {

	@Autowired
	ProduitRepository produitRepository;
	
	@Autowired
	ExpeditionService expeditionService;
	
	@Override
	public List<Produit> listeAllProduit() {
		Iterable<Produit> prods = produitRepository.findAll();
		List<Produit> produits = new ArrayList<>();
		for (Produit p : prods) {
			produits.add(p);
		}
		return produits;
	}
	
	@Override
	public Produit produitDetails(int id) {
		return produitRepository.findOne(id);
	}
	
	@Override
	public boolean effaceProduit(int id) {
		produitRepository.delete(id);
		return true;
	}
	
	@Override
	public Produit sauverProduit(Produit produit) {
		return produitRepository.save(produit);
	}

	@Override
	public double expedierProduit(int id, String ville, int quantite) {
		Produit p = produitRepository.findOne(id);
		if (p != null) {
			return expeditionService.tarifExpedition(ville, p.getPoids() * quantite);
		}
		return 0.0;
	}
	
	
}
