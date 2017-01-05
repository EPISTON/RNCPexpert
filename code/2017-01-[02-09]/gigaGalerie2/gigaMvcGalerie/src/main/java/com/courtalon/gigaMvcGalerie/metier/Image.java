package com.courtalon.gigaMvcGalerie.metier;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;


import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Image extends Asset {
	@JsonView(AssetOnly.class)
	private String fileName;
	@JsonView(AssetOnly.class)
	private String contentType;
	@JsonView(AssetOnly.class)
	private long fileSize;
	private String fileHash;
	
	
	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	
	@Column(length=100)
	public String getContentType() {return contentType;}
	public void setContentType(String contentType) {this.contentType = contentType;}
	
	public long getFileSize() {return fileSize;}
	public void setFileSize(long fileSize) {this.fileSize = fileSize;}

	@Column(length=100)
	public String getFileHash() {return fileHash;}
	public void setFileHash(String fileHash) {this.fileHash = fileHash;}
	
	public Image() { this(0, "", "", null, "", "", 0, "");}
	public Image(int id, String name, String description, Date dateAdded, String fileName, String contentType, long fileSize, String fileHash) {
		super(id, name, description, dateAdded);
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.fileHash = fileHash;
	}

	
}
