package com.courtalon.jpaSecurityForm.metier;

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
	private Set<Utilisateur> utilisateurs;
	
	@Id @GeneratedValue
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public String getRoleName() {return roleName;}
	public void setRoleName(String roleName) {this.roleName = roleName;}
	
	@ManyToMany(mappedBy="roles")
	public Set<Utilisateur> getUtilisateurs() {
		if (utilisateurs == null)
			utilisateurs = new HashSet<>();
		return utilisateurs;
	}
	public void setUtilisateurs(Set<Utilisateur> utilisateurs) {this.utilisateurs = utilisateurs;}
	
	public Role() {this(0, ""); }
	public Role(int id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}

	
	
}
