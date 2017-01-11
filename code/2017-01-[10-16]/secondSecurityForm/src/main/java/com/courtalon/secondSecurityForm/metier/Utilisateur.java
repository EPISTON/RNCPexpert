package com.courtalon.secondSecurityForm.metier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="utilisateurs")
public class Utilisateur {
	private int id;
	private String login;
	private String password;
	
	@Id @GeneratedValue
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	@Column(length= 100)
	public String getLogin() {return login;}
	public void setLogin(String login) {this.login = login;}

	@Column(length= 100)
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	

}
