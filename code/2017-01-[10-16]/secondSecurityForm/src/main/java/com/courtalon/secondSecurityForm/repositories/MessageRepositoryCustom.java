package com.courtalon.secondSecurityForm.repositories;

import java.util.List;

import com.courtalon.secondSecurityForm.metier.Message;



public interface MessageRepositoryCustom {
	List<Message> findUnsecureByTitre(String titre);
	List<Message> findsecureByTitre(String titre);
}
