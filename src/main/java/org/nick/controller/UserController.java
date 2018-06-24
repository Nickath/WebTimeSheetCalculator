package org.nick.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.nick.email.ChangePasswordRequest;
import org.nick.form.MonthForm;
import org.nick.model.EmailSubscription;
import org.nick.model.Month;
import org.nick.model.TimeSheet;
import org.nick.model.User;
import org.nick.pdf.templates.PdfTemplates;
import org.nick.repository.MonthRepository;
import org.nick.repository.UserRepository;
import org.nick.service.UserService;
import org.nick.service.impl.FileHandlerImpl;
import org.nick.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;


@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MonthRepository monthRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
    FileHandlerImpl fileHandlerImpl;
	
	@Autowired
	ChangePasswordRequest changePasswordService;

	
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
	 
	 
	 //forgot password page
	 @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	     public String forgotPassword(Model model) {
		    User user = userService.getAuthenticatedUser();
		    if(user!=null) {
		       model.addAttribute("user",user);
		       return "redirect:/homePage";
		    }
		    else {
		       return "forgotPassword";
		    }
	 }
	 
	 @RequestMapping(value = "/changePassRequest", method = RequestMethod.POST)
	      public String changePassRequest(@RequestParam String email, Model model) {
		    User user = userService.getAuthenticatedUser();
		    if(user!=null) {
		    	model.addAttribute("user",user);
			    return "redirect:/homePage";
		    }
		    else {
		    	if(userService.mailExists(email)) {
		    		String changePassId = java.util.UUID.randomUUID().toString();
		    		User userByEmail = userService.findByEmail(email);
		    		userService.writeChangePassRequestInDB(userByEmail, changePassId);
		    		changePasswordService.sendChangePasswordMail(email, changePassId);
		    		model.addAttribute("success","An email for password change has been sent into your account");
		    	    
		    	}
		    	else {
		    		model.addAttribute("notexistingmail","No registered user with email: "+email+" was found");
		    	    
		    	}
		    }
		    
		    return "forgotPassword";
		    
	 }
	 
	 
	 @RequestMapping(value = "/changePassAttempt", method = RequestMethod.POST)
	 public String changePassAttempt(@RequestParam("password") String password,
			 @RequestParam("passwordconfirm") String passwordconfirm,
			 Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		 
		        if(!password.equals(passwordconfirm) || password == null || password == "") {
		        	model.addAttribute("passworderror","Password should be same with password confirm and not null");
		        }
		        else {
                   String id = (String) session.getAttribute("changeID");
                   if(id.equals("") || id == null)
                     {
                	  model.addAttribute("error","You have not asked for a password change");
                     }
                   else
                     {
                     userService.changePasswordUsingCRid(id, password);
		             model.addAttribute("success","Your password has been successfully updated");
                     }
		        }
	        	return "changePasswordPage";
		 
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
	    		if(!password.equals("") && !password.equals(null) && !passwordconfirm.equals("") && !passwordconfirm.equals(null)) {
	    		if(password.equals(passwordconfirm)) {
	    			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //encrypt the password using BCryptPasswordEncoder
					String hashedPassword = passwordEncoder.encode(password);
	    			userService.activateUser(true, username, hashedPassword,"");
	    			model.addAttribute("success","Account was successfully enabled Mr/Mrs "+username+", "
	    					+ "click <a href=\"/WebTimeSheetCalculator/loginPage\">here</a> to login");
	    		}else {
	    			model.addAttribute("passworderror", "Password should be the same with the confirmation");
	    		}
	    	  }
	    		else {
	    			model.addAttribute("passworderror", "Password && password confirmation should be the same and not empty");
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
	    
	    
	    @RequestMapping(value = "/downloadPdfAjax", method = RequestMethod.GET, headers = "Accept=application/json")
	    public  @ResponseBody void downloadPdfAjax(HttpServletRequest req, HttpServletResponse response, @RequestParam("form") String form) {
	    	Pdf pdf = new Pdf();

	    	
	    	String html = PdfTemplates.startingHtmlTag + PdfTemplates.statisticsHeader+ PdfTemplates.startingCssTag 
	    			+ PdfTemplates.readMainCss(req,response) + PdfTemplates.endingCssTag + form + PdfTemplates.endingHtmlTag;
	    			
            pdf.addPageFromString(html);

	    	// Save the PDF
	    	try {
			  File file = pdf.saveAs(UserController.class
	                    .getClassLoader().getResource("").getPath().toString().replace("/", "\\")+ "statistics\\"+
	                    "statistics.pdf");
			  
			  userService.createPDF(file, response);
			  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    
	    @RequestMapping(value = "/downloadPdf", method = RequestMethod.POST)
	    public void downloadPdf(HttpServletRequest req, HttpServletResponse response) {
	    	/*, 
    		@RequestParam(value = "hiddenform") String hiddenform*/
	    	String hiddenform = req.getParameter("hiddenform");
	    	Pdf pdf = new Pdf();

	    	String html = PdfTemplates.startingHtmlTag + PdfTemplates.statisticsHeader+ PdfTemplates.startingCssTag 
	    			+ PdfTemplates.readMainCss(req,response) + PdfTemplates.endingCssTag + hiddenform + PdfTemplates.endingHtmlTag;
	    			
            pdf.addPageFromString(html);

	    	// Save the PDF
	    	try {
			  File file = pdf.saveAs(UserController.class
	                    .getClassLoader().getResource("").getPath().toString().replace("/", "\\")+ "statistics\\"+
	                    "statistics.pdf");
			  
			  userService.createPDF(file, response);
			  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
	    
	    
	   
	 
	
}
	 
