package org.nick.form;

import java.util.ArrayList;


import org.nick.model.TimeSheet;

public class TimeSheetWrapper {

	private ArrayList<TimeSheet> timesheets;

	public ArrayList<TimeSheet> getTimeSheets() {
	    return timesheets;
	}

	public void setTimeSheets(ArrayList<TimeSheet> timesheets) {
	    this.timesheets= timesheets;
	}
}
