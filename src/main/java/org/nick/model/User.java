package org.nick.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

//in case of Gson you can do the other way around marking those field you do want to
//be included in json with @Expose and creating the gson object with:


@Entity
@Table(name="users")
public class User implements Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Long id;

	
	@Column(name="username" , unique=true)
	@Expose
	private String username;
	
	@Column(name="email", unique=true)
	@Expose
	private String email;

	@Column(name="password")
	@Expose
	private String password;
	
	@Column(name="enabled")
	@Expose
	private boolean enabled;
	
	/*@Lob*/
	@Column(name="photo")
	@Expose
	private byte[]  photo;
	
	@Column(name="confirmid")
	@Expose
	private String confirmId;
	
	@Column(name="changepass_request_ID")
	@Expose
	private String changePassRequestID;
	
	//FK TO TABLE role, each user has his/her own role
	@ManyToOne
	private Role role;
	
	//one user has many timesheets (list), while a timesheet belongs explicitly to one user
	@JsonIgnore//json ignore to avoid exception when converting to JSON because of circular reference and to aboid declare the objects transient
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private  List<TimeSheet> timesheets;
	//one user may is the referrer of many notifications, but a notification has only one referrer user
	//we declare the below sets as transient to avoid stackoverflow in the GSON convert because of the circular reference
	//transient declares that these variables should not be serialized
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "referreruser", cascade = CascadeType.ALL)
	private  Set<Notification> notificationsReferrer;
	
	//one user may is assigned with many notifications, but a notification has only one user assigned to
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "assignedUser", cascade = CascadeType.ALL)
	private  Set<Notification> notificationsAssigned;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public User(UserXML userxml) {
		this.id       = userxml.getId();
		this.username = userxml.getUserName();
		this.email    = userxml.getEmail();
		this.password = userxml.getPassword();
		this.enabled  = userxml.isEnabled();
		this.photo    = userxml.getPhoto();
		this.role     = userxml.getRole();
		
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(Long id) {
		this.id = id;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getConfirmId() {
		return confirmId;
	}
	public void setConfirmId(String confirmId) {
		this.confirmId = confirmId;
	}
	public String getChangePassRequestID() {
		return changePassRequestID;
	}
	public void setChangePassRequestID(String changePassRequestID) {
		this.changePassRequestID = changePassRequestID;
	}
	public List<TimeSheet> getTimesheets() {
		return timesheets;
	}
	public void setTimesheets(List<TimeSheet> timesheets) {
		this.timesheets = timesheets;
	}
	public Set<Notification> getNotificationsReferrer() {
		return notificationsReferrer;
	}
	public void setNotificationsReferrer(Set<Notification> notificationsReferrer) {
		this.notificationsReferrer = notificationsReferrer;
	}
	public Set<Notification> getNotificationsAssigned() {
		return notificationsAssigned;
	}
	public void setNotificationsAssigned(Set<Notification> notificationsAssigned) {
		this.notificationsAssigned = notificationsAssigned;
	}

	@Override
	public int compareTo(User arg0) {
    long compareId = ((User) arg0).getId();
		
		//ascending order
		return (int) (this.id - compareId);
		
		//descending order
		//return compareQuantity - this.quantity;
	}
	
	public boolean match(String name, String password) {
            return this.username.equals(name) && this.password.equals(password);
        }
	public User(Long id, String username, String email, String password, boolean enabled, byte[] photo,
			String confirmId, String changePassRequestID, Role role, List<TimeSheet> timesheets,
			Set<Notification> notificationsReferrer, Set<Notification> notificationsAssigned) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.photo = photo;
		this.confirmId = confirmId;
		this.changePassRequestID = changePassRequestID;
		this.role = role;
		this.timesheets = timesheets;
		this.notificationsReferrer = notificationsReferrer;
		this.notificationsAssigned = notificationsAssigned;
	}


}
