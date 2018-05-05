package org.nick.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nick.form.MonthForm;
import org.nick.form.TimeSheetForm;
import org.nick.model.Month;
import org.nick.model.User;
import org.nick.repository.MonthRepository;
import org.nick.repository.UserRepository;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MonthRepository monthRepository;
	
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
	 
	 
	 
	 @RequestMapping(value = "/loadTimeSheetsPage", method = RequestMethod.GET)
	    public String loadTimeSheetsPage(Model model) {
		 User user = userService.getAuthenticatedUser();
		 List<Month> months = monthRepository.findAll();
		 model.addAttribute("user",user);
		 model.addAttribute("months",months);
		 MonthForm form = new MonthForm();
		 form.setMonths(months);
		 model.addAttribute("monthForm",form);
		 return "loadtimesheets";
	 }
	 
	 @RequestMapping(value = "/loadTimeSheetsAttempt", method = RequestMethod.POST)
	    public String loadTimeSheetsAttempt(@ModelAttribute("monthForm")MonthForm form ,Model model) {
		 User user = userService.getAuthenticatedUser();
		 for(Month month:form.getMonths()) {
			 System.out.println(month.getMonth());
		 }
		 model.addAttribute("user",user);
		 return "loadtimesheets";
	 }
	 
	 
	
}
