package com.courtalon.firstJunit;

public class TextUtil {
	
	public String capitalise(String chaine) {
		if (chaine == null || chaine.length() == 0)
			return chaine;
		return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
	}

	/*public String capitalise(String chaine) {
		if (chaine == null || chaine.length() == 0)
			return chaine;
		char[] cars = chaine.toCharArray();
		if (cars[0] >= 'a' && cars[0] <= 'z') {
			cars[0] = (char)(cars[0] - ('a' - 'A'));
		}
		return new String(cars);
	}
	*/
	
	public String inverse(String chaine) {
		StringBuilder sb = new StringBuilder();
		for (int i = chaine.length() - 1; i >= 0; i--) {
			sb.append(chaine.charAt(i));
		}
		return sb.toString();
	}
	
}
