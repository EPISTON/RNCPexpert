package com.courtalon.ExpeditionPereNoel.webservices;

import javax.jws.WebService;

import com.courtalon.lightspeedtransport.ExpeditionService;

@WebService(endpointInterface="com.courtalon.lightspeedtransport.ExpeditionService")
public class ExpeditionPereNoelService implements ExpeditionService
{

	
	@Override
	public double tarifExpedition(String ville, double poids) {
		switch(ville.toLowerCase()) {
			case "paris": return poids * 15.5;
			case "londres": return poids * 10.5;
			case "new york": return poids * 12.5;
			case "stockholm": return poids * 4.5;
			case "berlin" : return poids * 14.5;
		}
		return poids  * 40.0;
	}

}
