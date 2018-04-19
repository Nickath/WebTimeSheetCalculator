package org.nick.controller;

import org.nick.form.LoginForm;
import org.nick.form.TimeSheetForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
		
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm",loginForm);
		return "login";
	}
	
	
	@RequestMapping(value = "/loginAttempt", method = RequestMethod.POST)
	public String login(@ModelAttribute("loginForm") LoginForm form) {
		
		
		return "/";
	}
}
