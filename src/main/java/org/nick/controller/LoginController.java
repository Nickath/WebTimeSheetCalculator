package org.nick.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nick.form.LoginForm;
import org.nick.model.User;
import org.nick.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
		
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm",loginForm);
		return "login";
	}
	
	
	@RequestMapping(value = "/loginAttempt", method = RequestMethod.POST)
	public String login(@Valid  @ModelAttribute("loginForm")LoginForm form, BindingResult result,
			HttpSession session,  ModelMap model) {
		
		if(result.hasErrors()) {
			return "login";
		}
		
		List<User> existingUsers = userRepository.findAll();
		for(User user :existingUsers) {
			if(user.getUsername().equals(form.getUsername()) && user.getPassword().equals(form.getPassword())){
				model.addAttribute("username",user.getUsername());
				return "successfulLogin";
			}
		}
		
		model.addAttribute("invalidCreds","Wrong username or password");
		
		return "login";
	}
}
