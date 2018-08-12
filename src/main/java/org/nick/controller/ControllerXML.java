package org.nick.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.nick.model.User;
import org.nick.model.UserXML;
import org.nick.repository.UserRepository;
import org.nick.service.UserService;
import org.nick.service.impl.FileHandlerImpl;
import org.nick.web.contants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.SAXException;

@Controller
public class ControllerXML {
	
	private static final Logger LOGGER = Logger.getLogger(ControllerXML.class.getName());
	
	@Autowired 
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	/*@Autowired
	AuthenticationManager authManager;*/
	
	
	
	//go to download xml page
    @RequestMapping(value = "/downloadXMLPage", method = RequestMethod.GET)
    public String getDownloadXMLPage(Model model) {
    	User user = userService.getAuthenticatedUser();
    	model.addAttribute("user",user);
    	String photo  = userService.getUserImageBase64(user);
		model.addAttribute("photoProfil",photo);
    	return "downloadXmlPage";
    }
    
    //download xml profil
    @RequestMapping(value = "/downloadXMLAttempt", method = RequestMethod.GET, headers = "Accept=application/json")
    public void downloadXmlAttempt(HttpServletResponse response) {
    	User user = userService.getAuthenticatedUser();
    	File file = new File(Constants.DOWNLOAD_XML_PROFIL_PATH);
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

	//register xml page
	@RequestMapping(value = "/registerXMLPage", method = RequestMethod.GET)
	public String getRegisterPageXML(Model model) {
		return "registerPageXML";
	}
	
	//register xml action
	@RequestMapping(value = "/registerXMLAttempt", method = RequestMethod.POST)
	public String registerXMLAttempt(@RequestParam("file") CommonsMultipartFile file,
			HttpSession session,  ModelMap model ) throws IOException {
		   
		   String path=session.getServletContext().getRealPath("/"); 
		   try {

			File file2 = convert(file, path);
			JAXBContext jaxbContext = JAXBContext.newInstance(UserXML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			UserXML user = (UserXML) jaxbUnmarshaller.unmarshal(file2);
            User normalUser = new User(user);
            boolean alreadyExists = userService.registerUserObj(normalUser);
    		
    		if(alreadyExists) {
    		    model.addAttribute("error","Username or email already exists");
    		}
    		else {
    			model.addAttribute("success","Successfully registered, the email has been sent to "+user.getEmail());
    		}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		    return "registerPageXML";
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
			HttpSession session,  ModelMap model, HttpServletRequest request) throws IllegalStateException, IOException {

		   String path=session.getServletContext().getRealPath("/"); 
		   try {
			File file2 = convert(file, path);
			boolean isvalidXML = isValidXML(file2);
			if(isvalidXML) {
			  JAXBContext jaxbContext = JAXBContext.newInstance(UserXML.class);
			  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			  UserXML user = (UserXML) jaxbUnmarshaller.unmarshal(file2);
              //after unmarshalling the user object, check if the credentials of the user exist in the database
              User normalUser = userService.customUserlXMLAuthentication(user.getUserName(), user.getPassword());
              // if(normalUser != null) {
              //create usernamepasswordauthenticationtoken
              UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
              //set authentication for the user
              SecurityContextHolder.getContext().setAuthentication(authRequest);
              User userauth = userService.getAuthenticatedUser();
              model.addAttribute("user", userauth);
      		  model.addAttribute("user",normalUser);
              return "redirect:/homePage";
			}
			else {
				model.addAttribute("error","Not valid XML submitted");
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		   return "loginXmlPage";
		
	
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
		//UserXML userxml = new UserXML(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
		UserXML userxml = new UserXML(user);
		return userxml;
	}
	
	public boolean isValidXML(File xmlfile) throws FileNotFoundException {
		File fileXSD = new File(Constants.XSD_PATH);
		FileInputStream xsd = null;
		xsd = new FileInputStream(fileXSD);
		try {
			InputStream xml = new FileInputStream(xmlfile);
			SchemaFactory factory =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		    try {
				Schema schema = factory.newSchema(new StreamSource(xsd));
				Validator validator = schema.newValidator();
		        try {
					validator.validate(new StreamSource(xml));
					return true;
				} catch (IOException e) {
					LOGGER.warning("Could not open xsd file");
				}
			} catch (SAXException e) {
				e.printStackTrace();
				LOGGER.warning("Not valid XML file");
				return false;
			}
		           
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
		
	}
	
	
}
