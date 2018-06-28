package org.nick.model;

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
	public UserXML(Long id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email    = email;
	}
	
	public UserXML() {
		
	}
	@Override
	public String toString() {
		return "UserXML [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
	
    
    
   

}
