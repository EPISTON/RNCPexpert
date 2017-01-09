package com.courtalon.firstWebServiceForm.webservices;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.courtalon.BoutiqueServiceForm.webservices.ProduitService;
import com.courtalon.firstWebServiceForm.metier.Message;
import com.courtalon.firstWebServiceForm.metier.Produit;
import com.courtalon.firstWebServiceForm.repositories.MessageRepository;

@WebService(endpointInterface="com.courtalon.firstWebServiceForm.webservices.SalutationService")
public class SalutationServiceImpl implements SalutationService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ProduitService produitService;
	
	
	@Override
	public String direBonjour(String nom) {
		List<Produit> produits = produitService.listeAllProduit();
		Random rd = new Random();
		Produit p = produits.get(rd.nextInt(produits.size()));
		return "bonjour, " + nom + " , je vous propose en promotion : " + p.getNom();
	}

	
	@Override
	public Message findMessageByID(int id) {
		return messageRepository.findOne(id);
	}

}
