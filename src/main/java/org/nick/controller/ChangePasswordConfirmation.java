package org.nick.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nick.model.User;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChangePasswordConfirmation {

	
	@Autowired
	UserService userService;
	    
	        @RequestMapping(value = "/changePasswordRequest/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
			public String changePasswordRequest(@PathVariable("id") String id, HttpServletRequest req,
					HttpServletResponse resp, HttpSession session) {
	        	 
                 User hasAskedForPassChange = userService.searchChangeRequestPasswordUserByID(id);
                 if(hasAskedForPassChange != null) {
                	req.setAttribute("changeID", id);
                	session.setAttribute("changeID", id);
                    return "redirect:/changePasswordPage";
                 }
	        	 
	 			 return "redirect:/homePage";
	 		}	
	        
	        @RequestMapping(value = "/changePasswordPage", method = RequestMethod.GET)
			public String redirectToChangePasswordPage() {
                    return "changePasswordPage";
                
	 		}
	        
}
