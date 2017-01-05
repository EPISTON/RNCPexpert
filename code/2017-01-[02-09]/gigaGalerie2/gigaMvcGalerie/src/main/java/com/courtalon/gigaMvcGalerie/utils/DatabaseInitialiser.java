package com.courtalon.gigaMvcGalerie.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.courtalon.gigaMvcGalerie.metier.AssetSource;
import com.courtalon.gigaMvcGalerie.metier.LicenseType;
import com.courtalon.gigaMvcGalerie.metier.Tag;
import com.courtalon.gigaMvcGalerie.repositories.AssetSourceRepository;
import com.courtalon.gigaMvcGalerie.repositories.ImageRepository;
import com.courtalon.gigaMvcGalerie.repositories.LicenseTypeRepository;
import com.courtalon.gigaMvcGalerie.repositories.TagRepository;

// cette classe sera appelée au démarrage du contexte spring
// doit etre soit annoté, soit déclarée dans le fichier de configuration de spring
public class DatabaseInitialiser implements ApplicationListener<ContextRefreshedEvent>
{

	private static final Logger log = LogManager.getLogger(DatabaseInitialiser.class); 
	

	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private LicenseTypeRepository licenseTypeRepository;
	
	@Autowired
	private AssetSourceRepository assetSourceRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private FileStorageManager fileStorageManager;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		if (licenseTypeRepository.count() == 0) {
			log.info("base seems empty. initializing data");
			initTags();
			initAssets();
		}
		else
			log.info("licenses found");
	}

	
	private void initAssets() {
		log.info("inserting basic license types....");
		licenseTypeRepository.save(new LicenseType(0, "NONE", "aucune license"));
		licenseTypeRepository.save(new LicenseType(0, "CC-BY 3.0", "Share, Adapt, even commercially, can not restrict, attribute"));
		licenseTypeRepository.save(new LicenseType(0, "GPL 3.0", "license GPL v3"));
		licenseTypeRepository.save(new LicenseType(0, "GPL 2.0", "license GPL v2"));
		licenseTypeRepository.save(new LicenseType(0, "CC0 1.0", "public domain"));
		licenseTypeRepository.save(new LicenseType(0, "LGPL 2.1", "license LGPL v2.1"));
		licenseTypeRepository.save(new LicenseType(0, "LGPL 3.0", "license LGPL v3"));
		
		log.info("inserting basic AssetSources....");
		assetSourceRepository.save(new AssetSource(0, "inconnue", "http://localhost", "source inconnue"));
		assetSourceRepository.save(new AssetSource(0, "google image", "http://www.google.com/", "from google image service"));
	}

	
	
	private void initTags() {
		log.info("inserting basic tags....");
		tagRepository.save(new Tag(0, TagRepository.UPLOADED, "used for tagging content newly uploaded", true));
		tagRepository.save(new Tag(0, "texture", "anything that can be used as a texture", false));
		tagRepository.save(new Tag(0, "nature", "nature related pictures", false));
	}
	
	

}
