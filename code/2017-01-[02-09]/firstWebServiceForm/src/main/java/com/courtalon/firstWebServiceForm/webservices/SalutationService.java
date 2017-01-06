package com.courtalon.firstWebServiceForm.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style=Style.DOCUMENT,use=Use.LITERAL)
public interface SalutationService {
	
	@WebMethod
	String direBonjour(@WebParam(name="nom") String nom);

}
