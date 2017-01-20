package com.courtalon.firstSpringJunitForm.repositories;

import java.util.List;

import com.courtalon.firstSpringJunitForm.beans.Message;

public interface MessageDao {

	List<Message> findAll();

	Message findById(int id);

	int save(Message m);

}