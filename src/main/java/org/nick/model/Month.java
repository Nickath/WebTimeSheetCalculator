package org.nick.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="month_table")
public class Month {

	@Id()
	private Long id;
	@Column(name="month")
	private String month;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Month(Long id, String month) {
		super();
		this.id = id;
		this.month = month;
	}
	
	public Month() {
		
	}
	
	public Month(Long id) {
		this.id  = id;
	}
}
