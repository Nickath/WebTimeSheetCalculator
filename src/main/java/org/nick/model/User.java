package org.nick.model;

import java.util.List;

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

@Entity
@Table(name="users")
public class User implements Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@Column(name="username" , unique=true)
	private String username;
	
	@Column(name="email", unique=true)
	private String email;

	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	/*@Lob*/
	@Column(name="photo")
	private byte[]  photo;
	
	@Column(name="confirmid")
	private String confirmId;
	
	@Column(name="changepass_request_ID")
	private String changePassRequestID;
	
	//FK TO TABLE role, each user has his/her own role
	@ManyToOne
	private Role role;
	
	//one user has many timesheets (list), while a timesheet belongs explicitly to one user
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	List<TimeSheet> timesheets;
	
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
			String confirmId, String changePassRequestID, Role role, List<TimeSheet> timesheets) {
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
	}
}
