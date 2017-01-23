package com.courtalon.junitAndMockitoForm.services;

import com.courtalon.junitAndMockitoForm.beans.Message;
import com.courtalon.junitAndMockitoForm.beans.Template;

public interface TemplateService {

	Message format(Template t, Message m);

}