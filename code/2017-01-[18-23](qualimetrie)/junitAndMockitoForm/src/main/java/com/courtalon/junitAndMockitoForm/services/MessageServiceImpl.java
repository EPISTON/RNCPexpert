package com.courtalon.junitAndMockitoForm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courtalon.junitAndMockitoForm.beans.Message;
import com.courtalon.junitAndMockitoForm.beans.Template;

@Service
public class MessageServiceImpl {
	
	
	private TemplateService templateService;
	public TemplateService getTemplateService() {return templateService;}
	public void setTemplateService(TemplateService templateService) {this.templateService = templateService;}
	
	private MailService mailService;
	public MailService getMailService() {return mailService;}
	public void setMailService(MailService mailService) {this.mailService = mailService;}
	
	
	
	public boolean sendMessage(Template t, Message m, List<String> emails) {
		Message m2 = templateService.format(t, m);
		return mailService.mailMessage(m2, emails);
		//return true; 
	}
	
}
