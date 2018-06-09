package org.nick.scheduler.jobs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.nick.scheduler.services.JobsService;
import org.nick.web.contants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


public class EmailJob  {
	
	@Autowired
	JobsService jobsService;

	//At 00:00:00am every day
	@Scheduled(cron="0 0 0 ? * *")
	public void execute() {
		
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("mail_scheduler.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		


		     
		     //set the password authenticator object of javax.mail
		     PasswordAuthentication pa = new PasswordAuthentication(Constants.username,Constants.password);
			 
		     // get below mean users emails
		     HashMap<String, String> recipients = jobsService.getBelowBaseUsersMail();
		     
		      Set set = recipients.entrySet();
		      Iterator iterator = set.iterator();
		      

		     //send emails to users
		     
	while(iterator.hasNext()) {
		Map.Entry mapentry = (Map.Entry)iterator.next();
		     try{
		     Session session = Session.getDefaultInstance(prop, 
		                          new Authenticator(){
		                             protected PasswordAuthentication getPasswordAuthentication() {
		                                return pa;
		                             }});

		   // -- Create a new message --
		     Message msg = new MimeMessage(session);

  
		  // -- Set the FROM and TO fields --
		     msg.setFrom(new InternetAddress(Constants.username));
		     msg.setRecipients(Message.RecipientType.TO, 
		                      InternetAddress.parse((String)mapentry.getKey(),false));
		     msg.setSubject("Below average mean warning");
		     msg.setText("Be careful, your current mean is: " +mapentry.getValue());
		     msg.setSentDate(new Date());
		     Transport.send(msg);
		     System.out.println("Message sent.");
		     try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }catch (MessagingException e){ System.out.println("Error, message was not send correctly" + e);}
		     
		 }

	}

}
