package com.happycar.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDD = "yyyy-MM-dd";
	
	public static String formatTime(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(time);
	}	
	
	public static String formatTime(Date time,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(time);
	}
	
	public static Date parseTime(String time,String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(time);
	}
	
	public static Date[] getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date[] dates = new Date[2];
		dates[0] = cal.getTime();
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		dates[1] = cal.getTime();
		return dates;
	}
	
	public static Date[] getHistoryReport() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.add(Calendar.DAY_OF_MONTH, -4);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date[] dates = new Date[2];
		dates[0] = cal.getTime();
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		dates[1] = cal.getTime();
		return dates;
	}
	
	public static Date[] getLastWeek() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			cal1.add(Calendar.WEEK_OF_MONTH, -2);
		} else{
			cal1.add(Calendar.WEEK_OF_MONTH, -1);
		}
		return new Date[]{cal1.getTime(), cal2.getTime()};
	}
	
	public static Date[] getCurWeek() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			cal1.add(Calendar.WEEK_OF_MONTH, -1);
		} else{
			cal2.add(Calendar.WEEK_OF_MONTH, 1);
		}
		return new Date[]{cal1.getTime(), cal2.getTime()};
	}
	
	public static Date[] getLastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date[] dates = new Date[2];
		dates[0] = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		dates[1] = cal.getTime();
		return dates;
	}
	
	public static Date[] getCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date[] dates = new Date[2];
		dates[0] = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		dates[1] = cal.getTime();
		return dates;
	}
	
	public static Date[] getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date[] dates = new Date[2];
		dates[0] = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		dates[1] = cal.getTime();
		return dates;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <= 78; i++) {
			System.out.println("ALTER TABLE g_odds_lhc_default modify column h"+i+" decimal(10,3);");
		}
		
	}
}
