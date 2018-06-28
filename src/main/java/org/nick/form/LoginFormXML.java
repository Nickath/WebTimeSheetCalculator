package org.nick.form;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginFormXML {

	private Long id;
	@NotEmpty(message = "Please enter username")
	private String username;
	@NotEmpty(message = "Please enter a password")
	private String password;
	
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
	
	public LoginFormXML(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public LoginFormXML() {
		// TODO Auto-generated constructor stub
	}
}
