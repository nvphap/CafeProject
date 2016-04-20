package com.cafe.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 * The login action.
 *
 * @author phap.nguyen
 * @version 1.0
 * @since 2015-05-27
 * @update
 */
public class AppStrUtils {
	public final static String TIME_SEPERATE="~";
	public final static String SQL_APOSTROPHE="'";
	public final static String SEPERATE_JSON = ";||;";
	public final static String DATE_SEPERATE = "-";
	public final static String SQL_PERCENT = "%";
	public final static String SEPERATE_SN = ";";
	public final static String SEPERATE_INFO = "|";
	public final static String SEPERATE_INFO_SPLIT = "\\|";
	//
	public final static String NEW_LINE_HTML = "</br>";
	public final static String NEW_LINE = "\n";
	public final static String SEPERATE_INFO_BLANK = " | ";

	public final static String PATTERN_EMAIL = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	public final static String PATTERN_MOBILE = "^[0-9.()-]{10,25}$";
	public final static String PATTERN_NUMBER = "^[-+]?[0-9]*\\.?[0-9]+$";
	public final static String EMPTY = "";
	public final static String BLANK = " ";
	public final static String PLUS = "+";
	public final static int MOBILE_MAX = 12;
	public final static String PRE_KO = "82";
	
	public static String toTimeString(double fullHour){
		int hours = (int)Math.floor(fullHour);
		String hourStr = StringUtils.EMPTY;
		if(hours<10){
			hourStr = "0"+hours;
		}else{
			hourStr = String.valueOf(hours);
		}
		double minutes = (fullHour - hours)*60;
		String minutesStr = String.valueOf((int)minutes);
		if(minutesStr.length()==1){
			minutesStr = "0"+minutesStr;
		}else if(minutesStr.length()>2){
			minutesStr = minutesStr.substring(0,1);
		}
		StringBuffer timeStr = new StringBuffer();
		timeStr.append(hourStr).append(":").append(minutesStr).append(":00");
		return timeStr.toString();
	}
	
	public static String getExceptionContent(Exception exception){
		 StringWriter sw = new StringWriter();
		 exception.printStackTrace(new PrintWriter(sw));
		 return sw.toString();
	}
	

	public static boolean isEmpty(String value) {
		if (null == value) {
			return true;
		}
		if (EMPTY.equals(value)) {
			return true;
		}
		return false;
	}

	public static String toEmpty(Object value) {
		if (null == value) {
			return EMPTY;
		}
		return value.toString();
	}

	public static String toString(Object value) {
		if (null == value) {
			return EMPTY;
		}
		return value.toString();
	}

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(PATTERN_EMAIL,
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isValidMobile(String mobile) {
		String tempMobile = mobile;
		if (tempMobile.startsWith(PLUS)) {
			tempMobile = tempMobile.substring(1);
			if (tempMobile.length() > MOBILE_MAX) {
				return false;
			}
			if (!tempMobile.startsWith(PRE_KO)) {
				return false;
			}
		} else {
			if (tempMobile.length() > (MOBILE_MAX - 1)) {
				return false;
			}
		}
		Pattern pattern = Pattern.compile(PATTERN_NUMBER);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}

	/**
	 * isNumeric: Validate a number using Java regex. This method checks if the
	 * input string contains all numeric characters.
	 * 
	 * @param email
	 *            String. Number to validate
	 * @return boolean: true if the input is all numeric, false otherwise.
	 */

	public static boolean isNumeric(String inputStr) {
		/*
		 * Number: A numeric value will have following format: ^[-+]?: Starts
		 * with an optional "+" or "-" sign. [0-9]*: May have one or more
		 * digits. \\.? : May contain an optional "." (decimal point) character.
		 * [0-9]+$ : ends with numeric digit.
		 */

		// Initialize reg ex for numeric data.
		Pattern pattern = Pattern.compile(PATTERN_NUMBER);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}

	public static String toUTF8(String message) {
		try {
			return new String(message.getBytes(Constant.ISO), Constant.UTF8);
		} catch (UnsupportedEncodingException e) {
			return StringUtils.EMPTY;
		}

	}

	public static String toDisplayTime(Time time) {
		if (null != time) {
			return time.toString().substring(0, 5);
		}
		return StringUtils.EMPTY;
	}

	public static String decodeMD5(String s) {

		StringBuffer sb = new StringBuffer();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			//
		}
		return sb.toString();
	}

	public static String decodeSHA256(String s) {

		StringBuffer sb = new StringBuffer();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(s.getBytes());
			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			//
		}
		return sb.toString();
	}

	public static String priceWithoutDecimal (Double price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,##0");
	    return formatter.format(price);
	}
	
	public static String priceWithoutDecimal (int price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,##0");
	    return formatter.format(price);
	}

	public static String priceToString(Double price) {
		return priceWithoutDecimal(price);
	}
	
	public static String priceToString(int price) {
		return priceWithoutDecimal(price);
	}
}
