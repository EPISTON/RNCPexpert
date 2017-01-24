package com.courtalon.BoutiqueServiceForm.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;
import com.courtalon.BoutiqueServiceForm.services.ProduitNotFoundExcepetion;
import com.courtalon.BoutiqueServiceForm.services.ProduitServiceMetier;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration //(locations="classpath*:testContextMock.xml")
public class ProduitServiceMockitoTest {
	
	/*
	 * configuration spring speciale
	 */
	
	// cette configuration combine ce qui est déclaré dans la classe '@bean'
	// avec ce qui vient du fichier xml grace a @ImportResource
	@Configuration
	@ImportResource("classpath*:testContextMock.xml")
	public static class TestConfiguration {
		
		@Bean
		@Primary
		ProduitRepository produitMock() {
			// renvoie un mock "vide" de ProduitRepository
			return mock(ProduitRepository.class);
		}
	}
	
	/*
	 * ce qui concerne la suite de test
	 * 
	 */
	
	@Autowired
	private ProduitRepository produitRepositoryMock;
	
	@Autowired
	private ProduitServiceMetier produitServiceMetier;
	
	// methode utilitaire pour obtenir une liste de produits echantillon
	private List<Produit> getSampleListProduit1() {
		List<Produit> produits = new ArrayList<>();
		produits.add(new Produit(1, "biere oceania", 15.5, 0.75));
		produits.add(new Produit(2, "miel des carpathes", 25.5, 1.75));
		produits.add(new Produit(3, "quinoa des andes", 29.99, 1.5));
		produits.add(new Produit(4, "steak de lama", 35.5, 0.95));
		produits.add(new Produit(5, "tofu tout fou", 10.99, 1.0));
		produits.add(new Produit(6, "pain sans gluten", 4.99, 0.75));
		return produits;
	}
	
	@Before
	public void setupMock() {
		// reinitialise le mock
		// enleve tous les when, verify, etc...
		reset(produitRepositoryMock);
	}
	
	
	@Test
	public void testBestProduit() {
		when(produitRepositoryMock.findAll())
			.thenReturn(getSampleListProduit1());
		
		//
		//	^^^^^^^^^^^^^^^^^^^^^^^^^
		//  preparation du test de service metier
		//	en particulier en configurant le mock qu'il utilisera
		
		// test en lui même
		List<Produit> best = produitServiceMetier.listerBestProduit();
		
		//	verification du déroulement du test
		//  verifier si les valeurs attendues sont bien renvoyées
		//  et si les bonnes méthodes on bien été appelées par le produitServiceMetier
		//  vvvvvvvvvvvvvvvvvvvvvv
		//
		assertEquals("devrais renvoyer les 4 premier produits", 4, best.size());
		assertEquals("on devrait ici avoir le quinoa",
				"quinoa des andes", best.get(2).getNom());
		
		verify(produitRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void testChercherProduit() {
		when(produitRepositoryMock.findAll())
			.thenReturn(getSampleListProduit1());
		
		List<Produit> produits = produitServiceMetier.chercherProduit(5.0, 29.0);
		assertEquals("le nombre de produit devrait etre 3", 3, produits.size());
		for(Produit p : produits) {
			if (p.getPrix() < 5.0 || p.getPrix() > 29.0)
				fail("un produit ne respecte pas nos critere de prix");
		}
		
		verify(produitRepositoryMock, times(1)).findAll();
	}

	@Test
	public void testFindProduitNotExisting() {
		when(produitRepositoryMock.findOne(4))
			.thenReturn(null);
		when(produitRepositoryMock.findOne(5))
			.thenReturn(new Produit(5, "hoho", 5.5, 5.5));
		
		
		try {
			Produit p = produitServiceMetier.trouverProduit(4);
			
			fail("devrait avoir échoué avec une exepetion produitNotFound");
		}
		catch( ProduitNotFoundExcepetion ex) {}
		
		verify(produitRepositoryMock, times(1)).findOne(4);
	}

	

}
