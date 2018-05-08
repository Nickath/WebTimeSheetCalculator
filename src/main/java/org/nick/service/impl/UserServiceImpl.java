package org.nick.service.impl;

import java.util.List;

import org.nick.form.RegisterForm;
import org.nick.model.Role;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.repository.TimeSheetRepository;
import org.nick.repository.UserRepository;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserServiceImpl implements UserService {

	@Autowired
    TimeSheetRepository repository;

	@Autowired
	UserRepository userRepository;

	
	public List<TimeSheet> getAllTimeSheets(){
		return repository.findAll();
	}

	@Override
	public void updateTimeSheet(TimeSheet timesheet) {
		repository.updateTimeSheetByID(timesheet.getFile(), timesheet.getDesiredMean(), timesheet.getRestAverage(),
				timesheet.getMonth().getId(), timesheet.getUser().getId());
		
	}



	@Override
	public void insertTimeSheet(TimeSheet timesheet) {
		repository.save(timesheet);
	}



	@Override
	public boolean registerUser(RegisterForm form) {
		//if username already exists, do not allow, else write him on database
				List<User> existingUsers = userRepository.findAll();
				for(User user :existingUsers) {
					if(user.getUsername().equals(form.getUsername())){
						return true;
					}
				}
				User user = new User();
				user.setEmail(form.getEmail());
				user.setUsername(form.getUsername());
				user.setPassword(form.getPassword());
				user.setRole(new Role(2L));   //sets the user role to "2,user", (default) not any user is authorized to create admin user
				user.setEnabled(true);
				userRepository.save(user);
				return false;
		
	}



	@Override
	public boolean insertOtUpdateTimeSheet(TimeSheet timesheet) {
		List<TimeSheet> list = repository.findAll();
		for(TimeSheet t :list) {
			if(t.getUser().getId().equals(timesheet.getUser().getId()) && t.getMonth().getId().equals(timesheet.getMonth()
					.getId())) {
				updateTimeSheet(timesheet);
				return true;
			}
		}
		insertTimeSheet(timesheet);
		return false;
	}


	//to get the authenticated logged in user when is needed

	@Override
	public User getAuthenticatedUser() {
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 String username = auth.getName();
			 Object credentials = auth.getCredentials();
			 User user = userRepository.findByUsername(username);
			 return user;
		 
	}
}
