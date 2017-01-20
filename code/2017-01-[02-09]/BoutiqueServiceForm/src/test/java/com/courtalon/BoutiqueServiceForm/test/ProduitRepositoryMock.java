package com.courtalon.BoutiqueServiceForm.test;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;

public class ProduitRepositoryMock implements ProduitRepository {

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
		// TODO Auto-generated method stub
		return null;
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
	public Produit findOne(Integer arg0) {
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
	public <S extends Produit> S save(S arg0) {
		return arg0;
	}

	@Override
	public <S extends Produit> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
