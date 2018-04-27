package org.nick.controller;

import javax.servlet.http.HttpServletRequest;

import org.nick.form.LoginForm;
import org.nick.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model,HttpServletRequest request) {
		
		LoginForm loginForm = new LoginForm();
		request.getSession().invalidate();//destroying the session
		User user = new User();
		model.addAttribute("loginForm",loginForm);
		model.addAttribute("user",user); // filling the user object as empty
		return "login";
	}
}
