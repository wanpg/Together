package com.wanpg.together.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtil {
	private static final SimpleDateFormat mdDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
	private static final SimpleDateFormat hmDateFormat = new SimpleDateFormat("hh:mm");
	
	public static final Locale LOCAL_SOFT = Locale.CHINA;
	
	public static String parse(long time) {
		Date curDate = new Date();
		Date sysDate = new Date(time * 1000);
		String result;
		if (curDate.before(sysDate)) {
			result = mdDateFormat.format(sysDate);
		} else {
			int intervalDay = compareDay(curDate, sysDate);
			if (intervalDay == 0) {
				result = convertHour(sysDate);
			} else if (intervalDay == 1) {
				result = "昨天" + convertHour(sysDate);
			} else if (intervalDay == 2) {
				result = convertWeek(sysDate) + convertHour(sysDate);
			} else {
				result = mdDateFormat.format(sysDate);
			}
		}
		return result;
	}
	
	private static int compareDay(Date d1, Date d2) {
		Calendar curCalendar = Calendar.getInstance();
		Calendar sysCalendar = Calendar.getInstance();
		curCalendar.setTime(d1);
		sysCalendar.setTime(d2);
		int day1 = curCalendar.get(Calendar.DAY_OF_YEAR);
		int day2 = sysCalendar.get(Calendar.DAY_OF_YEAR);
		return day1 - day2;
	}

	private static String convertHour(Date date) {
		int hours = date.getHours();
		String result;
		if (hours < 12) {
			result = "早上 ";
		} else if (hours < 14) {
			result = "中午 ";
		} else if (hours < 18) {
			result = "下午 ";
		} else {
			result = "晚上 ";
		}
		result += hmDateFormat.format(date);
		return result;
	}
	
	private static String convertWeek(Date date) {
		int day = date.getDay();
		String result;
		switch(day) {
		case 0: result = "周日";break;
		case 1: result = "周一";break;
		case 2: result = "周二";break;
		case 3: result = "周三";break;
		case 4: result = "周四";break;
		case 5: result = "周五";break;
		case 6: result = "周六";break;
		default: result = "";break;
		}
		return result;
	}
	
	
	private static SimpleDateFormat format1 = new SimpleDateFormat("aaa hh:mm", LOCAL_SOFT);
	private static SimpleDateFormat format1_1 = new SimpleDateFormat("HH:mm", LOCAL_SOFT);
	private static SimpleDateFormat format2 = new SimpleDateFormat("EEEE HH:mm", LOCAL_SOFT);
	private static SimpleDateFormat format3 = new SimpleDateFormat("M月d日 HH:mm", LOCAL_SOFT);
	
	private static long curDayStart = 0L;
	private static long curWeekStart = 0L;
	private static Calendar currentDate;
	/**
	 * 根据传入的时间格式化成为三种格式
	 * @param time
	 * @param is24 --是否是24小时制
	 * @return 当天的     下午 10:23 or 22:23<br>
	 * 		          本周的     星期一 10:23<br>
	 * 		          本年的    7月28日 10:23
	 */
	public static String formatDateFromLong(long time, boolean is24){
		long curTime = System.currentTimeMillis();
		if(curTime-curDayStart>24*60*60*1000 || curTime < curDayStart)
			curDayStart = getDayStartTime(curTime);
		if(curTime-curWeekStart>7*24*60*60*1000 || curTime < curWeekStart)
			curWeekStart = getWeekStartTime(curTime);
		Date date = new Date(time);
		if(time>=curDayStart){
			if(is24)
				return format1_1.format(date);
			else
				return format1.format(date);
		}else if(time>=curWeekStart && time<curDayStart){
			return format2.format(date);
		}else{
			return format3.format(date);
		}
	}
	
	/**
	 * 获得一天开始的时间
	 * @param time
	 * @return
	 */
	public static long getDayStartTime(long time){
		if(currentDate==null)
			currentDate = new GregorianCalendar(LOCAL_SOFT);   
		currentDate.setTimeInMillis(time);  
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		return currentDate.getTimeInMillis();  		
	}
	
	/**
	 * 获得一周开始的日子
	 * @param time
	 * @return
	 */
	public static long getWeekStartTime(long time){
		if(currentDate==null)
			currentDate = new GregorianCalendar(LOCAL_SOFT);    
		currentDate.setTimeInMillis(time);  
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);        
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
		return currentDate.getTimeInMillis();  		
	}

}
