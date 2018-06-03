package org.nick.scheduler.jobs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.nick.scheduler.services.JobsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


public class EmailJob  {
	
	@Autowired
	JobsService jobsService;

	//At 00:00:00am every day
	@Scheduled(cron="0 0 0 ? * * *")
	public void execute() {
		
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("mail_scheduler.properties");
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		

		  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		     final String username = "<<mail>>";//
		     final String password = "<<pass>>";
		     
		     //set the password authenticator object of javax.mail
		     PasswordAuthentication pa = new PasswordAuthentication(username,password);
		     
			  // get below mean users emails
		     List<String> recipients = jobsService.getBelowBaseUsersMail();
		   
		     //send emails to users
		     
		 for(String recipient : recipients) {
		     try{
		     Session session = Session.getDefaultInstance(prop, 
		                          new Authenticator(){
		                             protected PasswordAuthentication getPasswordAuthentication() {
		                                return pa;
		                             }});

		   // -- Create a new message --
		     Message msg = new MimeMessage(session);

  
		  // -- Set the FROM and TO fields --
		     msg.setFrom(new InternetAddress("javaxmailtester@gmail.com"));
		     msg.setRecipients(Message.RecipientType.TO, 
		                      InternetAddress.parse(recipient,false));
		     msg.setSubject("You are late");
		     msg.setText("You are late");
		     msg.setSentDate(new Date());
		     Transport.send(msg);
		     System.out.println("Message sent.");
		  }catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
		     
		 }

	}

}
