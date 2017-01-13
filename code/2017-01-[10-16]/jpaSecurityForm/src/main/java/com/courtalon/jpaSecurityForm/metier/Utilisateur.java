package com.courtalon.jpaSecurityForm.metier;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Utilisateur {
	private int id;
	private String username;
	private String email;
	private String password;
	private boolean enabled;
	
	private Set<Role> roles;

	@Id @GeneratedValue
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	@Column(unique=true)
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	@Column(length=100)
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}

	public boolean isEnabled() {return enabled;}
	public void setEnabled(boolean enabled) {this.enabled = enabled;}

	@ManyToMany(fetch=FetchType.EAGER)
	public Set<Role> getRoles() {
		if (roles == null)
			roles = new HashSet<>();
		return roles;
	}
	public void setRoles(Set<Role> roles) {this.roles = roles;}
	
	public Utilisateur() {this(0, "", "", "", false); }
	public Utilisateur(int id, String username, String email, String password, boolean enabled) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
	}
	
	
	

}
