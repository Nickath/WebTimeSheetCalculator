package org.nick.model;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement specifies the root element for the XML document.
@XmlRootElement
public class UserXML {

	private Long id;
	private String username;
    private String password;
    private String email;
    private boolean enabled;
    private byte[] photo;
    private Role role;
    
    //@XmlAttribute specifies the attribute for the root element.
    @XmlAttribute
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	//@XmlElement specifies the sub-element for the root element.
	@XmlElement
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	@XmlElement
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@XmlElement
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
	@XmlElement
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@XmlElement
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	@XmlElement
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public UserXML(Long id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email    = email;
	}
	
	public UserXML(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email    = user.getEmail();
		this.enabled  = user.isEnabled();
		this.photo    = user.getPhoto();
		this.role     = user.getRole();
	}
	
	public UserXML() {
		
	}
	public UserXML(Long id, String username, String password, String email, boolean enabled, byte[] photo, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.photo = photo;
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserXML [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", enabled=" + enabled + ", photo=" + Arrays.toString(photo) + ", role=" + role + "]";
	}
	
	
    
    
   

}
