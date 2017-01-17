package com.courtalon.gigaMvcGalerie.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {
	
	
	@RequestMapping(value="/user", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Principal logMeIn(Principal user) {
		return user;
	}
}
