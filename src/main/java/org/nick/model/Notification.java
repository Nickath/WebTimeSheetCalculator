package org.nick.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.ehcache.search.expression.Not;
import org.nick.enums.NotificationType;


@Entity
@Table(name="notification")
public class Notification implements Comparable<Notification> {

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
		@ManyToOne(fetch = FetchType.EAGER) // many Notifications, may have as a referrer one user
		@JoinColumn(name = "referreruser_id", nullable = false)
		private User referreruser;
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "assigneduser_id", nullable = false)
		private User assignedUser;
		
		@Enumerated(EnumType.STRING)
	    @Column(length = 20)
	    private NotificationType notificationType;
		
		@Column(name = "shown")
		private boolean shown;

		@Column(name = "fromDate")
		private Date fromDate;
		@Column(name = "toDate")
		private Date toDate;

		public Date getFromDate() {
				return fromDate;
			}

		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}

		public Date getToDate() {
			return toDate;
		}

		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}





		
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
			return referreruser;
		}
		public void setReferreruser(User referreruser) {
			this.referreruser = referreruser;
		}
		public User getAssignedUser() {
			return assignedUser;
		}
		public void setAssignedUser(User assignedUser) {
			this.assignedUser = assignedUser;
		}
		
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public NotificationType getNotificationType() {
			return notificationType;
		}
		public void setNotificationType(NotificationType notificationType) {
			this.notificationType = notificationType;
		}
		public boolean isShown() {
			return shown;
		}
		public void setShown(boolean shown) {
			this.shown = shown;
		}
		public Notification() {
			
		}
		public Notification(Long id, String description, boolean isActive, User referreruser, User assignedUser) {
			super();
			this.id = id;
			this.description = description;
			this.isActive = isActive;
			this.referreruser = referreruser;
			this.assignedUser = assignedUser;
		}
		public Notification(Long id, String description, boolean isActive, Date date, User referreruser,
				User assignedUser) {
			super();
			this.id = id;
			this.description = description;
			this.isActive = isActive;
			this.date = date;
			this.referreruser = referreruser;
			this.assignedUser = assignedUser;
		}
		public Notification(User referrer, User recipient, boolean isActive, Date date) {
			super();
			this.referreruser = referrer;
			this.assignedUser = recipient;
			this.isActive = isActive;
			this.date = date;
		}


	public Notification(String description, Date date, User assignedUser, User referreruser, NotificationType notificationType, boolean shown, boolean isActive) {
		this.description = description;
		this.date = date;
		this.assignedUser = assignedUser;
		this.referreruser = referreruser;
		this.notificationType = notificationType;
		this.shown = shown;
		this.isActive = isActive;
	}

	public Notification(User referrer, User recipient, boolean isActive, Date date, String description) {
			super();
			this.referreruser = referrer;
			this.assignedUser = recipient;
			this.isActive = isActive;
			this.date = date;
			this.description = description;
		}
		public Notification(Long id, String description, boolean isActive, Date date, User referreruser,
				User assignedUser, NotificationType notificationType) {
			super();
			this.id = id;
			this.description = description;
			this.isActive = isActive;
			this.date = date;
			this.referreruser = referreruser;
			this.assignedUser = assignedUser;
			this.notificationType = notificationType;
		}
		public Notification(Long id, String description, boolean isActive, Date date, User referreruser,
				User assignedUser, NotificationType notificationType, boolean shown) {
			super();
			this.id = id;
			this.description = description;
			this.isActive = isActive;
			this.date = date;
			this.referreruser = referreruser;
			this.assignedUser = assignedUser;
			this.notificationType = notificationType;
			this.shown = shown;
		}


	@Override
	public int compareTo(Notification o) {
		return 0;
	}
}
