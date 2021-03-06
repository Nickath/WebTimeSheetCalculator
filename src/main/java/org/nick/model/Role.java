package org.nick.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {

	@Id()
	private Long id;
	@Column(name="role")
	private String role;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public Role(Long id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	
	//default constructor
	public Role() {
		
	}
	public Role(long l) {
		this.id = l;
	}
}
