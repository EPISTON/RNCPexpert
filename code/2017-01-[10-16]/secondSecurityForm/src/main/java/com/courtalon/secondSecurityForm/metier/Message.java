package com.courtalon.secondSecurityForm.metier;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {
	private int id;
	private String titre;
	private String corps;
	private boolean published;
	
	
	@Id @GeneratedValue
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getTitre() {return titre;}
	public void setTitre(String titre) {this.titre = titre;}
	public String getCorps() {return corps;}
	public void setCorps(String corps) {this.corps = corps;}
	public boolean isPublished() {return published;}
	public void setPublished(boolean published) {this.published = published;}
	
	public Message() {this(0, "", "", false); }
	public Message(int id, String titre, String corps, boolean published) {
		super();
		this.id = id;
		this.titre = titre;
		this.corps = corps;
		this.published = published;
	}

}
