package com.courtalon.firstSpringJunitForm.beans;


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
	
	
}
