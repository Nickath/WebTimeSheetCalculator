package org.nick.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nick.form.RegisterForm;
import org.nick.model.Role;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.repository.TimeSheetRepository;
import org.nick.repository.UserRepository;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
    TimeSheetRepository repository;

	@Autowired
	UserRepository userRepository;

	
	public List<TimeSheet> getAllTimeSheets(){
		return repository.findAll();
	}

	//updates the timesheet 
	@Override
	public void updateTimeSheet(TimeSheet timesheet) {
		repository.updateTimeSheetByID(timesheet.getFile(), timesheet.getDesiredMean(), timesheet.getRestAverage(), getCurrentTime(),
				timesheet.getMean(),timesheet.getInsertMean(), timesheet.getExitMean(), timesheet.getWorkingDays(), timesheet.getMonth().getId(), timesheet.getUser().getId());
	}

	//inserts a timesheet
	@Override
	public void insertTimeSheet(TimeSheet timesheet) {
		timesheet.setLastUpdate(getCurrentTime());
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


    //updates a timesheet if already exists, inserts it otherwise
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
	
	//get current time
	@Override 
	public Date getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		return now;
	}
	
	//get last update date of timesheet for a month
	@Override
	public Date getLastUpdateOfTimesheetForMonth(User user, long month) {
		long userID = user.getId();
		Date date = repository.getLastUpdateOfTimeSheet(userID, month);
		return date;
	}

	@Override
	public List<TimeSheet> getStatisticsByUserId(long  userID) {
		List<TimeSheet> list = repository.getStatisticsPerUser(userID);
		return list;
	}

	@Override
	public void deleteTimeSheetByMonth(long id,long userId) {
		List<TimeSheet> list = repository.findAll();
		for(TimeSheet timesheet:list) {
			if(timesheet.getMonth().getId()==id && timesheet.getUser().getId()==userId) {
				long idToBeDeleted = timesheet.getId();
				repository.delete(idToBeDeleted);
			}
		}
		
	}
	
	@Override
	public void downloadTimeSheetByMonth(long id,long userId,HttpServletResponse response) {
		List<TimeSheet> list = repository.findAll();
		for(TimeSheet timesheet:list) {
			if(timesheet.getMonth().getId()==id && timesheet.getUser().getId()==userId) {
				File file = timesheet.getFile();
				String month = timesheet.getMonth().getMonth();
				createXLS(month,file,response);
			}
		}
		
	}
	
	@Override
	public void createXLS(String month, File file,HttpServletResponse response) {
		try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);
            System.out.println("Your excel file has been generated! in "+path.toString()+" with name "
            		+ ""+ (file.getPath()+"TimeSheet"+month+".xls") );
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=TimeSheet"+getAuthenticatedUser().getUsername()
            		+""+month+".xls");
            try
            {
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
            }
            
            catch (IOException ex) {
                ex.printStackTrace();
            }
            		
        } catch ( Exception ex ) {
            System.out.println(ex);
        }
	}

	@Override
	public List<User> findAllUsers() {
		List<User> list = userRepository.findAll();
		return list;
	}

	@Override
	public User findById(long id) {
		return userRepository.findOne(id);
	}

	@Override
	public boolean isUserExist(User user) {
		List<User> list = findAllUsers();
		for(User u : list) {
			if(user.getId().equals(u.getId()) || (user.getId() == u.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateUser(User currentUser) {
		User userToUpdate = userRepository.getOne(currentUser.getId());
		userToUpdate.setUsername(currentUser.getUsername());
		userRepository.save(userToUpdate);
	}

	@Override
	public void deleteUserById(long id) {
		
		List<User> users = userRepository.findAll();
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                //delete the timesheets belonging to user not to violate the FK
                List<TimeSheet> timesheetsToDelete = repository.getStatisticsPerUser(id);
                for(TimeSheet t : timesheetsToDelete) {
                	repository.delete(t);
                }
                userRepository.delete(id);
            }
        }
		
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}
	
	
	
	
	
}
