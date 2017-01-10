package com.courtalon.firstSecurityForm.web;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

import com.courtalon.firstSecurityForm.dao.MessageDAO;
import com.courtalon.firstSecurityForm.metier.Message;

@Controller
@RequestMapping(value="/")
public class IndexController {

	private static Logger log = LogManager.getLogger(IndexController.class); 
	
	@Autowired
	private MessageDAO messageDAO;
	public MessageDAO getMessageDAO() {return messageDAO;}
	public void setMessageDAO(MessageDAO messageDAO) {this.messageDAO = messageDAO;}

	@RequestMapping(value = "/search",method=RequestMethod.GET)
	public String recherche() {
		return "recherche";
	}

	@RequestMapping(value = "/search",method=RequestMethod.POST)
	// Model nous servira a passer des parametre a la vue jsp
	// searchTerm contiendra le champs en provenance du formulaire
	public String listeRecherche(Model model, @RequestParam(name="searchTerm") String searchTerm) {
		log.info("recherche avec le terme:" + searchTerm);
		
		model.addAttribute("messages", getMessageDAO().findByTitre(searchTerm));
		
		return "listeRecherche";
	}

	@RequestMapping(value="/edit/{id:[0-9]+}", method=RequestMethod.GET)
	public String editMessage(Model model, @PathVariable(value="id") int id) {
		model.addAttribute("message", getMessageDAO().findByID(id));
		return "edit";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@ModelAttribute("message") Message message) {
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