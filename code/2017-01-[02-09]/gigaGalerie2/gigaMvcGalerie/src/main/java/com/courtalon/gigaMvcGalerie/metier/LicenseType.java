package com.courtalon.gigaMvcGalerie.metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.courtalon.gigaMvcGalerie.metier.Asset.AssetOnly;
import com.courtalon.gigaMvcGalerie.utils.JsonPageable;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class LicenseType {
	public static final int NO_LICENSE_ID = 1;
	
	public static class LicenseOnly extends JsonPageable.PaginatedResult {}
	
	@JsonView({LicenseOnly.class, AssetOnly.class})
	private int id;
	@JsonView({LicenseOnly.class, AssetOnly.class})
	private String name;
	@JsonView({LicenseOnly.class, AssetOnly.class})
	private String description;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {return id;}
	public void setId(int id) {	this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public LicenseType() { this(0, "", "");}
	public LicenseType(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

}
