package org.nick.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public String getLoginPage(Model model, HttpSession session,HttpServletRequest request ) {

		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(Model model) {
	
		return "welcome";
	}
	
	//for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

	  ModelAndView model = new ModelAndView();
		
		  //check if user is login
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (!(auth instanceof AnonymousAuthenticationToken))   {
	  UserDetails userDetail = (UserDetails) auth.getPrincipal();	
	  model.addObject("username", userDetail.getUsername());
		  }
			
		  model.setViewName("403");
		  return model;

		}
}
