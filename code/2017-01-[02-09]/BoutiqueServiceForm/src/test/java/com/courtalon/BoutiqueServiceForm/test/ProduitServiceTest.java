package com.courtalon.BoutiqueServiceForm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;
import com.courtalon.BoutiqueServiceForm.services.ProduitInvalideException;
import com.courtalon.BoutiqueServiceForm.services.ProduitNotFoundExcepetion;
import com.courtalon.BoutiqueServiceForm.services.ProduitServiceMetier;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:testContext.xml")
public class ProduitServiceTest {
	
	private static final double DELTA = 0.000001;
	
	@Autowired
	private ProduitServiceMetier produitServiceMetier;

	@Autowired 
	private ProduitRepositoryMock produitRepositoryMock;
	
	@Before
	public void prepareMock() {
		List<Produit> produits = new ArrayList<>();
		produits.add(new Produit(1, "biere oceania", 15.5, 0.75));
		produits.add(new Produit(2, "miel des carpathes", 25.5, 1.75));
		produits.add(new Produit(3, "quinoa des andes", 29.99, 1.5));
		produits.add(new Produit(4, "steak de lama", 35.5, 0.95));
		produits.add(new Produit(5, "tofu tout fou", 10.99, 1.0));
		produits.add(new Produit(6, "pain sans gluten", 4.99, 0.75));
		produitRepositoryMock.setProduits(produits);
	}
	
	
	@Test
	public void testFindBestProduit() {
		List<Produit> produits = produitServiceMetier.listerBestProduit();
		assertEquals("le nombre de produit devrait etre 4", 4, produits.size());
		assertEquals("le quatrieme produit devrait etre steak de lama", "steak de lama", produits.get(3).getNom());
	}

	@Test
	public void testFindProduit() {
		Produit p = produitServiceMetier.trouverProduit(5);
		assertEquals("id ne correspond pas", 5, p.getId());
		assertEquals("nom ne correspond pas", "tofu tout fou", p.getNom());
		assertEquals("prix ne correspond pas", 10.99, p.getPrix(), DELTA);
		assertEquals("poids ne correspond pas", 1.0, p.getPoids(), DELTA);
	}
	
	@Test(expected=ProduitNotFoundExcepetion.class)
	public void testFindProduitNOK() {
		Produit p = produitServiceMetier.trouverProduit(10);
	}
	
	@Test
	public void testChercherProduit() {
		List<Produit> produits = produitServiceMetier.chercherProduit(5.0, 29.0);
		assertEquals("le nombre de produit devrait etre 3", 3, produits.size());
		for(Produit p : produits) {
			if (p.getPrix() < 5.0 || p.getPrix() > 29.0)
				fail("un produit ne respecte pas nos critere de prix");
		}
	}
	
	@Test
	public void testChercherProduitAucun() {
		List<Produit> produits = produitServiceMetier.chercherProduit(50.0, 80.0);
		assertEquals("le nombre de produit devrait etre 0", 0, produits.size());
	}
	
	
	@Test
	public void testSaveExistingProduitOk() {
		Produit newProduit = new Produit(4, "steak de lama xxl", 39.99, 1.0);
		Produit p = produitServiceMetier.sauverProduit(newProduit);
		assertEquals("l'id ne correspond pas", 4, p.getId());
		assertEquals("nom ne correspond pas", "steak de lama xxl", p.getNom());
		assertEquals("prix ne correspond pas", 39.99, p.getPrix(), DELTA);
		assertEquals("poids ne correspond pas", 1.0, p.getPoids(), DELTA);
	}

	@Test(expected=ProduitInvalideException.class)
	public void testSaveExistingProduitNameNOk() {
		Produit newProduit = new Produit(4, "st", 39.99, 1.0);
		Produit p = produitServiceMetier.sauverProduit(newProduit);
	}

	@Test(expected=ProduitInvalideException.class)
	public void testSaveExistingProduitPrixNOk() {
		Produit newProduit = new Produit(4, "steak de lama", -0.5, 1.0);
		Produit p = produitServiceMetier.sauverProduit(newProduit);
	}

	@Test(expected=ProduitInvalideException.class)
	public void testSaveExistingProduitPoidsNOk() {
		Produit newProduit = new Produit(4, "steak de lama", 39.99, -0.5);
		Produit p = produitServiceMetier.sauverProduit(newProduit);
	}

	
}
