package com.courtalon.junitAndMockitoForm.services;

import java.util.List;

import com.courtalon.junitAndMockitoForm.beans.Message;

public class MailServiceImpl implements MailService {

	/* (non-Javadoc)
	 * @see com.courtalon.junitAndMockitoForm.services.MailService#mailMessage(com.courtalon.junitAndMockitoForm.beans.Message, java.util.List)
	 */
	@Override
	public boolean mailMessage(Message m, List<String> emails) {
		for (String email : emails) {
			System.out.println("envoie a " + email + " de " + m);
		}
		return true;
	}
	
}
