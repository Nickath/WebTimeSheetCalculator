package org.nick.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nick.controller.Calculator;
import org.nick.email.AccountConfirmation;
import org.nick.form.RegisterForm;
import org.nick.model.EmailSubscription;
import org.nick.model.Role;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.repository.EmailRepository;
import org.nick.repository.TimeSheetRepository;
import org.nick.repository.UserRepository;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
    TimeSheetRepository repository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	AccountConfirmation accountConfirmationService;
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());



	
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
				for(User user : existingUsers) {
					if(user.getUsername().equals(form.getUsername()) || user.getEmail().equals(form.getEmail())){
						return true;
					}
				}
				User user = new User();
				user.setEmail(form.getEmail());
				user.setUsername(form.getUsername());
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //encrypt the password using BCryptPasswordEncoder
				String hashedPassword = passwordEncoder.encode(form.getPassword());
				user.setPassword(hashedPassword);
				user.setRole(new Role(2L));   //sets the user role to "2,user", (default) not any user is authorized to create admin user
				//user.setEnabled(true);
				user.setPhoto(getDefaultImage());
				
				//generate the unique user id to confirm password
				String randomId = java.util.UUID.randomUUID().toString();
				user.setConfirmId(randomId);
				
				//send the verification email to the registered user to enable account
				accountConfirmationService.sendConfirmationMail(user.getEmail(),user.getConfirmId());
				
				userRepository.save(user);
				return false;
		
	}
	
	@Override
	public boolean registerUserObj(User user) {
		List<User> existingUsers = userRepository.findAll();
		for(User u : existingUsers) {
			if(u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail())){
				return true;
			}
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //encrypt the password using BCryptPasswordEncoder
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);  
		user.setEnabled(user.isEnabled()==true?true:false);
		user.setRole(user.getRole()==null?new Role(2L):user.getRole());
		user.setPhoto(user.getPhoto()==null?getDefaultImage():user.getPhoto());
		//generate the unique user id to confirm password
		String randomId = java.util.UUID.randomUUID().toString();
		user.setConfirmId(randomId);
		
		//send the verification email to the registered user to enable account
		accountConfirmationService.sendConfirmationMail(user.getEmail(),user.getConfirmId());
		userRepository.save(user);
		return false;
	}


    //updates a timesheet if already exists, inserts it otherwise
	@Override
	public boolean insertOrUpdateTimeSheet(TimeSheet timesheet) {
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
            LOGGER.info("Your excel file has been generated! in "+path.toString()+" with name "
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
            LOGGER.severe(ex.toString());
        }
	}
	
	
	@Override
	public void createXML(File file,HttpServletResponse response) {
		try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);
            LOGGER.info("Your xml file has been generated! in "+path.toString()+" with name "
            		+ ""+ (file.getPath()) );
            response.setContentType("text/xml");
            response.setHeader("Content-Disposition", "attachment; filename=profil_"+getAuthenticatedUser().getUsername()
            		+".xml");
            try
            {
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
            }
            
            catch (IOException ex) {
                ex.printStackTrace();
            }
            		
        } catch ( Exception ex ) {
            LOGGER.severe(ex.toString());
        }
	}
	
	
	@Override
	public void createPDF(File file,HttpServletResponse response) {
		try {
            Path path = Paths.get(file.getPath());
            byte[] data = Files.readAllBytes(path);
            LOGGER.info(("Your pdf file has been generated! in "+path.toString()+" with name "
            		+ ""+ (file.getName()) ));
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=statistics.pdf");
            
            /*alternative way to flush the file 
            InputStream is;

            try {
                is = new FileInputStream(path.toString());
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
                is.close(); 
            } catch (FileNotFoundException e) {
                LOGGER.severe(e.toString());
                e.printStackTrace();
            } catch (IOException e) {
            	 LOGGER.severe(e.toString());
                e.printStackTrace();
            }
            /*end of alternative way to flush the file */
            try
            {
                Files.copy(path, response.getOutputStream());
                response.getOutputStream().flush();
            }
            
            catch (IOException ex) {
                ex.printStackTrace();
            }
            		
        } catch ( Exception ex ) {
        	LOGGER.severe(ex.toString());
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
		if(user.getId()==null) {
			return false;
		}
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
	public void updateUserUsername(User currentUser) {
		User userToUpdate = userRepository.findOne(currentUser.getId());
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

	@Override
	public long getLastUserId() {
		List<User> users = userRepository.findAll();
		Collections.sort(users);
		for(User u : users) {//clear the list from null because for some reason null objects are retrieved
			if(u==null || u.getId()==null) {
				users.remove(u);
			}
		}
		return users.get(users.size()-1).getId();
	}

	@Override
	public boolean usernameExists(User user) {
		List<User> users = userRepository.findAll();
		for(User u : users) {//clear the list from null because for some reason null objects are retrieved
			if(u.getUsername().equals(user.getUsername())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean userAwaitsEnable(String username) {
		List<User> users = userRepository.findAll();
		for(User u : users)
		{
			if(u.getUsername().equals(username) && !u.isEnabled() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void activateUser(boolean enabled, String username, String password, String confirmid) {
		userRepository.activateUser(enabled, username, password,"");
		
	}
	
	@Override
	public String getUserImageBase64(User user) {
	   byte[] imageData = user.getPhoto();
	   if(imageData==null) {
		   return null;
	   }
       byte[] encodeBase64 = Base64.encodeBase64(imageData);
       try {
			String base64Encoded = new String(encodeBase64, "UTF-8");
			return base64Encoded;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return null;
	}
	
	@Override
	public byte[] getDefaultImage() {
		File fi = new File("C:\\Users\\NICK\\eclipse-workspace\\WebTimeSheetCalculatorTestBranch\\WebTimeSheetCalculatorTestBranch\\WebContent\\resources\\images\\defaultPhoto.png");
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(fi.toPath());
			return fileContent;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void uploadPhoto() {
		
		
	}

	@Override
	public void deletePhoto() {
		
		
	}

	@Override
	public boolean insertOrUpdateUser(User user) {
		User candidateUser = userRepository.findByUsername(user.getUsername());
		if(candidateUser==null) {
			userRepository.save(user);
		}
		else {
			userRepository.updateUser(user.getEmail(), user.isEnabled(),
					user.getPhoto(),user.getUsername());
		}
		return false;
	}

	@Override
	public EmailSubscription isUserSubscribed(User user) {
		List<EmailSubscription> subscriptions = emailRepository.findAll();
		for(EmailSubscription s : subscriptions) {
			if(s.getUser().getId().equals(user.getId())) {
				return s;
			}
		}
		return null;
	}

	@Override
	public void subscribeUser(User user) {
		if(isUserSubscribed(user) == null) {
			emailRepository.save(new EmailSubscription(user, getCurrentTime()));
		}
		
	}

	@Override
	public void unsubscribeUser(User user) {
		List<EmailSubscription> list = emailRepository.findAll();
		for(EmailSubscription e : list) {
			if(e.getUser().getId().equals(user.getId())) {
				emailRepository.delete(e);
				return;
			}
		}
		
	}

	@Override
	public String getUserCurrentMean(User user) {
		List<TimeSheet> timesheets = repository.findAll();
		//get current month
		LocalDate localDate = LocalDate.now();
        String date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate).substring(5,7);
        Long currentMonth = Long.parseLong(date)+1;
		for(TimeSheet timesheet : timesheets) {
			if(timesheet.getUser().getId().equals(user.getId()) && timesheet.getMonth().getId() == currentMonth) {
				return timesheet.getMean();
			}
		}
		return null;
	}

	@Override
	public User searchConfirmUserByID(String id) {
		List<User> existingUsers = userRepository.findAll();
		for(User user : existingUsers) {
			if(user.getConfirmId()!=null && user.getConfirmId()!="") {
			    if(user.getConfirmId().equals(id) && user.isEnabled() == false) {
				   return user;
			    }
			}
		}
		return null;
	}

	@Override
	public boolean mailExists(String mail) {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			if(user.getEmail().equals(mail)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User searchChangeRequestPasswordUserByID(String id) {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			if(user.getChangePassRequestID().equals(id)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User findByUsername(String username) {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User findByEmail(String email) {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			if(user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void writeChangePassRequestInDB(User user, String id) {
		user.setChangePassRequestID(id);
		userRepository.save(user);
		
	}

	@Override
	public void changePasswordUsingCRid(String id, String newpassword) {
		User user = findByChangePasswordID(id);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //encrypt the password using BCryptPasswordEncoder
		String hashedPassword = passwordEncoder.encode(newpassword);
		user.setPassword(hashedPassword);
		user.setChangePassRequestID("");
		userRepository.save(user);
		
	}

	private User findByChangePasswordID(String id) {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			if(user.getChangePassRequestID().equals(id)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public User customUserlXMLAuthentication(String username, String password) {
		List<User> users = userRepository.findAll();
		for(User user : users) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
	
	
}
