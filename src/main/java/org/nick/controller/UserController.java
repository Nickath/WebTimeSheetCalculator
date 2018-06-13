package org.nick.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.nick.form.MonthForm;
import org.nick.model.EmailSubscription;
import org.nick.model.Month;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.repository.MonthRepository;
import org.nick.repository.UserRepository;
import org.nick.service.impl.FileHandlerImpl;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MonthRepository monthRepository;
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
    FileHandlerImpl fileHandlerImpl;

	
	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public String getHomePage(Model model, HttpSession session,HttpServletRequest request ) {
		User user = userService.getAuthenticatedUser();
		String photo  = userService.getUserImageBase64(user);
		model.addAttribute("photoProfil",photo);
		model.addAttribute("user",user);
		
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(Model model) {
		User user = userService.getAuthenticatedUser();
		if(user!=null) {
			model.addAttribute("user",user);
			return "redirect:/homePage";
		}
		return "welcome";
	}
	
	//for 403 access denied page
	 @RequestMapping(value = "/403", method = RequestMethod.GET)
	    public String accessDenied(Model model, Principal principal) {
	         
	        if (principal != null) {
	            model.addAttribute("message", "Hi " + principal.getName()
	                    + "<br> You do not have permission to access this page!");
	        } else {
	            model.addAttribute("msg",
	                    "You do not have permission to access this page!");
	        }
	        return "403";
	    }
	 
	 //invalid session redirects to login
	 @RequestMapping(value = "/invalidSession", method = RequestMethod.GET)
		public String invalidSession(Model model) {
		
			return "login";
		}
	 
	 
	 
	 @RequestMapping(value = "/loadTimeSheetsPage", method = RequestMethod.GET)
	    public String loadTimeSheetsPage(Model model) {
		 User user = userService.getAuthenticatedUser();
		 model.addAttribute("user",user);
		 String photo  = userService.getUserImageBase64(user);
		 model.addAttribute("photoProfil",photo);
		 List<Month> months = monthRepository.findAll();
		 MonthForm form = new MonthForm();
		 form.setMonths(months);
		 model.addAttribute("months",form);
		 return "loadtimesheets";
	 }
	 
	 @RequestMapping(value = "/loadTimeSheetsAttempt", method = RequestMethod.POST)
	    public String loadTimeSheetsAttempt(HttpSession session, HttpServletRequest request,
	    		@RequestParam("file") MultipartFile file ,@RequestParam("month") String month, Model model) {
		 
		    if(month.equals("") || month.equals(null)) {
		    	model.addAttribute("montherror","Select a month please");
		    	return loadTimeSheetsPage(model); //if error exist, show it and go to the get control of the page
		    }
		    
		    //add attributes to the model first
		    User user = userService.getAuthenticatedUser();
		    model.addAttribute("user",user);
		    String photo  = userService.getUserImageBase64(user);
			model.addAttribute("photoProfil",photo);
		    List<Month> months = monthRepository.findAll();
			MonthForm form = new MonthForm();
			form.setMonths(months);
			model.addAttribute("months",form);
		    	if(!file.isEmpty()) {
		    		if (!file.getOriginalFilename().contains(".xlsx")) {
		    	    	model.addAttribute("error","The file you uploaded is not a .xlsx file");
		    	    	return "loadtimesheets";
		    	    }else { // if user has uploaded valid file insert or update it
		    		String path=session.getServletContext().getRealPath("/");  
		    		TimeSheet timesheet = new TimeSheet();
		    		//set month and username
		    		Month monthObj = monthRepository.findOne(Long.parseLong(month)+1);
		    		timesheet.setMonth(monthObj);
		            timesheet.setUser(user);
		    		File myFile = fileHandlerImpl.readFile(path,(CommonsMultipartFile) file, timesheet); // get the file
		            try {
						timesheet = fileHandlerImpl.makeCalculations(myFile,timesheet);
					} catch (IOException e) {
						e.printStackTrace();
					}
		            
		            timesheet.setFile(myFile);
		            userService.insertOrUpdateTimeSheet(timesheet);

		            
		    	}
		      }
		    
		
			
	
		 return "loadtimesheets";
	 }
	 
	 
	 // watch my Statistics action
	 @RequestMapping(value = "/userStatisticsPage", method = RequestMethod.GET)
	 public String getStatisticsPage(Model model) {
		 
		 User user = userService.getAuthenticatedUser();
		 model.addAttribute("user",user);
		 String photo  = userService.getUserImageBase64(user);
		 model.addAttribute("photoProfil",photo);
		 long userId = user.getId();
		 List<TimeSheet> userTimeSheets = userService.getStatisticsByUserId(userId);
		 model.addAttribute("timesheetList", userTimeSheets);
		 return "userStatisticsPage";
		 
		 
	 }
	 
	 
	    @RequestMapping(value = "/deleteMonth/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public String deleteMonth(@PathVariable("id") long id) {
		     User user = userService.getAuthenticatedUser();
			 userService.deleteTimeSheetByMonth(id,user.getId());
			 return "redirect:/userStatisticsPage";
		}	
	 
	 
	    @RequestMapping(value = "/downloadMonth/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
		public void downloadMonth(@PathVariable("id") long id, HttpServletResponse response) {
		     User user = userService.getAuthenticatedUser();
			 userService.downloadTimeSheetByMonth(id,user.getId(),response);
			 //return "redirect:/userStatisticsPage";
		}	
	    
	    @RequestMapping(value = "/accountEnablePage", method = RequestMethod.GET)
        public String enableAccountPage(Model model) {
	    	User user = userService.getAuthenticatedUser();
	    	if(user!=null) {
	    		return "home";
	    	}
	    	return "enableAccountPage";
	    }
	    
	    
	    @RequestMapping(value = "/activateUser", method = RequestMethod.POST)
        public String checkUsername(@RequestParam String username, @RequestParam String password,
        		@RequestParam String passwordconfirm, Model model) {
	    	User user = userService.getAuthenticatedUser();
	    	if(user!=null) {
	    		return "home";
	    	}
	    	if(userService.userAwaitsEnable(username)) {
	    		if(password.equals(passwordconfirm)) {
	    			userService.activateUser(true, username, password,"");
	    			model.addAttribute("success","Account was successfully enabled Mr/Mrs "+username+", "
	    					+ "click <a href=\"/WebTimeSheetCalculator/loginPage\">here</a> to login");
	    		}else {
	    			model.addAttribute("passworderror", "Password should be the same with the confirmation");
	    		}
	    	}
	    	return "enableAccountPage";
	    }
	    
	    
	    @RequestMapping(value = "/subscribeMailPage", method = RequestMethod.GET)
	    public String subscribePage(Model model) {
	    	User user = userService.getAuthenticatedUser();
	    	model.addAttribute("user",user);
			EmailSubscription s = userService.isUserSubscribed(user);
			model.addAttribute("usersubscription",s);
	    	String photo  = userService.getUserImageBase64(user);
			model.addAttribute("photoProfil",photo);

	    	return "subscribepage";
	    }
	   
	 
	
}
	 
