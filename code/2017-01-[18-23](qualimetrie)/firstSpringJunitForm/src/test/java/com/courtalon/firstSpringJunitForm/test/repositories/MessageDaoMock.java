package com.courtalon.firstSpringJunitForm.test.repositories;

import java.util.ArrayList;
import java.util.List;

import com.courtalon.firstSpringJunitForm.beans.Message;
import com.courtalon.firstSpringJunitForm.repositories.MessageDao;

public class MessageDaoMock implements MessageDao {

	
	private List<Message> sampleMessages;
	public List<Message> getSampleMessages() {return sampleMessages;}
	public void setSampleMessages(List<Message> sampleMessages) {this.sampleMessages = sampleMessages;}

	@Override
	public List<Message> findAll() {
		return new ArrayList<>(getSampleMessages());
	}

	@Override
	public Message findById(int id) {
		// mon "bouchon/mock/stub" simule un acces a la base
		// comme si l abase n'avais qu'un message d'id 1
		// pour findById
		if (id == 1) {
			return new Message(1,"gazouille test", "lorem ipsum");
		}
		else {
			return null;
		}
	}

	@Override
	public int save(Message m) {
		return 1;
	}

}
