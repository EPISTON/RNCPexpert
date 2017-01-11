package com.courtalon.firstSecurityForm.repositories;

import java.util.List;

import com.courtalon.firstSecurityForm.metier.Message;

public interface MessageRepositoryCustom {
	List<Message> findUnsecureByTitre(String titre);
	List<Message> findsecureByTitre(String titre);
}
