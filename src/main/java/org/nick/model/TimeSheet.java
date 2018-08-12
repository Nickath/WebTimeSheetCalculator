package org.nick.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TimeSheets")
public class TimeSheet {
    

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="file")
	private File file;
	@Column(name="desiredMean")
	private String desiredMean;
	@Column(name="dayspending")
	private int daysPending;
	@Column(name="restmean")
	private String restAverage;
	@Column(name="average_coming")
	private String insertMean;
	@Column(name="average_leaving")
	private String exitMean;
	@Column(name="average")
	private String mean;
	@Column(name="days")
	private int workingDays;
	@Column(name="last_update")
	private Date lastUpdate;
	
	//FK to month table
	@ManyToOne
	private Month month;
	
	//FK to User table, many timesheets may belong to one user
	@ManyToOne
	private User user;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getDesiredMean() {
		return desiredMean;
	}
	public void setDesiredMean(String desiredMean) {
		this.desiredMean = desiredMean;
	}
	public int getDaysPending() {
		return daysPending;
	}
	public void setDaysPending(int daysPending) {
		this.daysPending = daysPending;
	}
	
	public String getRestAverage() {
		return restAverage;
	}
	public void setRestAverage(String restAverage) {
		this.restAverage = restAverage;
	}
	public String getInsertMean() {
		return insertMean;
	}
	public void setInsertMean(String insertMean) {
		this.insertMean = insertMean;
	}
	public String getExitMean() {
		return exitMean;
	}
	public void setExitMean(String exitMean) {
		this.exitMean = exitMean;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public int getWorkingDays() {
		return workingDays;
	}
	public void setWorkingDays(int workingDays) {
		this.workingDays = workingDays;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date localDateTime) {
		this.lastUpdate = localDateTime;
	}
	public TimeSheet(File file, String desiredMean, int daysPending, String restAverage, String insertMean,
			String exitMean, String mean,int workingDays) {
		super();
		this.file = file;
		this.desiredMean = desiredMean;
		this.daysPending = daysPending;
		this.restAverage = restAverage;
		this.insertMean = insertMean;
		this.exitMean = exitMean;
		this.mean = mean;
		this.workingDays = workingDays;
	}
	public TimeSheet() {
		// TODO Auto-generated constructor stub
	}
	
	
	
 
     
}
