package org.nick.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
		this.username = userxml.getUserName();
		this.password = userxml.getPassword();
		this.id       = userxml.getId();
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
	@Override
	public int compareTo(User arg0) {
    long compareId = ((User) arg0).getId();
		
		//ascending order
		return (int) (this.id - compareId);
		
		//descending order
		//return compareQuantity - this.quantity;
	}
}
