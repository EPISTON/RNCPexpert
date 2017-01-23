package com.courtalon.junitAndMockitoForm.beans;


public class Message {
	private String titre;
	private String corps;
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getCorps() {
		return corps;
	}
	public void setCorps(String corps) {
		this.corps = corps;
	}
	
	@Override
	public String toString() {
		return "Message [titre=" + titre + ", corps=" + corps + "]";
	}
	
	public Message() { this("", ""); }
	public Message(String titre, String corps) {
		super();
		this.titre = titre;
		this.corps = corps;
	}
	
	
}
