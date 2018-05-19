package org.nick.service;

import java.io.File;
import java.io.IOException;

import org.nick.form.TimeSheetForm;
import org.nick.model.TimeSheet;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface FileHandler {


    File readFile(String path, CommonsMultipartFile file, TimeSheet timesheet);

	TimeSheet makeCalculations(File myFile, TimeSheetForm timesheetform, TimeSheet timesheet) throws IOException;

	TimeSheet makeCalculations(File myFile, TimeSheet timesheet) throws IOException;
}
