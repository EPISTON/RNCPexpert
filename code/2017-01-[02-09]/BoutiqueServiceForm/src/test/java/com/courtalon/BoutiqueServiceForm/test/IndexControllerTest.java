package com.courtalon.BoutiqueServiceForm.test;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.courtalon.BoutiqueServiceForm.metier.Produit;
import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;
import com.courtalon.BoutiqueServiceForm.web.IndexController;

// les methodes utilitaires pour tester un controller spring mvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class IndexControllerTest {

	
	@Configuration
	@ImportResource("classpath*:testContextController.xml")
	public static class TestConfiguration {
		
		@Bean
		@Primary
		ProduitRepository produitMock() {
			// renvoie un mock "vide" de ProduitRepository
			return mock(ProduitRepository.class);
		}
	}
	
	// le faux repository pour les produits
	@Autowired
	private ProduitRepository produitRepositoryMock;
	
	//le veritable controller index que l'on veut tester 
	@Autowired
	private IndexController indexController;
	
	// ce même controller encapsuler dans un objet qui simule/mock
	// l'environnement web pour nous permettre de faire des tests dessus
	private MockMvc mockindexController;
	
	@Before
	public void setupMock() {
		mockindexController = MockMvcBuilders.standaloneSetup(indexController)
											 .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
											 .setMessageConverters(
													 new MappingJackson2HttpMessageConverter())
											 .build();
		reset(produitRepositoryMock);
	}
	
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
	
	@Test
	public void testHome() {
		try {
			// test d'une requette GET sur /Index 
			mockindexController
				.perform(get("/Index"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("message", "bonjour depuis spring 3 mvc"))
				.andExpect(forwardedUrl("bonjour"));
		}
		catch(Exception ex) {
			fail("declenchement d'une exception au test " + ex.getMessage());
		}
	}

	@Test
	public void testBonjour2() {
		try {
			mockindexController
				.perform(
						post("/bonjour2")
							.param("name", "patrick")
						)
				.andExpect(status().isOk())
				.andExpect(model().attribute("message", "bonjour patrick"))
				.andExpect(forwardedUrl("bonjour"));
		}
		catch(Exception ex) {
			fail("declenchement d'une exception au test " + ex.getMessage());
		}
	}

	@Test
	public void testListeProduit() {
		when(produitRepositoryMock.findAll())
			.thenReturn(getSampleListProduit1());
		
		//-------------------------------------------
		try {
			
			MvcResult mvr = mockindexController
				.perform(get("/produit/liste"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.content", hasSize(6)))
				.andExpect(jsonPath("$.content[0].nom", is("biere oceania")))
				.andReturn();
			System.out.println(mvr.getResponse().getContentAsString());
		}
		catch(Exception ex) {
			fail("declenchement d'une exception au test " + ex.getMessage());
		}
	}
	
	
}
