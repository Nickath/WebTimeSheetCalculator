package org.nick.service;

import java.io.File;
import java.io.IOException;

import org.nick.form.TimeSheetForm;
import org.nick.model.TimeSheet;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface FileHandler {

	public File readFile(String path,CommonsMultipartFile file);
	
	public TimeSheet makeCalculations(File myFile,TimeSheetForm timesheetform) throws IOException;

	TimeSheet makeCalculations(File myFile) throws IOException;
}
