package org.nick.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nick.model.User;
import org.nick.repository.UserRepository;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserServiceImpl userService;

	
	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public String getLoginPage(Model model, HttpSession session,HttpServletRequest request ) {
		User user = userService.getAuthenticatedUser();
		model.addAttribute("user",user);
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(Model model) {
	
		return "welcome";
	}
	
	//for 403 access denied page
	 @RequestMapping(value = "/403", method = RequestMethod.GET)
	    public String accessDenied(Model model, Principal principal) {
	         
	        if (principal != null) {
	            model.addAttribute("message", "Hi " + principal.getName()
	                    + "<br> You do not have permission to access this page!");
	        } else {
	            model.addAttribute("msg",
	                    "You do not have permission to access this page!");
	        }
	        return "403";
	    }
	 
	 @RequestMapping(value = "/invalidSession", method = RequestMethod.GET)
		public String invalidSession(Model model) {
		
			return "login";
		}
	 
	 
	 
	
}
