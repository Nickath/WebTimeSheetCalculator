package org.nick.form;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.nick.model.Month;

public class MonthForm {

	
		private  String month;
		private String monthId;
		
		private List<Month> months ;
		
		public MonthForm() {
			
		}
		
		public MonthForm(String month, String monthId) {
			super();
			this.month = month;
			this.monthId = monthId;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getMonthId() {
			return monthId;
		}
		public void setMonthId(String monthId) {
			this.monthId = monthId;
		}

		public List<Month> getMonths() {
			return months;
		}

		public void setMonths(List<Month> months) {
			this.months = months;
		}
}
