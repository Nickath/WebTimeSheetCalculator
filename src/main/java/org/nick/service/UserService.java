package org.nick.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.nick.form.RegisterForm;
import org.nick.model.TimeSheet;
import org.nick.model.User;

public interface UserService {

	public void updateTimeSheet(TimeSheet timesheet);
	
	public void insertTimeSheet(TimeSheet timesheet);
	
	public boolean registerUser(RegisterForm form);
	
	public boolean insertOtUpdateTimeSheet(TimeSheet timesheet);
	
	public User getAuthenticatedUser();

	public Date getCurrentTime();

	Date getLastUpdateOfTimesheetForMonth(User user, long month);
	
	public List<TimeSheet> getStatisticsByUserId(long userID);
}
