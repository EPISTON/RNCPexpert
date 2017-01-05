package com.courtalon.gigaMvcGalerie.metier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

@Entity
public class Gallery extends Asset {
	
	private List<Image> images;

	@ManyToMany
	@OrderColumn(name="position")
	public List<Image> getImages() {
		if (images == null)
			images = new ArrayList<>();
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	public Gallery() {
		this(0, "", "", null);
	}
	public Gallery(int id, String name, String description, Date dateAdded) {
		super(id, name, description, dateAdded);
	}
	
	
}
