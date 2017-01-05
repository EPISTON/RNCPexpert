package com.courtalon.gigaMvcGalerie.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class FileStorageManager {
	
	private final static Logger log = LogManager.getLogger(FileStorageManager.class);
	
	private File storageRoot;
	public File getStorageRoot() {return storageRoot;}

	public void setStorageRoot(File storageRoot) {this.storageRoot = storageRoot;}
	
	public FileStorageManager(){
	}

	public boolean saveFile(String entityName, int id, File f) {
		try {
			return saveFile(entityName, id, new FileInputStream(f));
		} catch (FileNotFoundException e) {
			log.error(e);
		}
		return false;
	}
	
	public boolean saveFile(String entityName, int id, InputStream data) {
		if (storageRoot == null 
			|| !storageRoot.exists()
			|| !storageRoot.isDirectory()) {
			log.error("root directory for file storage unavalaible: " + storageRoot.getAbsolutePath());
			return false;
		}
		// je génére le hash du depot
		String md5name = DigestUtils.md5Hex(entityName);
		// si le rep n'existe pas, je le creer
		File entityRep = new File(storageRoot.getAbsolutePath() + File.separatorChar + md5name); 
		if (!entityRep.exists()){
			entityRep.mkdirs();
		}
		if (!entityRep.isDirectory()) {
			log.error("could not create or access directory" + md5name +" for entity " + entityName + "for file storage");
			return false;
		}
		// je le génére le hash spécifique pour notre image (a partir de son id)
		String hashedFileName = DigestUtils.md5Hex(entityName + "_" + id);
		// on sous-decoupe en 256 sous-rep potentiels pour eviter un répertoire avec une enorme liste de fichier dedans
		File entitySubDir = new File(entityRep, hashedFileName.substring(0, 2));
		if (!entitySubDir.exists()){
			entitySubDir.mkdirs();
		}
		if (!entitySubDir.isDirectory()) {
			log.error("could not create or access sub directory for entity " + entityName + " with id " + id  +"for file storage");
			return false;
		}
		
		try {
			Files.copy(data, Paths.get(entitySubDir.getAbsolutePath(), hashedFileName.substring(2)), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			log.error(e);
		}
		return false;
	}
	public Optional<File> getFile(String entityName, int id) {
		if (storageRoot == null 
				|| !storageRoot.exists()
				|| !storageRoot.isDirectory()) {
				log.error("root directory for file storage unavalaible");
				return Optional.empty();
		}
		String md5name = DigestUtils.md5Hex(entityName);
		File entityRep = new File(storageRoot.getAbsolutePath() + File.separatorChar + md5name); 
		if (!entityRep.exists() || !entityRep.isDirectory()){
			return Optional.empty();
		}
		String hashedFileName = DigestUtils.md5Hex(entityName + "_" + id);
		File entitySubDir = new File(entityRep, hashedFileName.substring(0, 2));
		if (!entitySubDir.exists() || !entitySubDir.isDirectory()){
			return Optional.empty();
		}
		File f = new File(entitySubDir.getAbsolutePath() +  File.separatorChar +  hashedFileName.substring(2));
		if (f.exists() && f.isFile())
			return Optional.of(f);
		else
			return Optional.empty();
	}
	
	public boolean removeFile(String entityName, int id) {
		if (storageRoot == null 
				|| !storageRoot.exists()
				|| !storageRoot.isDirectory()) {
				log.error("root directory for file storage unavalaible");
				return false;
		}
		String md5name = DigestUtils.md5Hex(entityName);
		File entityRep = new File(storageRoot.getAbsolutePath() + File.separatorChar + md5name); 
		if (!entityRep.exists() || !entityRep.isDirectory()){
			return false;
		}
		String hashedFileName = DigestUtils.md5Hex(entityName + "_" + id);
		File entitySubDir = new File(entityRep, hashedFileName.substring(0, 2));
		if (!entitySubDir.exists() || !entitySubDir.isDirectory()){
			return false;
		}
		File f = new File(entitySubDir.getAbsolutePath() +  File.separatorChar +  hashedFileName.substring(2));
		if (f.exists() && f.isFile())
			return f.delete();
		else
			return false;
	}
	

}
