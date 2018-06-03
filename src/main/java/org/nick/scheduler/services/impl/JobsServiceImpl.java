package org.nick.scheduler.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.nick.model.User;
import org.nick.repository.EmailRepository;
import org.nick.repository.UserRepository;
import org.nick.scheduler.services.JobsService;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class JobsServiceImpl implements JobsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	EmailRepository emailRepository;
	
	@Autowired
	UserService userService;

	@Override
	public List<String> getBelowBaseUsersMail() {
		List<String> belowMeanUsersEmails = new ArrayList<>();
		List<User> users = userRepository.findAll();
		for(User u : users) {
			int meanHours = Character.getNumericValue(userService.getUserCurrentMean(u).charAt(0));
			if(meanHours < 9) {
				belowMeanUsersEmails.add(u.getEmail());
			}
		}
		return belowMeanUsersEmails;
	}
}
