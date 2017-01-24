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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;
import com.courtalon.BoutiqueServiceForm.web.IndexController;

// les methodes utilitaires pour tester un controller spring mvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.Assert.*;

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
											 .build();
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
	
}
