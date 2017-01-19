package com.courtalon.qualimetrieExercice1Form;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

public class ProduitTest {

	private static final double DELTA = 0.0000001;

	@Test
	public void testProduitValide() {
		int idExpected = 1;
		String nomExpected = "steak de lama";
		double prixExpected = 39.99;
		double poidsExpected = 0.75;
		int stockExpected = 15;
		
		Produit p = new Produit(idExpected, nomExpected, prixExpected,poidsExpected, stockExpected);
		assertNotNull("le produit devrait etre crée", p);
		assertEquals("l'id n'est pas identique", idExpected, p.getId());
		assertEquals("le nom n'est pas identique", nomExpected, p.getNom());
		assertEquals("le prix n'est pas identique", prixExpected, p.getPrix(), DELTA);
		assertEquals("le poids n'est pas identique", poidsExpected, p.getPoids(), DELTA);
		assertEquals("le stock n'est pas identique", stockExpected, p.getStock());
	}
	
	//---------------------------------------------
	@Test(expected=IdException.class)
	public void testSetWithBadID() {
		Produit p = new Produit(5, "table", 15.5, 15.5, 5);
		p.setId(-5);
	}
	@Test(expected=IdException.class)
	public void testCreateWithBadID() {
		Produit p = new Produit(-5, "table", 15.5, 15.5, 5);
		//fail("le produit n'aurait pas du etre crée");
	}
	
	//----------------------------------------------
	@Test(expected=NomException.class)
	public void testCreateWithTooShortNom() {
		Produit p = new Produit(5, "t", 15.5, 15.5, 5);
		//fail("le produit n'aurait pas du etre crée");
	}
	
	
	@Test(expected=NomException.class)
	public void testCreateWithTooLongNom() {
		//IntStream.range(1, 100).collect("", iaccumulator, combiner)
		Produit p = new Produit(5, "tdjklghklkjlhdgjklhkldjklhsdgkjsdghkjkhkjhdkljshkghkjdghsdfkjhdsgkdgjhdkhdgkjdfhdgksdghkhjkhkjhkjhjkhkjhkhkjhkjhkjh", 15.5, 15.5, 5);
		//fail("le produit n'aurait pas du etre crée");
	}

	@Test(expected=NomException.class)
	public void testCreateWithNullNom() {
		//IntStream.range(1, 100).collect("", iaccumulator, combiner)
		Produit p = new Produit(5, null, 15.5, 15.5, 5);
		//fail("le produit n'aurait pas du etre crée");
	}
	
	
	//------------------------
	
	
	@Test(expected=PoidsException.class)
	public void testSetWithBadPoids() {
		Produit p = new Produit(5, "table", 15.5, 4.0, 5);
		p.setPoids(150);
	}
	@Test(expected=PoidsException.class)
	public void testCreateWithBadPoids() {
		Produit p = new Produit(5, "table", 15.5, -4.5, 5);
	}

	@Test(expected=PrixException.class)
	public void testSetWithBadPrix() {
		Produit p = new Produit(5, "table", 15.5, 4.0, 5);
		p.setPrix(-5.0);
	}
	@Test(expected=PrixException.class)
	public void testCreateWithBadPrix() {
		Produit p = new Produit(5, "table", 20000, 4.5, 5);
	}

	
	@Test(expected=StockException.class)
	public void testSetWithBadStock() {
		Produit p = new Produit(5, "table", 15.5, 15.5, 5);
		p.setStock(-5);
	}
	@Test(expected=StockException.class)
	public void testCreateWithBadStock() {
		Produit p = new Produit(5, "table", 15.5, 15.5, -5);
	}

}
