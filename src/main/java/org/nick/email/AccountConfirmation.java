package org.nick.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import org.nick.email.templates.AccountVerificationTemplate;
import org.nick.web.contants.Constants;
import org.springframework.stereotype.Service;

@Service("accountconfirmationService")
public class AccountConfirmation {

	public void sendConfirmationMail(String recipient, String confirmationId) {
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
		                      InternetAddress.parse(recipient,false));
		     msg.setSubject(AccountVerificationTemplate.header);
		     msg.setText(AccountVerificationTemplate.start
		     		+ AccountVerificationTemplate.prefixUrl +confirmationId +AccountVerificationTemplate.end);
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
