package com.courtalon.gigaMvcGalerie.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.courtalon.gigaMvcGalerie.metier.AssetSource;
import com.courtalon.gigaMvcGalerie.metier.LicenseType;
import com.courtalon.gigaMvcGalerie.repositories.AssetRepository;
import com.courtalon.gigaMvcGalerie.repositories.AssetSourceRepository;
import com.courtalon.gigaMvcGalerie.repositories.ImageRepository;
import com.courtalon.gigaMvcGalerie.repositories.LicenseTypeRepository;
import com.courtalon.gigaMvcGalerie.repositories.TagRepository;
import com.courtalon.gigaMvcGalerie.utils.FileStorageManager;
import com.courtalon.gigaMvcGalerie.utils.HibernateAwareObjectMapper;
import com.courtalon.gigaMvcGalerie.web.LicenseAndSourceController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


// j'indique avec @ContextConfiguration ou aller chercher
// la configuration spring a utiliser pour les tests
// on peu lui indiquer par exemple comme ici une classe
// annotée avec @Configuration,
// ou l'emplacement d'un fichier xml spring
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfigController.class})
public class LicenseAndSourceTest {

	
	// ici, ce seront les faux repository License et source qui seront injectés
	// les memes que ceux utilisé par le controller qu'on veut tester
	@Autowired
	private LicenseTypeRepository licenseTypeRepositoryMock;
	@Autowired
	private AssetSourceRepository assetSourceRepositoryMock;
	
	//le veritable controller LicenseAndSource que l'on veut tester 
	@Autowired
	private LicenseAndSourceController licenseAndSourceController;
	
	// ce même controller encapsulé dans un objet qui simule/mock
	// l'environnement web pour nous permettre de faire des tests dessus
	/*
	 * 		+-----------------------+
	 * 		|	mockMvcController	|
	 * 		|	+---------------+	|
	 *      |   |  Controller	|---|-----> LicenseTypeRepositoryMock
	 *      |   +---------------+	|
	 * 		+-----------------------+
	 * 
	 */
	private MockMvc mocklicenseAndSourceController;
	
	@Before
	public void setupMock() {
		// je declare ici via le mockMvcBuilder
		// la gestion des PageableDefault/etc
		// et du json jackson serialiser
		//
		// normalement, c'est déclaré via le spring-mvc-servlet.xml
		// mais ici (bug?) ce n'est pas pris en compte si on le laisse dans le xml
		// donc, on fait l'equivalent via les méthode
		// setCustomArgumentResolvers et setMessageConverters
		mocklicenseAndSourceController = MockMvcBuilders.standaloneSetup(licenseAndSourceController)
				 .setCustomArgumentResolvers(
						 new PageableHandlerMethodArgumentResolver())
				 .setMessageConverters(
						 new MappingJackson2HttpMessageConverter(new HibernateAwareObjectMapper()))
				 .build();
		// je rinitialise mes mock repository dont je me sert
		reset(licenseTypeRepositoryMock);
		reset(assetSourceRepositoryMock);
		
	}
	
	private List<LicenseType> getSampleLicenses() {
		// meme donnée que celle initialisé par le DatabaseInitialiser
		List<LicenseType> licenses = new ArrayList<>();
		licenses.add(new LicenseType(1, "NONE", "aucune license"));
		licenses.add(new LicenseType(2, "CC-BY 3.0", "Share, Adapt, even commercially, can not restrict, attribute"));
		licenses.add(new LicenseType(3, "GPL 3.0", "license GPL v3"));
		licenses.add(new LicenseType(4, "GPL 2.0", "license GPL v2"));
		licenses.add(new LicenseType(5, "CC0 1.0", "public domain"));
		licenses.add(new LicenseType(6, "LGPL 2.1", "license LGPL v2.1"));
		licenses.add(new LicenseType(7, "LGPL 3.0", "license LGPL v3"));
		return licenses;
	}
	
	private Page<AssetSource> getSamplepageAssetSources() {
		// meme donnée que celle initialisé par le DatabaseInitialiser
		List<AssetSource> sources = new ArrayList<>();
		sources.add(new AssetSource(1, "inconnue", "http://localhost", "source inconnue"));
		sources.add(new AssetSource(2, "google image", "http://www.google.com/", "from google image service"));
		return new PageImpl<>(sources);
	}
	
	@Test
	public void testLicenseList() {
		// mise ne place du test
		// si on appelle findAll(Pageable) sur le mockLicenseRepository
		// renvoyer une Page contenant la liste echantillon de license
		Pageable p = any();
		when(licenseTypeRepositoryMock.findAll(p))
									  .thenReturn(new PageImpl<>(getSampleLicenses()));
		// on execute le test
		// GET -> /rest/licenses
		// devrait renvoyer une page en json avec les licenses
		try {
			MvcResult me = mocklicenseAndSourceController.perform(get("/rest/licenses"))
										.andExpect(status().isOk())
										.andExpect(content().contentType("application/json"))
										.andExpect(jsonPath("$.content", hasSize(7)))
										.andExpect(jsonPath("$.content[2].name", is("GPL 3.0")))
										.andReturn();
			System.out.println(me.getResponse().getContentAsString());
		} catch (Exception e) {
			fail("erreur dans le test " + e);
		}
		// verifie que le mock a bien été appelé correctement
		verify(licenseTypeRepositoryMock, times(1)).findAll(new PageRequest(0, 50, null));
		
	}

	@Test
	public void testSourceList() {
		Pageable p = any();
		when(assetSourceRepositoryMock.findAll(p))
									  .thenReturn(getSamplepageAssetSources());
		// on execute le test
		// GET -> /rest/assetsources
		// devrait renvoyer une page en json avec les licenses
		try {
			MvcResult me = mocklicenseAndSourceController.perform(get("/rest/assetsources"))
										.andExpect(status().isOk())
										.andExpect(content().contentType("application/json"))
										.andExpect(jsonPath("$.content", hasSize(2)))
										.andExpect(jsonPath("$.content[1].name", is("google image")))
										.andReturn();
			System.out.println(me.getResponse().getContentAsString());
		} catch (Exception e) {
			fail("erreur dans le test " + e);
		}
					
	}

	
}
