package com.courtalon.BoutiqueServiceForm.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;

public class ProduitRepositoryMock implements ProduitRepository {

	private List<Produit> produits;
	public List<Produit> getProduits() {
		return produits;
	}
	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	@Override
	public Iterable<Produit> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Produit> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Integer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Produit arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Produit> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(Integer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * 
	 * methode a "Mocker/imiter" pour nos tests 
	 * 
	 *  
	 */
	
	@Override
	public Iterable<Produit> findAll() {
		return new ArrayList<>(produits);
	}

	@Override
	public Iterable<Produit> findAll(Iterable<Integer> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 * methode a "Mocker/imiter" pour nos tests 
	 * 
	 *  
	 */

	@Override
	public Produit findOne(Integer id) {
		for (Produit p : produits) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	/*
	 * 
	 * methode a "Mocker/imiter" pour nos tests 
	 * 
	 *  
	 */

	@Override
	public <S extends Produit> S save(S p) {
		if (p.getId() > 0) {
			// simulation d'un update
			for (int i = 0; i < produits.size(); i++) {
				if (produits.get(i).getId() == p.getId()) {
					produits.set(i, p);
					return p;
				}
			}
			throw new IllegalArgumentException("produit a mettre a jour inexistant");
		}
		else {
			// simulation d'un insert
			int maxId = 0;
			for (Produit p2 : produits) {
				maxId = Math.max(maxId, p2.getId());
			}
			p.setId(maxId);
			produits.add(p);
			return p;
		}
	}

	@Override
	public <S extends Produit> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
