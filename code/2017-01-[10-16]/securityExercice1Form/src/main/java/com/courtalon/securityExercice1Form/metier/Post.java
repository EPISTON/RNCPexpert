package com.courtalon.securityExercice1Form.metier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
public class Post {
	private int id;
	private String titre;
	private String corps;
	private boolean published;
	private Auteur auteur;
	private int auteurId;
	
	@PostLoad
	public void apresChargement() {
		if (getAuteur() != null) {
			setAuteurId(getAuteur().getId());
		}
		else
			setAuteurId(0);
	}

	
	// attribut non géré par JPA
	@Transient
	public int getAuteurId() {return auteurId;}
	public void setAuteurId(int auteurId) {this.auteurId = auteurId;}
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(length=100)
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	@Column(length=1000)
	public String getCorps() {
		return corps;
	}
	public void setCorps(String corps) {
		this.corps = corps;
	}
	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	@ManyToOne
	public Auteur getAuteur() {
		return auteur;
	}
	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}
	
	public Post() { this(0, "", "", false); }
	
	public Post(int id, String titre, String corps, boolean published) {
		super();
		this.id = id;
		this.titre = titre;
		this.corps = corps;
		this.published = published;
	}
	
	
	

}
