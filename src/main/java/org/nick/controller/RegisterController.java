package org.nick.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nick.form.RegisterForm;
import org.nick.model.Role;
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
public class RegisterController {

	@Autowired
	UserRepository userRepository;
	
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
		//if username already exists, do not allow, else write him on database
		List<User> existingUsers = userRepository.findAll();
		for(User user :existingUsers) {
			if(user.getUsername().equals(form.getUsername())){
				model.addAttribute("error","Username exists");
				return "register";
			}
		}
		User user = new User();
		user.setEmail(form.getEmail());
		user.setUsername(form.getUsername());
		user.setPassword(form.getPassword());
		user.setRole(new Role(2L));   //sets the user role to "2,user", (default) not any user is authorized to create admin user
		userRepository.save(user);
		
		
		return "successfulRegister";
	   }
	
	
	
	
}