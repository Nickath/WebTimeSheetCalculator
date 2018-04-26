package org.nick.service.impl;

import org.nick.model.TimeSheet;
import org.nick.repository.TimeSheetRepository;
import org.nick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

	@Autowired
    TimeSheetRepository repository;



	@Override
	public void updateTimeSheet(TimeSheet timesheet) {
		
		repository.updateTimeSheetByID(timesheet.getDesiredMean(), timesheet.getRestAverage(),
				timesheet.getMonth().getId(), timesheet.getUser().getId());
		
	}



	@Override
	public void insertTimeSheet(TimeSheet timesheet) {
		repository.save(timesheet);
	}
}
