/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.date;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * @author Mark Gottschling on Apr 14, 2016
 *
 */
public class DateHelper {

	public static final String MYSQL_DATE_PATTERN = "yyyy-MM-dd";
	public static final String MDY_PATTERN = "MM-dd-yyyy";

	// ISO 8601 BASIC is used by the API signature
	public static String ISO_8601BASIC_DATE_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
	
	// ISO 8601 LIGHTSPEED
	public static String ISO_8601_EPOCH_DATE_PATTERN = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'+00:00'";
	public static String ISO_8601_LIGHTSPEED_DATE_PATTERN = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'+00:00'";
	public static String ISO_8601_LIGHTSPPED_DATE_ONLY_PATTERN = "yyyy-MM-dd";
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isIsoTimestamp(String s) {
		return s.matches("\\d{8}T\\d{6}Z");
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Date parseIsoDateTime(String s) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_LIGHTSPEED_DATE_PATTERN);
		dateFormat.setLenient(false);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date result = null;
		if (s != null && s.length() > 0) {
			result = dateFormat.parse(s, new ParsePosition(0));
		}
		return result;
	}
	
	/**
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		DateTime dt = new DateTime(date);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);
		String d = format.format(date);
		if (dt.getHourOfDay() == 0 && dt.getMinuteOfHour() == 0 && dt.getSecondOfMinute() == 0) {
			d = d.replace("+", "-");
		}
		return d;
	}
	
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date result = null;
		if (date != null && date.length() > 0) {
			result = dateFormat.parse(date, new ParsePosition(0));
		}
		return result;		
	}
	
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date toDateNoTZ(String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
//		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date result = null;
		if (date != null && date.length() > 0) {
			result = dateFormat.parse(date, new ParsePosition(0));
		}
		return result;		
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String toMySqlDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(MYSQL_DATE_PATTERN);
		format.setLenient(false);
		String d = format.format(date);
		return d;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String toMDY(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(MDY_PATTERN);
		format.setLenient(false);
		String d = format.format(date);
		return d;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTomorrowDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @param dateStr
	 * @return
	 */
	public static DateTimeZone getZone(String dateStr) {
		DateTimeZone zone = DateTimeZone.getDefault();//.toString();
		int idx = dateStr.lastIndexOf("-");
		// either not found (-1) or not a proper ISO fromat date (<9)
		if (idx == -1 || idx < 9) idx = dateStr.lastIndexOf("+");
		if (idx > -1) zone = DateTimeZone.forID(dateStr.substring(idx));
		return zone;
	}
	
	/**
	 * 
	 * @param zone
	 * @return
	 */
	public static DateTimeZone getZone(Double zone) {
		// check for non full hour zone
		Boolean b = Math.floor(zone) < Math.round(zone);
		String s = "";
		if (b == true) {
			s = (zone < 0.0) ? String.format("%03d:30", ((int)Math.ceil(zone))) : String.format("+%02d:30", ((int)Math.floor(zone))) ;
		}
		else {
			s = (zone < 0.0) ? String.format("%03d:00", zone.intValue()) : String.format("+%02d:00", zone.intValue()) ;
		}
		return DateTimeZone.forID(s);
	}
}
