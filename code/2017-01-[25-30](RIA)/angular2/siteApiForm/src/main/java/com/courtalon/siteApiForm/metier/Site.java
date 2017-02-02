package com.courtalon.siteApiForm.metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Site {
	private int id;
	private String nom;
	private String rue;
	private String ville;
	private String pays;
	
	@Id @GeneratedValue
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}
	
	public String getRue() {return rue;}
	public void setRue(String rue) {this.rue = rue;}
	
	public String getVille() {return ville;}
	public void setVille(String ville) {this.ville = ville;}
	
	public String getPays() {return pays;}
	public void setPays(String pays) {this.pays = pays;}
	
	public Site() {this(0, "", "", "", ""); }
	public Site(int id, String nom, String rue, String ville, String pays) {
		super();
		this.id = id;
		this.nom = nom;
		this.rue = rue;
		this.ville = ville;
		this.pays = pays;
	}
	
	
	

}
