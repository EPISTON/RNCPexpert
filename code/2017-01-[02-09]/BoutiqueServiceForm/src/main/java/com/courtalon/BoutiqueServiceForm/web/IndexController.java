package com.courtalon.BoutiqueServiceForm.web;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.courtalon.BoutiqueServiceForm.repositories.ProduitRepository;

@Controller
@RequestMapping(value="/")
public class IndexController {

	
	
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
	//---------------------------------------------
	@Autowired
	private ProduitRepository produitRepository;

	@RequestMapping(value = "/produit/liste", method = RequestMethod.GET)
	public ModelAndView listeProduit() {

		ModelAndView model = new ModelAndView();
		model.setViewName("produitListe");
		model.addObject("produits", produitRepository.findAll(new PageRequest(1, 5)));

		return model;
	}

}