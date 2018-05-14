package org.nick.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nick.model.User;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/getLastUpdate")
public class SelectBoxAjaxController {
	

    @Autowired
    UserServiceImpl userService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String getLastUpdate(@RequestParam("month") String month) {
		
		
		long monthID = Long.parseLong(month)+1;
		User user = userService.getAuthenticatedUser();
		Date lastUpdate = userService.getLastUpdateOfTimesheetForMonth(user, monthID);
		if(lastUpdate!=null) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");//return to String format
		String lastUpdateString = df.format(lastUpdate);
		return lastUpdateString;
		}
		else {
			return "";
		}
		
	}
	
	
}