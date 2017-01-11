package com.courtalon.secondSecurityForm.web;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.courtalon.secondSecurityForm.dao.MessageDAO;
import com.courtalon.secondSecurityForm.metier.Message;
import com.courtalon.secondSecurityForm.repositories.MessageRepository;
import com.courtalon.secondSecurityForm.utils.HtmlSanitizer;



@Controller
@RequestMapping(value="/")
public class IndexController {

	private static Logger log = LogManager.getLogger(IndexController.class); 
	
	@Autowired
	private MessageDAO messageDAO;
	public MessageDAO getMessageDAO() {return messageDAO;}
	public void setMessageDAO(MessageDAO messageDAO) {this.messageDAO = messageDAO;}

	@Autowired
	private MessageRepository messageRepository;
	public MessageRepository getMessageRepository() {return messageRepository;}
	public void setMessageRepository(MessageRepository messageRepository) {this.messageRepository = messageRepository;}
	
	@RequestMapping(value = "/search",method=RequestMethod.GET)
	public String recherche() {
		return "recherche";
	}

	@RequestMapping(value = "/search",method=RequestMethod.POST)
	// Model nous servira a passer des parametre a la vue jsp
	// searchTerm contiendra le champs en provenance du formulaire
	public String listeRecherche(Model model, @RequestParam(name="searchTerm") String searchTerm) {
		log.info("recherche avec le terme:" + searchTerm);
		
		//model.addAttribute("messages", getMessageDAO().findByTitre(searchTerm));
		model.addAttribute("messages", getMessageRepository().findsecureByTitre(searchTerm));
		return "listeRecherche";
	}

	@RequestMapping(value="/edit/{id:[0-9]+}", method=RequestMethod.GET)
	public String editMessage(Model model, @PathVariable(value="id") int id) {
		Message msg = getMessageDAO().findByID(id);
		if (!msg.isPublished()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "message non trouvé");
		}
		model.addAttribute("message", msg);
		return "edit";
	}
	
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@ModelAttribute("message") Message message) {
		Message m = getMessageDAO().findByID(message.getId());
		if (m == null || !m.isPublished()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "message non trouvé");
		}
		
		/*
		message.setTitre(message.getTitre().replaceAll("<", "&lt;")
			 .replaceAll(">", "&gt;")
			 .replaceAll("\"", "&quot;")
			 .replaceAll("'", "&apos;"));
		message.setCorps(message.getCorps().replaceAll("<", "&lt;")
				 .replaceAll(">", "&gt;")
				 .replaceAll("\"", "&quot;")
				 .replaceAll("'", "&apos;"));
		*/
		message.setTitre(HtmlSanitizer.sanitize(message.getTitre()));
		message.setCorps(HtmlSanitizer.sanitize(message.getCorps()));
		getMessageDAO().saveMessage(message);
		return "redirect:/search";
	}
	
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToIndex() {
		return "redirect:/Index";
	}

	
	@RequestMapping(value = "/Index", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "bonjour depuis spring 3 mvc");
		return "bonjour";

	}

	@RequestMapping(value = "/bonjour/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("bonjour");
		model.addObject("message", "bonjour " + name);

		return model;

	}

}