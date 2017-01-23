package com.courtalon.junitAndMockitoForm.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.courtalon.junitAndMockitoForm.beans.Message;
import com.courtalon.junitAndMockitoForm.beans.Template;
import com.courtalon.junitAndMockitoForm.services.MailService;
import com.courtalon.junitAndMockitoForm.services.MessageServiceImpl;
import com.courtalon.junitAndMockitoForm.services.TemplateService;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:testContext.xml")
public class MessageServiceTest {

	private MailService mailService;
	private TemplateService templateService;
	
	@Autowired
	private MessageServiceImpl messageService;
	
	@Before
	public void setupMock() {
		// la librairie Mockito va générer un objet stub/fake
		// qui répondera comme un objet MailService
		mailService = mock(MailService.class);
		// idem pour TemplateService
		templateService = mock(TemplateService.class);
	}
	
	@Test
	public void testTemplate() {
		Template t = new Template("bonjour", "bonsoir");
		Message m = new Message("bonjour monde", "et bienvenue");
		/*
		 * when est une fonction de mockito qui permet d'indiquer
		 * ce que doit faire l'objet Mock quand on appel une de ses methodes
		 * avec certains parametres
		 * ici
		 * si on appelle le Mock de templateService avec t et m en parametre
		 * renvoie un nouveau message ("test", "testtest")
		 */
		when(templateService.format(t, m))
							.thenReturn(new Message("bonsoir Monde", "et bienvenue"));
		
		/*
		 * ici, c'est en fait le mock qui est appelé
		 * comme les parametres correspondent au when indiqué avant
		 * cette "fausse" mathode renverra ce qui était indiqué dans le thenReturn
		 */
		Message m2 = templateService.format(t, m);
		assertEquals("titre devrait etre 'bonsoir Monde'", "bonsoir Monde", m2.getTitre());

		// verify permet de verifier si certaines methodes on bien été
		// appelée sur l'objet mock
		//
		// ici, je verifie que format a été au moins appelé une fois avec les
		// parametres indiqués
		verify(templateService,atLeastOnce()).format(t, m);
	}
	
	@Test
	public void testMessageService() {
		// j'inject les stub/mock des services mail et template
		// dans les service message a tester
		messageService.setTemplateService(templateService);
		messageService.setMailService(mailService);
		
		/*
		 * preparation des mock pour le test du messageService
		 * 
		 */
		Message m = new Message("bonjour Monde", "et bienvenue");
		Message m2 = new Message("bonsoir Monde", "et bienvenue");
		List<String> emails = new ArrayList<>();
		emails.add("bob@joe.com");
		emails.add("patrick@bikinibottom.com");
		Template t = new Template("bonjour", "bonsoir");
		
		when(templateService.format(t, m))
			.thenReturn(m2);
		when(mailService.mailMessage(m2, emails))
			.thenReturn(true);
		
		// messageService appelera nos Mocks pour les services template et mail
		// qui lui retournerons les valeurs attendu
		boolean result = messageService.sendMessage(t, m, emails);
		
		
		// verifications
		assertTrue("messageService aurait du retourner true", result);
		
		// verification que les methodes sont bien appelées par le service
		// avec les bons arguments
		verify(templateService, times(1)).format(t, m);
		verify(mailService, times(1)).mailMessage(m2, emails);
		
	}
	@Test
	public void testMessageService2() {
		// j'inject les stub/mock des services mail et template
		// dans les service message a tester
		messageService.setTemplateService(templateService);
		messageService.setMailService(mailService);
		
		/*
		 * preparation des mock pour le test du messageService
		 * 
		 */
		Message m = new Message("bonjour Monde", "et bienvenue");
		List<String> emails = new ArrayList<>();
		emails.add("bob@joe.com");
		emails.add("patrick@bikinibottom.com");
		Template t = new Template("bonjour", "bonsoir");
		
		when(templateService.format(any(Template.class), any(Message.class)))
							.thenReturn(m);
		when(mailService.mailMessage(any(Message.class), anyListOf(String.class)))
							.thenReturn(true);
		
		// messageService appelera nos Mocks pour les services template et mail
		// qui lui retournerons les valeurs attendu
		boolean result = messageService.sendMessage(t, m, emails);
		
		
		// verifications
		assertTrue("messageService aurait du retourner true", result);
		
		// verification que les methodes sont bien appelées par le service
		// sans verifier la valeur des argument
		// mais en vérifiant l'ordre
		verify(templateService, times(1)).format(any(Template.class), any(Message.class));
		verify(mailService, times(1)).mailMessage(any(Message.class), anyListOf(String.class));
	}
	
}
