package org.nick.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class TimeSheetForm {
     
 
	private  CommonsMultipartFile file;
	
    @NotNull(message = "Please enter pending days left ")
    @Min(value=1)
    @Max(value=28)
	private  int pendingDays;
    @NotEmpty(message = "Please enter desired mean")
	private  String desiredMean;
    
    private String check;
	
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
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	@Override
	public String toString() {
		return "TimeSheetForm [file path is =" + file.getOriginalFilename() + ", pendingDays=" + pendingDays + ", desiredMean=" + desiredMean + "]";
	}
	
	
}
