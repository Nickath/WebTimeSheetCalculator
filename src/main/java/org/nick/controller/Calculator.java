package org.nick.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.nick.service.FileHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class Calculator {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String TimeSheetCalcGetPage() {
	      return "index";
	   }
	
	@RequestMapping(value="/calculate", method = RequestMethod.POST)
	public String TimeSheetCalculator(@RequestParam CommonsMultipartFile file,HttpSession session) throws IOException {
		String path=session.getServletContext().getRealPath("/");  
		System.out.println("The path is "+path+""+file.getOriginalFilename());
		File myFile = convert(file);
		
		FileHandler fileHandler = new FileHandler();
		
       // File myFile = fileHandler.readFile(path,file); // get the file
        try {
			fileHandler.makeCalculations(myFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return "results";
	}
	
	
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
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