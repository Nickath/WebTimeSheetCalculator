package org.nick.service;

import org.nick.form.TimeSheetForm;

public interface FormService {

	public TimeSheetForm saveFrom(TimeSheetForm timesheetForm);
	public TimeSheetForm updateForm(TimeSheetForm timesheetForm);
}
