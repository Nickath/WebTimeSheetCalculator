package org.nick.model;

import java.io.File;

public class TimeSheet {

	private File file;
	private double desiredMean;
	private int daysPending;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public double getDesiredMean() {
		return desiredMean;
	}
	public void setDesiredMean(double desiredMean) {
		this.desiredMean = desiredMean;
	}
	public int getDaysPending() {
		return daysPending;
	}
	public void setDaysPending(int daysPending) {
		this.daysPending = daysPending;
	}
	
	public TimeSheet(File file, double desiredMean, int daysPending) {
		super();
		this.file = file;
		this.desiredMean = desiredMean;
		this.daysPending = daysPending;
	}
	
 
     
}
