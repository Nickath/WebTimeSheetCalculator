package org.nick.form;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class RegisterForm {

	@NotEmpty(message = "Please enter username")
	private String username;
	
	@NotEmpty(message = "Please enter password")
	private String password;
	
	@Email(message = "Not valid email")
	@NotEmpty(message = "Please enter email")
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RegisterForm(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public RegisterForm() {
		
	}
}
