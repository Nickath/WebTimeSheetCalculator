package org.nick.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.nick.form.RegisterForm;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

	
	
	@Autowired
	UserServiceImpl userService;
	
	@RequestMapping(value = "/registerPage", method = RequestMethod.GET)
	public String getRegisterPage(Model model) {
		  RegisterForm form = new RegisterForm();
		  model.addAttribute("registerForm",form);
	      return "register";
	   }
	
	@RequestMapping(value = "/registerAttempt", method = RequestMethod.POST)
	public String registerPost(@Valid  @ModelAttribute("registerForm")RegisterForm form, BindingResult result,
			HttpSession session,  ModelMap model) {
		 
		if (result.hasErrors()) {
	         return "register";
	      }
		
		boolean alreadyExists = userService.registerUser(form);
		
		if(alreadyExists) {
		    model.addAttribute("error","Username already exists");
		    return "register";
		}
		else {
			model.addAttribute("userForm",form);
			return "successfulRegister";
		}
		
		
		
	   }
	
	
	
	
}
