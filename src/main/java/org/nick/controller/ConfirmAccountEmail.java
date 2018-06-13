package org.nick.controller;


import org.nick.model.User;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConfirmAccountEmail {

	@Autowired
	UserService userService;
	    
	        @RequestMapping(value = "/confirmAccount/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
			public String emailConfirmation(@PathVariable("id") String id) {
                 User isUserToBeEnabled = userService.searchConfirmUserByID(id);
                 if(isUserToBeEnabled != null) {
                	 userService.activateUser(true, isUserToBeEnabled.getUsername(), isUserToBeEnabled.getPassword(),"");
                 }
	        	 
	 			 return "redirect:/loginPage";
	 		}	
	 	 
}
