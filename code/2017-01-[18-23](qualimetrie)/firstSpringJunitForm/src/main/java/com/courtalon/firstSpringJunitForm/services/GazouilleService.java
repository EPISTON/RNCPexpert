package com.courtalon.firstSpringJunitForm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courtalon.firstSpringJunitForm.beans.Message;
import com.courtalon.firstSpringJunitForm.repositories.MessageDao;

@Service
public class GazouilleService {
	public static final String CENSORED = "twitter";
	
	@Autowired
	private MessageDao messageDao;
	public MessageDao getMessageDao() {return messageDao;}
	public void setMessageDao(MessageDao messageDao) {this.messageDao = messageDao;}


	public Message publish(Message m) {
		m.setTitre(m.getTitre().replaceAll(CENSORED, "gazouille"));
		m.setCorps(m.getCorps().replaceAll(CENSORED, "gazouille"));
		getMessageDao().save(m);
		return m;
	}
	
	

}
