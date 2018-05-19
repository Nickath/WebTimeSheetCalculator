package org.nick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	@RequestMapping(value = "usersPage", method = RequestMethod.GET)
    public String getAdminPage() {
        return "userManagement";
    }
}
