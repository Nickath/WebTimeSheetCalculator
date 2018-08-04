package org.nick.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.nick.model.User;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;


@Controller
public class AjaxController {
	

    @Autowired
    UserServiceImpl userService;

	@RequestMapping(value="/getLastUpdate", method = RequestMethod.POST)
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
	
	@RequestMapping(value="/searchAccount", method = RequestMethod.POST)
	public @ResponseBody String searchUserAwaiting(@RequestParam("username") String username) {
		if(userService.userAwaitsEnable(username)) {
			return "User "+username +" awaits to be enabled";
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="/uploadPhoto", method = RequestMethod.POST,
			produces = {"application/json"})
	public @ResponseBody void uploadPhoto(MultipartHttpServletRequest request,
            HttpServletResponse response) {
		    MultipartFile multipartFile = request.getFile("photo");
	        Long size = multipartFile.getSize();
	        String contentType = multipartFile.getContentType();
	        InputStream stream;
			try {
				stream = multipartFile.getInputStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				User user = userService.getAuthenticatedUser();
				user.setPhoto(bytes);
				userService.insertOrUpdateUser(user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	
		
	}
	
	@RequestMapping(value="/deletePhoto", method = RequestMethod.POST)
	public @ResponseBody void deletePhoto(HttpServletResponse response ) {
		User user = userService.getAuthenticatedUser();
		user.setPhoto(userService.getDefaultImage());
		userService.insertOrUpdateUser(user);
	}
	
	
	@RequestMapping(value="/subsribeAction", method = RequestMethod.POST)
	public @ResponseBody void subscribeAction(@RequestParam("subscribed") boolean subscribed) {
        User user = userService.getAuthenticatedUser();
		if(subscribed == true) {
			userService.subscribeUser(user);
		}
		else {
			userService.unsubscribeUser(user);
		}
	}
	
	@RequestMapping(value="/addRecipient", method = RequestMethod.POST)
	public @ResponseBody String addRecipient(@RequestParam("recipient") String recipient,
			HttpServletResponse response) {
            List<User> allUsers = userService.excludeCurrentUser();
            Gson gson = new Gson();
			String usersJson = gson.toJson(allUsers);
			return usersJson;
	}
	
	
	
	
}
