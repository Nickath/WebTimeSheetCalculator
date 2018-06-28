package org.nick.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.nick.form.LoginForm;
import org.nick.form.LoginFormXML;
import org.nick.form.RegisterForm;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.model.UserXML;
import org.nick.service.UserService;
import org.nick.service.impl.FileHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class ControllerXML {
	
	private static final Logger LOGGER = Logger.getLogger(ControllerXML.class.getName());
	
	@Autowired 
	UserService userService;
	
//	@Autowired
//	AuthenticationManager authManager;
	
	
	
	//go to download xml page
    @RequestMapping(value = "/downloadXMLPage", method = RequestMethod.GET)
    public String getDownloadXMLPage(Model model) {
    	User user = userService.getAuthenticatedUser();
    	model.addAttribute("user",user);
    	return "downloadXmlPage";
    }
    
    //download xml profil
    @RequestMapping(value = "/downloadXMLAttempt", method = RequestMethod.GET, headers = "Accept=application/json")
    public void downloadXmlAttempt(HttpServletResponse response) {
    	User user = userService.getAuthenticatedUser();
    	File file = new File("C:/Users/NICK/profil.xml");
    	UserXML userxml = convertUserToUserXML(user);
    	JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(UserXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(userxml, file);
			jaxbMarshaller.marshal(userxml, System.out);
			userService.createXML(file, response);
		} catch (JAXBException e) {
			LOGGER.severe(e.toString());
			e.printStackTrace();
		}
    	

    	
    }

	@RequestMapping(value = "/loginXmlPage", method = RequestMethod.GET)
	public String getXMLLoginPage(Model model, HttpSession session,HttpServletRequest request ) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
		    /* The user is logged in :) */
			User user = userService.getAuthenticatedUser();
			model.addAttribute("user",user);
		    return "redirect:/homePage";
		}
		return "loginXmlPage";
	}
	
	@RequestMapping(value = "/loginXMLAttempt", method = RequestMethod.POST)
	public String loginAttemptXML(@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,  ModelMap model) throws IllegalStateException, IOException {
		
		   String path=session.getServletContext().getRealPath("/"); 
		   try {
			
			/*File testfile = new java.io.File("C:/Users/NICK/"+file.getOriginalFilename());
			testfile.getParentFile().mkdirs(); // correct!
			if (!testfile.exists()) {
			    ((File) testfile).createNewFile();
			} */
			File file2 = convert(file, path);
			JAXBContext jaxbContext = JAXBContext.newInstance(UserXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			UserXML user = (UserXML) jaxbUnmarshaller.unmarshal(file2);
            User normalUser = new User(user);
            
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		  
		 return "";
	}
	
/*	public void login(HttpServletRequest req, String user, String pass) { 
	    UsernamePasswordAuthenticationToken authReq
	      = new UsernamePasswordAuthenticationToken(user, pass);
	    Authentication auth = authManager.authenticate(authReq);
	     
	    SecurityContext sc = SecurityContextHolder.getContext();
	    sc.setAuthentication(auth);
	    HttpSession session = req.getSession(true);
	    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	}
	*/
	
	private  File convert(MultipartFile file,String path) throws IOException
	{    	
			byte[] bytes = file.getBytes();
			LOGGER.info("Path where the file is going to be stored is:\n" +FileHandlerImpl.class
	                .getClassLoader().getResource("").getPath().toString());
	        File file2 = new File(FileHandlerImpl.class
	                .getClassLoader().getResource("").getPath().toString().replace("/", "\\")+ "xls\\"
	                +"");
	     

	        try {
	            OutputStream os = new FileOutputStream(file2);
	            os.write(bytes);
	            LOGGER.info("Write bytes to file.");
	            os.close();
	        } catch (Exception e) {
	            LOGGER.severe("File was not written successfully :( "+e);
	        }
			return file2;
		}
	    
	
	
	public UserXML convertUserToUserXML(User user) {
		UserXML userxml = new UserXML(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
		return userxml;
	}
	
	
}
