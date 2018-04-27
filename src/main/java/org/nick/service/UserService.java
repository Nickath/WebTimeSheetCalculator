package org.nick.service;

import org.nick.form.RegisterForm;
import org.nick.model.TimeSheet;

public interface UserService {

	public void updateTimeSheet(TimeSheet timesheet);
	
	public void insertTimeSheet(TimeSheet timesheet);
	
	public boolean registerUser(RegisterForm form);
	
	public boolean insertOtUpdateTimeSheet(TimeSheet timesheet);
}
