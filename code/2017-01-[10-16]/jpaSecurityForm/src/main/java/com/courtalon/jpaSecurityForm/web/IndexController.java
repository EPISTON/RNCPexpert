package com.courtalon.jpaSecurityForm.web;

import java.security.Principal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.courtalon.jpaSecurityForm.metier.Role;
import com.courtalon.jpaSecurityForm.metier.Utilisateur;
import com.courtalon.jpaSecurityForm.repositories.RoleRepository;
import com.courtalon.jpaSecurityForm.repositories.UtilisateurRepository;
import com.courtalon.jpaSecurityForm.utils.MyPasswordEncoder;

@Controller
@RequestMapping(value="/")
public class IndexController {
	
	Logger log = LogManager.getLogger(IndexController.class);
	
	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	public UtilisateurRepository getUtilisateurRepository() {return utilisateurRepository;}
	public void setUtilisateurRepository(UtilisateurRepository utilisateurRepository) {
		this.utilisateurRepository = utilisateurRepository;
	}


	@Autowired
	private RoleRepository roleRepository;
	public RoleRepository getRoleRepository() {return roleRepository;}
	public void setRoleRepository(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String redirectToPublic() {
		return "redirect:/public/";
	}
	
	@RequestMapping(value = "/admin/", method = RequestMethod.GET)
	public String vueAdmin(Model model, Principal utilisateur) {
		model.addAttribute("message",
							"bienvenue venerable admin : " + utilisateur.getName());
		
		return "welcome";
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public String vueUser(Model model, Principal utilisateur) {
		model.addAttribute("message",
							"bienvenue cher utilisateur : " + utilisateur.getName());
		
		return "welcome";
	}

	@RequestMapping(value = "/public/", method = RequestMethod.GET)
	public String vuePublic(Model model, Principal utilisateur) {
		if (utilisateur != null)
			model.addAttribute("message",
							"ouuii, c'est pour quoi ?  : " + utilisateur.getName());
		else
			model.addAttribute("message", "ouuii, c'est pour quoi ?");
		
		return "welcome";
	}

	// afficher le formulaire de login
	@RequestMapping(value="/public/login", method= RequestMethod.GET)
	public String loginForm() {
		return "login";
	}

	
	@RequestMapping(value="/public/register", method= RequestMethod.GET)
	public String registerForm() {
		return "register";
	}
	
	@RequestMapping(value="/public/register", method= RequestMethod.POST)
	public String registerUser(Model model,
							   @RequestParam("username") String username,
							   @RequestParam("password") String password,
							   @RequestParam("confirm") String confirm,
							   @RequestParam("email") String email) {
		Utilisateur u = getUtilisateurRepository().findByUsername(username);
		if (u != null) {
			// utilisateur existant !!!
			return "registerFailure";
		}
		else {
			Pattern passwordPattern1 = Pattern.compile("[a-zA-Z]");
			Pattern passwordPattern2 = Pattern.compile("[-_.!?+=%@0-9]");
			int nbLettre = 0;
			Matcher m1 = passwordPattern1.matcher(password);
			int pos = 0;
			while(m1.find(pos)) {
				nbLettre++;
				pos = m1.end();
			}
			
			int nbSpecial = 0;
			Matcher m2 = passwordPattern1.matcher(password);
			pos = 0;
			while(m2.find(pos)) {
				nbSpecial++;
				pos = m2.end();
			}
			log.info("mot de passe avec " + nbLettre + " lettre et " + nbSpecial + " speciaux");
			if (nbLettre >= 4 && nbSpecial >= 2
					&& password.length() >= 7 && password.equals(confirm)) {
				// OK, format bon et confirmation vérifiée
				Role r = getRoleRepository().findByRoleName("ROLE_USER");
				
				Utilisateur newUser = new Utilisateur(0,
														username,
														email,
														myPasswordEncoder.encode(password),
														true);
				newUser.getRoles().add(r);
				getUtilisateurRepository().save(newUser);
				model.addAttribute("username", newUser.getUsername());
				return "registerSuccess";
			}
		}
		
		return "registerFailure";
	}
	
	
	
	

}