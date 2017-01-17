package com.courtalon.securityExercice3Form.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.courtalon.securityExercice3Form.metier.Auteur;
import com.courtalon.securityExercice3Form.metier.Role;
import com.courtalon.securityExercice3Form.repositories.AuteurRepository;
import com.courtalon.securityExercice3Form.repositories.RoleRepository;
import com.courtalon.securityExercice3Form.utils.MyPasswordEncoder;

@Controller
@RequestMapping("/")
public class AccessController {

	@Autowired
	private AuteurRepository auteurRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MyPasswordEncoder myPasswordEncoder;
	
	@RequestMapping(value="/loginForm", method=RequestMethod.GET )
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value="/registerForm", method=RequestMethod.GET )
	public String registerForm() {
		return "register";
	}

	@RequestMapping(value="/register", method= RequestMethod.POST)
	public String registerUser(Model model,
							   @RequestParam("nom") String nom,
							   @RequestParam("password") String password,
							   @RequestParam("confirm") String confirm,
							   @RequestParam("email") String email) {
		Auteur a = auteurRepository.findByNom(nom);
		if (a != null) {
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
			Matcher m2 = passwordPattern2.matcher(password);
			pos = 0;
			while(m2.find(pos)) {
				nbSpecial++;
				pos = m2.end();
			}
			if (nbLettre >= 4 && nbSpecial >= 2
					&& password.length() >= 7 && password.equals(confirm)) {
				// OK, format bon et confirmation vérifiée
				Role r = roleRepository.findByRoleName("ROLE_USER");
				
				Auteur newUser = new Auteur(0,
														nom,
														email,
														myPasswordEncoder.encode(password),
														true);
				newUser.getRoles().add(r);
				auteurRepository.save(newUser);
				model.addAttribute("nom", newUser.getNom());
				return "registerSuccess";
			}
		}
		
		return "registerFailure";
	}
	
	
}
