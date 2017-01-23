package com.courtalon.junitAndMockitoForm.services;

import com.courtalon.junitAndMockitoForm.beans.Message;
import com.courtalon.junitAndMockitoForm.beans.Template;

public class TemplateServiceImpl implements TemplateService {

	
	
	/* (non-Javadoc)
	 * @see com.courtalon.junitAndMockitoForm.services.TemplateService#format(com.courtalon.junitAndMockitoForm.beans.Template, com.courtalon.junitAndMockitoForm.beans.Message)
	 */
	@Override
	public Message format(Template t, Message m) {
		return new Message(
				m.getTitre().replaceAll(t.getToReplace(), t.getReplacement()),
				m.getCorps().replaceAll(t.getToReplace(), t.getReplacement())
				);
	}
	
}
