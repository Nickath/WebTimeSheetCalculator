package org.nick.model;

import java.io.File;

public class TimeSheet {

	private File file;
	private String desiredMean;
	private int daysPending;
	private String restAverage;
	private String insertMean;
	private String exitMean;
	private String mean;
	private int workingDays;
	
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
