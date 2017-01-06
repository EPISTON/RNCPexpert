package com.courtalon.firstWebServiceForm.webservices;

import javax.jws.WebService;

@WebService(endpointInterface="com.courtalon.firstWebServiceForm.webservices.SalutationService")
public class SalutationServiceImpl implements SalutationService {

	@Override
	public String direBonjour(String nom) {
		return "bonjour, " + nom;
	}

}
