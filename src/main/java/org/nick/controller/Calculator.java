package org.nick.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.nick.form.TimeSheetForm;
import org.nick.service.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class Calculator {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String TimeSheetCalcGetPage(Model model) {
		  TimeSheetForm form = new TimeSheetForm();
		  model.addAttribute("timeSheetForm",form);
	      return "index";
	   }
	
	//,@RequestParam CommonsMultipartFile file
	@RequestMapping(value="/calculate", method = RequestMethod.POST)
	public String TimeSheetCalculator(@ModelAttribute("timeSheetForm") TimeSheetForm form,HttpSession session,ModelMap model) {
		
		String path=session.getServletContext().getRealPath("/");  
		model.addAttribute("file",form.getFile());
		model.addAttribute("pendingDays",form.getPendingDays());
		model.addAttribute("desiredMean",form.getDesiredMean());
		System.out.println(form.toString());
		System.out.println(form.getDesiredMean());
		
		FileHandler fileHandler = new FileHandler();
		
        File myFile = fileHandler.readFile(path,form.getFile()); // get the file
        System.out.println(myFile.length());
        try {
			fileHandler.makeCalculations(myFile,form.getPendingDays(),form.getDesiredMean());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
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