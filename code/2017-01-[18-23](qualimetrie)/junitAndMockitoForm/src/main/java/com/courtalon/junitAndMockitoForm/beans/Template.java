package com.courtalon.junitAndMockitoForm.beans;

public class Template {
	private String toReplace;
	private String replacement;
	
	public String getToReplace() {return toReplace;}
	public void setToReplace(String toReplace) {this.toReplace = toReplace;}
	public String getReplacement() {return replacement;}
	public void setReplacement(String replacement) {this.replacement = replacement;}
	
	public Template() { this("", ""); }
	public Template(String toReplace, String replacement) {
		this.toReplace = toReplace;
		this.replacement = replacement;
	}
	
	
}
