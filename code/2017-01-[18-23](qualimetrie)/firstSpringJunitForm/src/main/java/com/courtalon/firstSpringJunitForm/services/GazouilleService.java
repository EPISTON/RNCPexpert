package com.courtalon.firstSpringJunitForm.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Pattern p = Pattern.compile(CENSORED, Pattern.CASE_INSENSITIVE);
		m.setTitre(p.matcher(m.getTitre()).replaceAll("gazouille"));
		m.setCorps(p.matcher(m.getCorps()).replaceAll("gazouille"));
		getMessageDao().save(m);
		return m;
	}
	
	public Message readGazouille(int id) {
		Message m = getMessageDao().findById(id);
		if (m == null)
			throw new GazouilleNotFound();
		return m;
	}
	
	public List<Message> readAllGazouilles() {
		return getMessageDao().findAll();
	}
	
	

}
