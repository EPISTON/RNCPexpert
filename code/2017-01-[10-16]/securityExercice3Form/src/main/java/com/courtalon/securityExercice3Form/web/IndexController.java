package com.courtalon.securityExercice3Form.web;

import java.security.Principal;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.courtalon.securityExercice3Form.metier.Auteur;
import com.courtalon.securityExercice3Form.metier.Post;
import com.courtalon.securityExercice3Form.repositories.AuteurRepository;
import com.courtalon.securityExercice3Form.repositories.PostRepository;
import com.courtalon.securityExercice3Form.utils.NettoyeurHtml;



@Controller
@RequestMapping(value="/")
public class IndexController {

	@Autowired
	private PostRepository postRepository;
	public PostRepository getPostRepository() {return postRepository;}
	public void setPostRepository(PostRepository postRepository) {this.postRepository = postRepository;}

	@Autowired
	private AuteurRepository auteurRepository;
	public AuteurRepository getAuteurRepository() {	return auteurRepository;}
	public void setAuteurRepository(AuteurRepository auteurRepository) {this.auteurRepository = auteurRepository;}
	
	@Autowired
	private NettoyeurHtml nettoyeurHtml;
	public NettoyeurHtml getNettoyeurHtml() {return nettoyeurHtml;}
	public void setNettoyeurHtml(NettoyeurHtml nettoyeurHtml) {this.nettoyeurHtml = nettoyeurHtml;}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirectToIndex() {
		return "redirect:/welcome";
	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		return "welcome";
	}
	
	@RequestMapping(value = "/posts/", method = RequestMethod.GET)
	public ModelAndView listePost() {
		ModelAndView mv = new ModelAndView("liste");
		mv.addObject("posts", getPostRepository().findByPublished(true));
		return mv;
	}
	
	@RequestMapping(value = "/posts/search", method = RequestMethod.GET)
	public ModelAndView searchPost(@RequestParam("searchTerm") String searchTerm) {
		ModelAndView mv = new ModelAndView("liste");
		mv.addObject("posts", getPostRepository()
							.findByTitreContainingAndPublished(searchTerm, true));
		return mv;
	}
	
	@RequestMapping(value = "/posts/create", method = RequestMethod.GET)
	public ModelAndView createPost() {
		ModelAndView mv = new ModelAndView("edit");
		mv.addObject("post", new Post(0, "", "", true));
		mv.addObject("auteurs", getAuteurRepository().findAll());
		return mv;
	}

	
	@RequestMapping(value = "/posts/edit/{id:[0-9]+}", method = RequestMethod.GET)
	public ModelAndView editPost(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("edit");
		Post p = getPostRepository().findOne(id);
		if (p == null || !p.isPublished())
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "post not found");
		mv.addObject("post", p);
		mv.addObject("auteurs", getAuteurRepository().findAll());
		return mv;
	}
	
	// Principal représnete l'identite de l'utilisateur loggé
	// il sera automatiquement injecté par spring
	@RequestMapping(value = "/posts/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("post") Post post,
						Principal principal) {
		String auteurName = principal.getName();
		
		// on peut aussi passer par le SecurityContextHolder
		// SecurityContextHolder.getContext().getAuthentication();
		
		Post originalPost = getPostRepository().findOne(post.getId());
		if (originalPost == null) {
			// nouveau post
			post.setAuteur(getAuteurRepository().findByNom(auteurName));
			post.setTitre(getNettoyeurHtml().sanitizeTitre(post.getTitre()));
			post.setCorps(getNettoyeurHtml().sanitizeCorps(post.getCorps()));
			originalPost = post;
		}
		else if (!originalPost.isPublished()) {
			throw new HttpClientErrorException(HttpStatus.METHOD_NOT_ALLOWED, "interdit!");
		}
		else {
			if (!originalPost.getAuteur().getNom().equals(auteurName)) {
				throw new HttpClientErrorException(HttpStatus.METHOD_NOT_ALLOWED,
						"interdit d'editer le post d'un autre!");				
			}
			Auteur a = getAuteurRepository().findByNom(auteurName);
			originalPost.setTitre(getNettoyeurHtml().sanitizeTitre(post.getTitre()));
			originalPost.setCorps(getNettoyeurHtml().sanitizeCorps(post.getCorps()));
			originalPost.setAuteur(a);
		}
		getPostRepository().save(originalPost);
		return "redirect:/posts/";
	}
	
	@RequestMapping(value="/posts/remove/{id:[0-9]+}", method=RequestMethod.POST)
	public String removePost(@PathVariable("id") int id) {
		getPostRepository().delete(id);
		return "redirect:/posts/";
	}
	
	
}