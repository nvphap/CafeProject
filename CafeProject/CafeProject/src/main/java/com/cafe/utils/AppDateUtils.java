package com.cafe.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * The login action.
 *
 * @author phap.nguyen
 * @version 1.0
 * @since 2015-05-27
 * @update
 */
public class AppDateUtils {
	public final static String DAY_MONDAY="Monday";
	public final static String DAY_TUESDAY="Tuesday";
	public final static String DAY_WEDNESDAY="Wednesday";
	public final static String DAY_THURSDAY="Thursday";
	public final static String DAY_FRIDAY="Friday";
	public final static String DAY_SATURDAY="Saturday";
	public final static String DAY_SUNDAY="Sunday";
	
	public final static String DAY_KO_MONDAY="월";
	public final static String DAY_KO_TUESDAY="화";
	public final static String DAY_KO_WEDNESDAY="수";
	public final static String DAY_KO_THURSDAY="목";
	public final static String DAY_KO_FRIDAY="금";
	public final static String DAY_KO_SATURDAY="토";
	public final static String DAY_KO_SUNDAY="일";
	
	// Format date type
	public final static String DATE_FORMAT = "yyyy/MM/dd";
	public final static String YYYY_MM_DD="yyyy-MM-dd";
	public final static String DATE_FORMAT3="yyyy-MM-dd HH:mm";
	public final static String TIME_FORMAT1="hh a";
	public final static String DATE_FORMAT4="MMMM dd,yyyy";
	public final static String DATE_FORMAT5="E,MMMM dd,yyyy";
	public final static String DATE_FORMAT5_KO="yyyy년  MM월 dd일 (E)";
	
	private static DateFormat dateFormat1 = new SimpleDateFormat(YYYY_MM_DD);
	private static DateFormat dateFormat3 = new SimpleDateFormat(DATE_FORMAT3);
	private static DateFormat timeFormat1 = new SimpleDateFormat(TIME_FORMAT1);
	private static DateFormat timeFormat4 = new SimpleDateFormat(DATE_FORMAT4);
	private static DateFormat timeFormat5 = new SimpleDateFormat(DATE_FORMAT5);
	
	
	public static int calDaysBetween2Days(Date startDate, Date endDate){
		if(null== startDate || null==endDate){
			return 1;
		}
		Calendar day1 = Calendar.getInstance();
	    Calendar day2 = Calendar.getInstance(); 
	    day1.setTime(startDate);
	    day2.setTime(endDate);
	    int daysBetween = day2.get(Calendar.DAY_OF_YEAR) - day1.get(Calendar.DAY_OF_YEAR);
	    daysBetween+=1;
	    if(daysBetween<1){
	    	daysBetween=1;
	    }
	    return daysBetween;
	}
	
	public static Time getHHMMTime(Date date){
		String dateStr = toYYYYMMDDHHMMStr(date);
		if(!StringUtils.isEmpty(dateStr)){
			String timeStr = dateStr.substring(11,16);
			return Time.valueOf(timeStr+":00");
		}
		return null;
	}
	
	public static String toEMMMMDDYYYY(Date date){
		if(null==date){
			return StringUtils.EMPTY;
		}
		try{
			timeFormat5.setLenient(false);
			return timeFormat5.format(date);
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}
	
	public static String toEMMMMDDYYYY(Date date,String languageCode){
		if(null==date){
			return StringUtils.EMPTY;
		}
		try{
			DateFormat dateFormat = null;
			if (Constant.LANG_ENGLISH_CODE.equals(languageCode)) {
				dateFormat = new SimpleDateFormat(DATE_FORMAT5, Locale.ENGLISH);
			} else {
				dateFormat = new SimpleDateFormat(DATE_FORMAT5_KO, Locale.KOREAN);
			} 
			dateFormat.setLenient(false);
			String dateResult = dateFormat.format(date);
			if(!Constant.LANG_ENGLISH_CODE.equals(languageCode)){//Korean
				//StringBuffer finalDate = new StringBuffer(dateResult);
				//finalDate.append(AppStrUtils.BLANK).append("(").append(getKoreanDay(date)).append(")");
				//dateResult = finalDate.toString();
			}
			return dateResult;
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}
	
	public static String getKoreanDay(Date date){
		if(null!=date){
			int day = getDayOfWeek(date);
			switch (day) {
				case Calendar.SUNDAY:
					return DAY_KO_SUNDAY;
				case Calendar.MONDAY:
					return DAY_KO_MONDAY;
				case Calendar.TUESDAY:
					return DAY_KO_TUESDAY;
				case Calendar.WEDNESDAY:
					return DAY_KO_WEDNESDAY;
				case Calendar.THURSDAY:
					return DAY_KO_THURSDAY;
				case Calendar.FRIDAY:
					return DAY_KO_FRIDAY;
				case Calendar.SATURDAY:
					return DAY_KO_SATURDAY;
			}
		}
		return StringUtils.EMPTY; 
	}
	
	public static String toMMMMDDYYYY(Date date){
		if(null==date){
			return StringUtils.EMPTY;
		}
		try{
			timeFormat4.setLenient(false);
			return timeFormat4.format(date);
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}
	
	public static String toHHAPM(Date date){
		if(null==date){
			return StringUtils.EMPTY;
		}
		try{
			timeFormat1.setLenient(false);
			return timeFormat1.format(date);
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-19
	 * @update
	 */
	
	public static Date getDate(Long millis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		clearTime(cal);
        return cal.getTime();
	}
	
	public static Date getFullDate(Long millis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
        return cal.getTime();
	}
	
	/**
	 * Convert to date to string by format yyyy-MM-dd
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-16
	 * @update
	 */
	
	public static Date getStartOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        clearTime(cal);
        return cal.getTime();
	}
	
	/**
	 * 
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-17
	 * @update
	 */
	
	public static void clearTime(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	}
	
	public static Date clearTime(Date date) {
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	/**
	 * 
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-17
	 * @update
	 */
	
	public static Date setTimeToEndOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    cal.set(Calendar.MILLISECOND, 999);
	    return cal.getTime();
	}
	
	public static Date setTimeToEndOfDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}
	
	/**
	 * Convert to date to string by format yyyy/MM/dd
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-16
	 * @update
	 */
	
	public static Date getEndOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndOfDay(cal);
        return cal.getTime();
	}
	
	public static int getEndDayOfMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static int getDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static String getDayStringOfWeek(Date date){
		int day = getDayOfWeek(date);
		switch (day) {
			case Calendar.MONDAY:{return DAY_MONDAY;}
			case Calendar.TUESDAY:{return DAY_TUESDAY;}
			case Calendar.WEDNESDAY:{return DAY_WEDNESDAY;}
			case Calendar.THURSDAY:{return DAY_THURSDAY;}
			case Calendar.FRIDAY:{return DAY_FRIDAY;}
			case Calendar.SATURDAY:{return DAY_SATURDAY;}
			case Calendar.SUNDAY:{return DAY_SUNDAY;}
		}
		return AppStrUtils.EMPTY;
	}
	
	public static String getDayStringOfWeek(int day){
		switch (day) {
			case Calendar.MONDAY:{return DAY_MONDAY;}
			case Calendar.TUESDAY:{return DAY_TUESDAY;}
			case Calendar.WEDNESDAY:{return DAY_WEDNESDAY;}
			case Calendar.THURSDAY:{return DAY_THURSDAY;}
			case Calendar.FRIDAY:{return DAY_FRIDAY;}
			case Calendar.SATURDAY:{return DAY_SATURDAY;}
			case Calendar.SUNDAY:{return DAY_SUNDAY;}
		}
		return AppStrUtils.EMPTY;
	}
	
	public static int getDayOfWeek(Date date,int dateValue){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(dateValue, Calendar.DAY_OF_MONTH);
        return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	
	/**
	 * Convert to date to string by format yyyy/MM/dd
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-16
	 * @update
	 */
	public static String toYYYYMMDDStr(Date date){
		if(null==date){
			return StringUtils.EMPTY;
		}
		try{
			return dateFormat1.format(date);
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * Convert to date to string by format yyyy-MM-dd
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-16
	 * @update
	 */
	public static Date toYYMMDD(String date){
		if(StringUtils.isEmpty(date)){
			return null;
		}
		try{
			dateFormat1.setLenient(false);
			return dateFormat1.parse(date);
		}catch(Exception ex){
			return null;
		}
	}
	
	/**
	 * Convert to date to string by format yyyy/MM/dd
	 *
	 * @author phap.nguyen
	 * @version 1.0
	 * @since 2015-06-16
	 * @update
	 */
	/*public static String toYYMMDDStr2(Date date){
		if(null==date){
			return null;
		}
		try{
			dateFormat2.setLenient(false);
			return dateFormat2.format(date);
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}*/
	

	/**
	 * Convert to date to string by format yyyy/MM/dd
	 *
	 * @author dat.huynh
	 * @version 1.0
	 * @since 2015-06-08
	 * @update
	 */
	public static String convertDateToString(Date value, String language) {
		
		DateFormat dateFormat = null;
		if (language.equals(Constant.LANG_ENGLISH_CODE)) {
			dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
		} else {
			dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREAN);
		} 

		if (null == value) {
			return AppStrUtils.EMPTY;
		} else {
			dateFormat = new SimpleDateFormat(DATE_FORMAT);
			return dateFormat.format(value);
		}
	}

	/**
	 * Convert string to date by format yyyy/MM/dd
	 *
	 * @author dat.huynh
	 * @version 1.0
	 * @since 2015-06-08
	 * @update
	 */
	public static Date convertStringToDate(String value, String language) {
		
		DateFormat dateFormat = null;
		Date date = null;
		if (language.equals(Constant.LANG_ENGLISH_CODE)) {
			dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
		} else {
			dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREAN);
		} 
		
		try {
			date = dateFormat.parse(value);
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return date;	
	}
	
	/**
	 * Convert string to Timestamp by format yyyy/MM/dd
	 *
	 * @author dat.huynh
	 * @version 1.0
	 * @since 2015-07-15
	 * @update
	 */
	public static Timestamp convertStringToTimestamp(String value, String language) {
		
		DateFormat dateFormat = null;
		Date date = null;
		Timestamp timeStampDate = null;
		if (language.equals(Constant.LANG_ENGLISH_CODE)) {
			dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
		} else {
			dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.KOREAN);
		} 
		
		try {
			date = dateFormat.parse(value);
			timeStampDate = new Timestamp(date.getTime());
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return timeStampDate;	
	}
	
	
	public static boolean isYYYYMMDDHHMM(String date){
		if(StringUtils.isEmpty(date)){
			return false;
		}
		try{
			dateFormat3.setLenient(false);
			dateFormat3.parse(date);
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	
	public static Date toYYYYMMDDHHMM(String date){
		if(StringUtils.isEmpty(date)){
			return null;
		}
		try{
			dateFormat3.setLenient(false);
			return dateFormat3.parse(date);
		}catch(Exception ex){
			return null;
		}
	}
	
	public static String toYYYYMMDDHHMMStr(Date date){
		if(null==date){
			return StringUtils.EMPTY;
		}
		try{
			dateFormat3.setLenient(false);
			return dateFormat3.format(date);
		}catch(Exception ex){
			return StringUtils.EMPTY;
		}
	}
	
	public static String toHHMMStr(Time time){
		if(null!=time){
			return time.toString().substring(0,5);
		}
		return StringUtils.EMPTY;
	}
	
	public static Date getFirstDateOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}
	
	public static Date getEndDateOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_WEEK, cal.getMaximum(Calendar.DAY_OF_WEEK));
		return cal.getTime();
	}
	
	public static Calendar setWeekStart(Calendar calendar) {
		  while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
		    calendar.add(Calendar.DATE, -1);
		  }
		  clearTime(calendar); // method which sets H:M:S:ms to 0
		  return calendar;
	}
	
	public static Calendar setWeekEnd(Calendar calendar) {
		  while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
		    calendar.add(Calendar.DATE, 1);
		  }
		  clearTime(calendar); // method which sets H:M:S:ms to 0
		  return calendar;
	}
	
	
	/**
     * <p>Checks if two dates are on the same day ignoring time.</p>
     * @param date1  the first date, not altered, not null
     * @param date2  the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
    
    /**
     * <p>Checks if two calendars represent the same day ignoring time.</p>
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
    
    /**
     * <p>Checks if a date is today.</p>
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }
    
    /**
     * <p>Checks if a calendar date is today.</p>
     * @param cal  the calendar, not altered, not null
     * @return true if cal date is today
     * @throws IllegalArgumentException if the calendar is <code>null</code>
     */
    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }
    

    /** Determines whether or not a date has any time values (hour, minute, 
     * seconds or millisecondsReturns the given date with the time values cleared. */

    /**
     * Determines whether or not a date has any time values.
     * @param date The date.
     * @return true iff the date is not null and any of the date's hour, minute,
     * seconds or millisecond values are greater than zero.
     */
    public static boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.HOUR_OF_DAY) > 0) {
            return true;
        }
        if (c.get(Calendar.MINUTE) > 0) {
            return true;
        }
        if (c.get(Calendar.SECOND) > 0) {
            return true;
        }
        if (c.get(Calendar.MILLISECOND) > 0) {
            return true;
        }
        return false;
    }

    /** 
     * Returns the maximum of two dates. A null date is treated as being less
     * than any non-null date. 
     */
    public static Date max(Date d1, Date d2) {
        if (d1 == null && d2 == null) return null;
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return (d1.after(d2)) ? d1 : d2;
    }
    
    /** 
     * Returns the minimum of two dates. A null date is treated as being greater
     * than any non-null date. 
     */
    public static Date min(Date d1, Date d2) {
        if (d1 == null && d2 == null) return null;
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return (d1.before(d2)) ? d1 : d2;
    }

    /** The maximum date possible. */
    public static Date MAX_DATE = new Date(Long.MAX_VALUE);
    
    
    public static Date getDate(Date date, int denta){
    	Calendar cal = Calendar.getInstance();
    	if(null!=date){
    		cal.setTime(date);
    	}
    	cal.add(Calendar.DATE,denta);
    	return cal.getTime();
    }
    
    public static Date getDate(Date date, Time time){
    	if(null!=date && null!=time){
    		
    	}
    	String fullDate=toYYYYMMDDStr(date);
    	fullDate+=AppStrUtils.BLANK;
    	fullDate+=time.toString().substring(0,5);
    	return toYYYYMMDDHHMM(fullDate);
    }
    
   public static int getHour(Time time){
    	if(null!=time){
    		String fullTime = time.toString();
    		return Integer.parseInt(fullTime.substring(0,2));
    	}
    	return 0;
    }
    
    public static int getMinutes(Time time){
    	if(null!=time){
    		String fullTime = time.toString();
    		return Integer.parseInt(fullTime.substring(3,5));
    	}
    	return 0;
    }
    
    public static double toHour(Time time){
    	if(null!=time){
    		String fullTime = time.toString();
    		double fullHour = Integer.parseInt(fullTime.substring(0,2));
    		double minutes = Integer.parseInt(fullTime.substring(3,5));
    		fullHour +=(minutes/60);
    		return fullHour;
    	}
    	return 0;
    }
    
}

