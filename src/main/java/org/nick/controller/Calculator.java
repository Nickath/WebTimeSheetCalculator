package org.nick.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nick.form.TimeSheetForm;
import org.nick.model.Month;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.repository.MonthRepository;
import org.nick.repository.TimeSheetRepository;
import org.nick.repository.UserRepository;
import org.nick.service.impl.FileHandlerImpl;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

//to bind an existing model object to the session (timeSheetForm) and reuse it
@SessionAttributes({"timeSheetForm"})
@Controller
public class Calculator {
	
	private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());


	@Autowired
	UserServiceImpl userService;
	
    @Autowired
    FileHandlerImpl fileHandlerImpl;
    
    @Autowired
    MonthRepository monthRepository;

     
	
	@RequestMapping(value = "calculatePage", method = RequestMethod.GET)
	public String TimeSheetCalcGetPage(Locale locale,Model model,HttpSession session,HttpServletRequest request) {
		  /* if(request.getSession().getAttribute("user") == null) {
			  return "login";
		  }*/
		
		  User user = userService.getAuthenticatedUser();
		  model.addAttribute("user",user);
		  TimeSheetForm form = new TimeSheetForm();
		  model.addAttribute("timeSheetForm",form);
	      return "index";
	   }
	

	@RequestMapping(value="/calculate", method = RequestMethod.POST)
	public String TimeSheetCalculator( @Valid  @ModelAttribute("timeSheetForm")TimeSheetForm form,BindingResult result,
			HttpSession session, HttpServletRequest request,  ModelMap model)    {

		if (result.hasErrors()) {
	         return "index";
	      }
			
		
		LOGGER.info("Logger Name: "+LOGGER.getName());
		
		/* Properties file to be added to get the logger path programmatically
		 * Properties prop = new Properties();
		prop.getProperty("java.util.logging.FileHandler.pattern");*/

		String path=session.getServletContext().getRealPath("/");  
		
	    if(form.getFile().isEmpty()) {
	    	model.addAttribute("error","File is Empty, please enter a valid .xlsx file");
	    	return "index";
	    }
	    else if(!form.getFile().getOriginalFilename().contains(".xlsx")) {
	    	model.addAttribute("error","The file you uploaded is not a .xlsx file");
	    	return "index";
	    }
	    TimeSheet timesheet = new TimeSheet();
	    //get the current month so we write the record in the database pointing to the month ID of the month table
        LocalDate localDate = LocalDate.now();
        String date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate).substring(5,7);
        Long month = Long.parseLong(date);
        Month monthObj = monthRepository.findOne(month);
        timesheet.setMonth(monthObj); 
        //get the logged in user and set it to the timesheet object
        User user = userService.getAuthenticatedUser();
        timesheet.setUser(user);
		model.addAttribute("user",user);
		//write the file and get it 
        File myFile = fileHandlerImpl.readFile(path,form.getFile(),timesheet); // get the file
       
        LOGGER.info("Upload file size in bytes: \n"+myFile.length());

        //make the calculations and set them to the already existing timesheet object
        try {
			 timesheet = fileHandlerImpl.makeCalculations(myFile,form,timesheet);
		} catch (IOException e) {
			LOGGER.severe("ERROR"+e);
			return "index";
		}
       
		model.addAttribute("timesheet",timesheet);
		
		LOGGER.info("The info of the submitted form are: \n"+form.toString());
		if(form.getChecked()) {
			userService.insertOtUpdateTimeSheet(timesheet);
		}
		
		LOGGER.info("The info of the submitted form are: \n"+form.toString());
        
		return "results";
	}
	
	
	

	@RequestMapping(value="/recalculate", method = RequestMethod.POST)
	public String TimeSheetReCalculator( @Valid  @ModelAttribute("timeSheetForm") TimeSheetForm form,
			BindingResult result, HttpSession session,  ModelMap model, HttpServletRequest request)    {

		if (result.hasErrors()) {
	         return "index";
	      }
		
        LOGGER.info("Logger Name: "+LOGGER.getName());
	
		String path=session.getServletContext().getRealPath("/");  
		
		
	    if(form.getFile().isEmpty()) {
	    	model.addAttribute("error","File is Empty, please enter a valid .xlsx file");
	    	return "index";
	    }
	    else if(!form.getFile().getOriginalFilename().contains(".xlsx")) {
	    	model.addAttribute("error","The file you uploaded is not a .xlsx file");
	    	return "index";
	    }
	    
	    
	    TimeSheet timesheet = new TimeSheet();
	    //get the current month so we write the record in the database pointing to the month ID of the month table
        LocalDate localDate = LocalDate.now();
        String date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate).substring(5,7);
        Long month = Long.parseLong(date);
        Month monthObj = monthRepository.findOne(month);
        timesheet.setMonth(monthObj);
        User user = userService.getAuthenticatedUser();
		model.addAttribute("user",user);
        timesheet.setUser(user);
	    
        File myFile = fileHandlerImpl.readFile(path,form.getFile(),timesheet); // get the file
        

        
        LOGGER.info("Upload file size in bytes: \n"+myFile.length());

        try {
			 timesheet = fileHandlerImpl.makeCalculations(myFile,form,timesheet);
		} catch (IOException e) {
			LOGGER.severe("ERROR"+e);
			return "index";
		}

       
		model.addAttribute("timesheet",timesheet);
		
		LOGGER.info("The info of the submitted form are: \n"+form.toString());
		
		if(form.getChecked()) {
		    userService.insertOtUpdateTimeSheet(timesheet);
		}
		
        
		return "results";
 
		
	}
	
	
	
	
	
	
}


