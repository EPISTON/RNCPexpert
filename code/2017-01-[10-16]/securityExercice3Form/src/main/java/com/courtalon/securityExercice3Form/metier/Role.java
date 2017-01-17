package com.courtalon.securityExercice3Form.metier;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {
	private int id;
	private String roleName;
	private Set<Auteur> auteurs;
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@ManyToMany(mappedBy="roles")
	public Set<Auteur> getAuteurs() {
		if (auteurs == null)
			auteurs = new HashSet<>();
		return auteurs;
	}
	public void setAuteurs(Set<Auteur> auteurs) {
		this.auteurs = auteurs;
	}
	
	public Role() { this(0, ""); }
	public Role(int id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	
	
	
	

}
