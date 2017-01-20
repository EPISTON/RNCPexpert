package com.courtalon.firstSpringJunitForm.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.courtalon.firstSpringJunitForm.beans.Message;
import com.courtalon.firstSpringJunitForm.services.GazouilleNotFound;
import com.courtalon.firstSpringJunitForm.services.GazouilleService;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

// cette annotation indique a junit d'utiliser un autre gestionnaire de test
// ici, le gestionnaire de Spring
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:testContext.xml") //charge ce context spring
public class MessageTest {

	@Autowired
	private GazouilleService gazouilleService;
	
	/*
	@Autowired
	@Qualifier("msg1")
	private Message message;

	@Test
	public void testmessage() {
		String expected = "hello!";
		String actual = message.getTitre();
		assertEquals("le titre ne correspond pas", expected, actual);
	}
	*/
	
	@Test
	public void testPublishNormal() {
		Message m = gazouilleService.publish(new Message(0, "vive la pizza", "avec mozarella"));
		assertEquals("le titre ne correspond pas", "vive la pizza", m.getTitre());
		assertEquals("le corps ne correspond pas", "avec mozarella", m.getCorps());
	}

	@Test
	public void testPublishToCensor() {
		Message m = gazouilleService.publish(new Message(0, "vive twitter", "avec twitter"));
		assertEquals("le titre n'est pas censuré", "vive gazouille", m.getTitre());
		assertEquals("le corps n'est pas censuré", "avec gazouille", m.getCorps());
	}

	@Test
	public void testPublishToCensorMajuscule() {
		Message m = gazouilleService.publish(new Message(0, "Vive TwitteR", "Avec TWitter"));
		assertEquals("le titre n'est pas censuré", "Vive gazouille", m.getTitre());
		assertEquals("le corps n'est pas censuré", "Avec gazouille", m.getCorps());
	}
	
	@Test
	public void testFindGazouilleOk() {
		Message m = gazouilleService.readGazouille(1);
		assertNotNull("la gazouille n'est pas trouvée", m);
		assertEquals("le titre de la cazouille n'est pas le bon", "gazouille test", m.getTitre());
	}

	@Test(expected=GazouilleNotFound.class)
	public void testFindGazouilleNOK() {
		Message m = gazouilleService.readGazouille(2);
	}

	@Test
	public void testFindAllGazouilleOK() {
		List<Message> gs = gazouilleService.readAllGazouilles();
		assertEquals("pas le bon nombre de gazouille", 3, gs.size());
	}

	
}
