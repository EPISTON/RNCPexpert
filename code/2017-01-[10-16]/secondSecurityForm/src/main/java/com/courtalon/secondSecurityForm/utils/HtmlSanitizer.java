package com.courtalon.secondSecurityForm.utils;

public class HtmlSanitizer {

	public static String sanitize(String unsafeHtml) {
		 return  unsafeHtml.replaceAll("<", "&lt;")
				 .replaceAll(">", "&gt;")
				 .replaceAll("\"", "&quot;")
				 .replaceAll("'", "&apos;");
	}
	
}
