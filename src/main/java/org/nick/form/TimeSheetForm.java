package org.nick.form;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.nick.model.Month;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class TimeSheetForm {
     
 
	private  CommonsMultipartFile file;
	
	private List<CommonsMultipartFile> fileList;
	
    @NotNull(message = "Please enter pending days left ")
    @Min(value=1)
    @Max(value=28)
	private  int pendingDays;
    @NotEmpty(message = "Please enter a valid desired mean")
    @Length( message="Invalid format (f.e 9:15)", min = 4, max = 5)
    @Pattern(regexp ="^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
	private  String desiredMean;
    
    private boolean checked;
    
    private List<Month> months;
    
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

	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Month> getMonths() {
		return months;
	}
	public void setMonths(List<Month> months) {
		this.months = months;
	}
	public List<CommonsMultipartFile> getFileList() {
		return fileList;
	}
	public void setFileList(List<CommonsMultipartFile> fileList) {
		this.fileList = fileList;
	}
	@Override
	public String toString() {
		return "TimeSheetForm [file path is =" + file.getOriginalFilename() + ", pendingDays=" + pendingDays + ", desiredMean=" + desiredMean + ", checked= " +checked+"  ]";
	}
	
	
}
