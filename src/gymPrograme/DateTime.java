package gymPrograme;

import java.util.Calendar;
import java.util.Date;

// this class contain all info about date
public class DateTime {

	private int day, month, year ;
	private Calendar paymentDate;
	private Calendar registeredDate = Calendar.getInstance();
	private boolean needsToPay = false;

	public DateTime(int year, int month, int day) {
		super();
		this.registeredDate.set(Calendar.DATE, day);
		this.registeredDate.set(Calendar.MONTH, month);
		this.registeredDate.set(Calendar.YEAR, year);
	}

	public Calendar getRegisteredDate() {
		return registeredDate;
	}

	public void setNeedsToPay(boolean needsToPay) {
		this.needsToPay = needsToPay;
	}

	
	public String getRegiterationDate() {
		String str =  getDay() + "/" + getMonth()+ "/" + getYear();
		return str;
	}

	//this getter for regestiration day
	public int getDay() {
		return registeredDate.get(Calendar.DATE);
	}

	public int getMonth() {
		return registeredDate.get(Calendar.MONTH);
	}

	public int getYear() {
		return registeredDate.get(Calendar.YEAR);
	}


}
