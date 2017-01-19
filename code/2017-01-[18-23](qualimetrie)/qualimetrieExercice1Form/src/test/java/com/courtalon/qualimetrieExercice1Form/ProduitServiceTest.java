package com.courtalon.qualimetrieExercice1Form;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProduitServiceTest {
	private static List<Produit> sampleProducts;
	private ProduitService ps;
	
	@BeforeClass
	public static void initSamples() {
		sampleProducts = new ArrayList<>();
		sampleProducts.add(new Produit(1, "biere oceania", 9.99, 0.5, 25));
		sampleProducts.add(new Produit(2, "steak de lama", 19.99, 0.5, 0));
		sampleProducts.add(new Produit(3, "tofu tout fou", 15.99, 0.7, 12));
		sampleProducts.add(new Produit(4, "miel des carpathes", 29.99, 0.75, 10));
		sampleProducts.add(new Produit(5, "quinoa des andes", 35.99, 1.0, 0));
		sampleProducts.add(new Produit(6, "galinette parisienne", 8.99, 0.35, 150));
	}
	
	@Before
	public void setupService() {
		ps = new ProduitService();
		ps.setAllProduit(sampleProducts);
	}
	
	@After
	public void cleanService() {
		ps = null;
	}
	
	@Test
	public void testGetAllListe() {
		Produit[] expecteds = sampleProducts.toArray(new Produit[0]);
		Produit[] actuals = ps.listerProduits().toArray(new Produit[0]);
		assertArrayEquals("list of products is not the same",expecteds, actuals);
	}
	
	@Test
	public void testGetAllAvailableListe() {
		int expected = 4;
		int actual = ps.listerProduitsDisponnible().size();
		assertEquals("pas le bon nombre de produits disponnibles", expected, actual);
	}
	
	@Test
	public void testSaveProduitOk() {
		int expected = 7;
		Produit p = ps.saveProduit(7, "menthe extra forte", 29.99, 0.5, 5);
		int actual = ps.listerProduits().size();
		assertEquals("sauvegarde de produit echouée",expected, actual);
		assertEquals("sauvegarde de produit echouée (poids)",0.5, p.getPoids(), 0.0001);
	}
	
	@Test(expected=InvalidProduct.class)
	public void testSaveProduitNOk() {
		int expected = 7;
		Produit p = ps.saveProduit(7, "menthe extra forte", -29.99, 0.5, 5);
	}
	
	
	
	
	
}
