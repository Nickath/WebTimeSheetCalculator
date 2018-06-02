package org.nick.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "email_subscription")
public class EmailSubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "subscription_date")
	private Date subscription_date;
	//FK to user table
	@OneToOne()
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getSubscription_date() {
		return subscription_date;
	}
	public void setSubscription_date(Date subscription_date) {
		this.subscription_date = subscription_date;
	}
	
	public EmailSubscription(User user, Date date) {
		this.user = user; 
		this.subscription_date = date;
	}
	//default constructor for the JPA
	public EmailSubscription() {
		
	}
	
}
