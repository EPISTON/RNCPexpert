package com.courtalon.securityExercice3Form.utils;

import java.util.regex.Pattern;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;

@Component
public class NettoyeurHtml {
	
	private PolicyFactory filtreCorps;
	private PolicyFactory filtreTitre;
	
	public NettoyeurHtml() {
		filtreCorps = new HtmlPolicyBuilder()
				.allowElements("b", "i", "ol", "li", "img")
				.allowUrlProtocols("http")
				.allowAttributes("src").matching(Pattern.compile("http://i[.]imgur[.]com/.*"))
									   .onElements("img")
				.toFactory();
		filtreTitre = new HtmlPolicyBuilder()
				.toFactory();
	}
	
	public String sanitizeTitre(String unsafeHtml) {
		return filtreTitre.sanitize(unsafeHtml);
	}

	public String sanitizeCorps(String unsafeHtml) {
		return filtreCorps.sanitize(unsafeHtml);
	}

}
