package com.courtalon.securityExercice3Form.metier;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Auteur {
	private int id;
	private String nom;
	private String email;
	private String password;
	private boolean enabled;
	private Set<Post> posts;
	private Set<Role> roles;
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(unique=true)
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
	@Column(length=100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	
	@ManyToMany(fetch=FetchType.EAGER)
	public Set<Role> getRoles() {
		if (roles == null)
			roles = new HashSet<>();
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Auteur() { this(0, "", "", "", true);}
	public Auteur(int id, String nom, String email, String password, boolean enabled) {
		super();
		this.id = id;
		this.nom = nom;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}
	
	
	

}
