package com.courtalon.gigaMvcGalerie.test;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import com.courtalon.gigaMvcGalerie.repositories.AssetRepository;
import com.courtalon.gigaMvcGalerie.repositories.AssetSourceRepository;
import com.courtalon.gigaMvcGalerie.repositories.ImageRepository;
import com.courtalon.gigaMvcGalerie.repositories.LicenseTypeRepository;
import com.courtalon.gigaMvcGalerie.repositories.TagRepository;
import com.courtalon.gigaMvcGalerie.utils.FileStorageManager;

// la classe de configuration de notre environnement de test
// au sens spring
// en la mettant dans sa propre classe séparée, je peu la réutiliser
// pour de multiples classes de test différentes

@Configuration
@ImportResource("classpath*:testContext.xml")
public class TestConfigController {
	
	// je declare mes "faux" repository avec @Bean
	// pour qu'ils soient a disposition de mes controller pour les tests
	// au cas ou  les vrais repository JPA sont présent dans le context spring de test
	// j'ajoute @Primary pour que mes faux repository ai la priorité sur les autres
	
	@Bean @Primary
	public LicenseTypeRepository licenseTypeRepositoryMock() {return mock(LicenseTypeRepository.class);}
	@Bean @Primary
	public AssetSourceRepository assetSourceRepositoryMock() {	return mock(AssetSourceRepository.class);}
	@Bean @Primary
	public ImageRepository imageRepositoryMock() {	return mock(ImageRepository.class);}
	@Bean @Primary
	public TagRepository tagRepositoryMock() {	return mock(TagRepository.class);}
	@Bean @Primary
	public AssetRepository assetRepositoryMock() {return mock(AssetRepository.class);}
	@Bean @Primary
	public FileStorageManager fileStorageManagerMock() {return mock(FileStorageManager.class);}
	
}
