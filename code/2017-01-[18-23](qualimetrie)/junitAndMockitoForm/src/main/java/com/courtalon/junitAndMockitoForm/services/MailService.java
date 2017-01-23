package com.courtalon.junitAndMockitoForm.services;

import java.util.List;

import com.courtalon.junitAndMockitoForm.beans.Message;

public interface MailService {

	boolean mailMessage(Message m, List<String> emails);

}