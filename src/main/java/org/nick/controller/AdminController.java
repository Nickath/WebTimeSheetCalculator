package org.nick.controller;

import org.nick.model.User;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
	
	@Autowired 
	UserService userService;

	@RequestMapping(value = "usersPage", method = RequestMethod.GET)
    public String getAdminPage(Model model) {
		User user = userService.getAuthenticatedUser();
		model.addAttribute("user",user);
		String photo  = userService.getUserImageBase64(user);
		model.addAttribute("photoProfil",photo);
        return "userManagement";
    }
}
