package com.courtalon.securityExercice1Form.metier;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Auteur {
	private int id;
	private String nom;
	private String email;
	private Set<Post> posts;
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToMany(mappedBy="auteur")
	public Set<Post> getPosts() {
		if (posts == null)
			posts = new HashSet<>();
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	public Auteur() { this(0, "", "");}
	public Auteur(int id, String nom, String email) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
	}
	
	
	

}
