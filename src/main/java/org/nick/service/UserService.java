package org.nick.service;

import org.nick.model.TimeSheet;

public interface UserService {

	public void updateTimeSheet(TimeSheet timesheet);
	
	public void insertTimeSheet(TimeSheet timesheet);
}
