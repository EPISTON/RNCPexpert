package com.courtalon.firstSpringJunitForm.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.courtalon.firstSpringJunitForm.beans.Message;

import static org.junit.Assert.*;

import org.junit.Test;

// cette annotation indique a junit d'utiliser un autre gestionnaire de test
// ici, le gestionnaire de Spring
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:testContext.xml") //charge ce context spring
public class MessageTest {
	
	@Autowired
	@Qualifier("msg1")
	private Message message;

	@Test
	public void testmessage() {
		String expected = "hello!";
		String actual = message.getTitre();
		assertEquals("le titre ne correspond pas", expected, actual);
	}
	
}
