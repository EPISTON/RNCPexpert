package com.courtalon.firstWebServiceForm.webservices;

import java.util.stream.Collectors;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.courtalon.firstWebServiceForm.metier.Message;
import com.courtalon.firstWebServiceForm.repositories.MessageRepository;

@WebService(endpointInterface="com.courtalon.firstWebServiceForm.webservices.SalutationService")
public class SalutationServiceImpl implements SalutationService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public String direBonjour(String nom) {
		return "bonjour, " + nom;
	}

	
	@Override
	public Message findMessageByID(int id) {
		return messageRepository.findOne(id);
	}

}
