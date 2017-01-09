package com.courtalon.BoutiqueServiceForm.webservices;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.courtalon.BoutiqueServiceForm.metier.Produit;

@WebService
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL)
public interface ProduitService {

	@WebMethod
	List<Produit> listeAllProduit();

	@WebMethod
	Produit produitDetails(@WebParam(name="id") int id);

	@WebMethod
	boolean effaceProduit(@WebParam(name="id") int id);

	@WebMethod
	Produit sauverProduit(@WebParam(name="produit") Produit produit);

}