package org.nick.controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nick.form.TimeSheetForm;
import org.nick.model.TimeSheet;
import org.nick.service.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class Calculator {
	
	private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());


    @Autowired
    FileHandler fileHandler;
    

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String TimeSheetCalcGetPage(Locale locale,Model model) {
		  TimeSheetForm form = new TimeSheetForm();
		  model.addAttribute("timeSheetForm",form);
	      return "index";
	   }
	

	@RequestMapping(value="/calculate", method = RequestMethod.POST)
	public String TimeSheetCalculator( @Valid  @ModelAttribute("timeSheetForm")TimeSheetForm form, BindingResult result,
			HttpSession session,  ModelMap model)    {

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
        File myFile = fileHandler.readFile(path,form.getFile()); // get the file
        TimeSheet timesheet = new TimeSheet();

        
        LOGGER.info("Upload file size in bytes: \n"+myFile.length());

        try {
			 timesheet = fileHandler.makeCalculations(myFile,form.getPendingDays(),form.getDesiredMean());
		} catch (IOException e) {
			LOGGER.severe("ERROR"+e);
			return "index";
		}
		
		
		model.addAttribute("file",form.getFile());
		model.addAttribute("timesheet",timesheet);
		
		LOGGER.info("The info of the submitted form are: \n"+form.toString());
		
		
		
        
		return "results";
	}
}























