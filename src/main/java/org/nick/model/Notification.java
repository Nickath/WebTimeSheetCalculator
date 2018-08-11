package org.nick.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="notification")
public class Notification {

		@Id()
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		@Column(name="description")
		private String description;
		@Column(name="is_active")
		private boolean isActive;
		@Column(name = "date")
		private Date date;
		//FK to User table
		@ManyToOne
		private User Referreruser;
		@ManyToOne
		private User AssignedUser;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		public User getReferreruser() {
			return Referreruser;
		}
		public void setReferreruser(User referreruser) {
			Referreruser = referreruser;
		}
		public User getAssignedUser() {
			return AssignedUser;
		}
		public void setAssignedUser(User assignedUser) {
			AssignedUser = assignedUser;
		}
		
		public Notification() {
			
		}
		public Notification(Long id, String description, boolean isActive, User referreruser, User assignedUser) {
			super();
			this.id = id;
			this.description = description;
			this.isActive = isActive;
			Referreruser = referreruser;
			AssignedUser = assignedUser;
		}
		public Notification(Long id, String description, boolean isActive, Date date, User referreruser,
				User assignedUser) {
			super();
			this.id = id;
			this.description = description;
			this.isActive = isActive;
			this.date = date;
			Referreruser = referreruser;
			AssignedUser = assignedUser;
		}
		public Notification(User referrer, User recipient, boolean isActive, Date date) {
			super();
			this.Referreruser = referrer;
			this.AssignedUser = recipient;
			this.isActive = isActive;
			this.date = date;
		}
		
		
		public Notification(User referrer, User recipient, boolean isActive, Date date, String description) {
			super();
			this.Referreruser = referrer;
			this.AssignedUser = recipient;
			this.isActive = isActive;
			this.date = date;
			this.description = description;
		}
		
		
		

        
}
