package com.courtalon.gigaMvcGalerie.metier;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.courtalon.gigaMvcGalerie.metier.Asset.AssetAndTags;
import com.courtalon.gigaMvcGalerie.utils.JsonPageable;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Tag {

	public static class TagOnly extends JsonPageable.PaginatedResult {}
	
	@JsonView({AssetAndTags.class, TagOnly.class})
	private int id;
	@JsonView({AssetAndTags.class, TagOnly.class})
	private boolean systemTag;
	@JsonView({AssetAndTags.class, TagOnly.class})
	private String libelle;
	@JsonView({AssetAndTags.class, TagOnly.class})
	private String description;
	private Set<Asset> assets;
	
	@ManyToMany(mappedBy="tags")
	public Set<Asset> getAssets() {
		if (assets == null)
			assets = new HashSet<>();
		return assets;
	}
	public void setAssets(Set<Asset> assets) {
		this.assets = assets;
	}
	
	@Id @GeneratedValue
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	@Column(unique=true, length=100)
	public String getLibelle() {return libelle;}
	public void setLibelle(String libelle) {this.libelle = libelle;}
	public boolean isSystemTag() {return systemTag;	}
	public void setSystemTag(boolean systemTag) {this.systemTag = systemTag;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public Tag() {this(0, "", "", false); }
	public Tag(int id, String libelle, String description, boolean systemTag) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.systemTag = systemTag;
		this.description = description;
	}

	
	
}
