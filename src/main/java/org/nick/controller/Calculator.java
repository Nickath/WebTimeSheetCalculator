package org.nick.controller;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.nick.form.TimeSheetForm;
import org.nick.model.TimeSheet;
import org.nick.service.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class Calculator {
	
	private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());



	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String TimeSheetCalcGetPage(Model model) {
		  TimeSheetForm form = new TimeSheetForm();
		  model.addAttribute("timeSheetForm",form);
	      return "index";
	   }
	
	//,@RequestParam CommonsMultipartFile file
	@RequestMapping(value="/calculate", method = RequestMethod.POST)
	public String TimeSheetCalculator(@ModelAttribute("timeSheetForm") TimeSheetForm form,HttpSession session,ModelMap model) {
		
		LOGGER.info("Logger Name: "+LOGGER.getName());
		
		/* Properties file to be added to get the logger path programmatically
		 * Properties prop = new Properties();
		prop.getProperty("java.util.logging.FileHandler.pattern");*/

		String path=session.getServletContext().getRealPath("/");  
		
		FileHandler fileHandler = new FileHandler();
        File myFile = fileHandler.readFile(path,form.getFile()); // get the file
        TimeSheet timesheet = new TimeSheet();
        timesheet.setDaysPending(form.getPendingDays());
        timesheet.setDesiredMean(form.getDesiredMean());
        LOGGER.info("Upload file size in bytes: \n"+myFile.length());
        String mean="";
        try {
			 timesheet = fileHandler.makeCalculations(myFile,form.getPendingDays(),form.getDesiredMean(),timesheet);
		} catch (IOException e) {
			LOGGER.severe("ERROR"+e);
		}
		
		
		model.addAttribute("file",form.getFile());
		model.addAttribute("timesheet",timesheet);
		
		LOGGER.info("The info of the submitted form are: \n"+form.toString());
		
		
		
        
		return "results";
	}
}




























/*        
try{  
byte barr[]=file.getBytes();  
  
BufferedOutputStream bout=new BufferedOutputStream(  
         new FileOutputStream(path+"/"+filename));  
bout.write(barr);  
bout.flush();  
bout.close();  
  
}catch(Exception e){
	System.out.println(e);

} */ 