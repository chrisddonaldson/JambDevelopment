package com.development;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class dateCalcs {

	static long getDayDiff(Date date) {
		//System.out.println(date);
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		//System.out.println(cal.getTime());
		Calendar today = Calendar.getInstance();
		long diff = today.getTimeInMillis() - cal.getTimeInMillis(); //result in millis
		long days = diff / (24 * 60 * 60 * 1000);
		return days;
}}
