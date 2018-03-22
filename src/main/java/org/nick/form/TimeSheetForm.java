package org.nick.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class TimeSheetForm {
     
 
	private  CommonsMultipartFile file;
	
    @NotNull(message = "Please enter pending days left ")
	private  int pendingDays;
    @NotEmpty(message = "Please enter desired mean")
	private  String desiredMean;
	
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public int getPendingDays() {
		return pendingDays;
	}
	public void setPendingDays(int pendingDays) {
		this.pendingDays = pendingDays;
	}
	public String getDesiredMean() {
		return desiredMean;
	}
	public void setDesiredMean(String desiredMean) {
		this.desiredMean = desiredMean;
	}
	@Override
	public String toString() {
		return "TimeSheetForm [file path is =" + file.getOriginalFilename() + ", pendingDays=" + pendingDays + ", desiredMean=" + desiredMean + "]";
	}
	
	
}
