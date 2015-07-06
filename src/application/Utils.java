package application;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Utilities for data validation and formatting.
 */
public class Utils {

	// Units in milliseconds
	public static final long YEAR = 31556926000L;
	public static final long MONTH = 2629743830L;
	public static final long WEEK = 604800000L;
	public static final long DAY = 86400000L;

	/**
	 * Converts a formatted date string into the number of milliseconds since 1
	 * January 1970.s
	 * 
	 * @param date
	 *            string in the format YYYY-MM-DD.
	 * @return -1 if the date is not in the correct format, otherwise the time
	 *         since 1 January 1970 in milliseconds.
	 */
	public static long dateToMillis(String date) {
		if (!isDate(date)) {
			return -1;
		} else {
			String[] s = date.split("-");
			long years = (Long.parseLong(s[0]) - 1970) * YEAR;
			long months = Long.parseLong(s[1]) * MONTH;
			long days = Long.parseLong(s[2]) * DAY;

			return years + months + days;
		}
	}

	/**
	 * Returns true if the string argument can be parsed as a double.
	 */
	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Returns true if the string is in the format dddd-dd-dd where d is an
	 * integer.
	 * 
	 * @param str
	 *            string to be matched to the date regex.
	 */
	public static boolean isDate(String str) {
		return Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", str);
	}

	/**
	 * Returns true if the string is in the format dd:dd:dd where d is an
	 * integer.
	 * 
	 * @param str
	 *            the string to be matched to the time regex.
	 */
	public static boolean isTime(String str) {
		return Pattern.matches("\\d\\d:\\d\\d:\\d\\d", str);
	}

	/**
	 * Rounds a double to a specified number of decimal places.
	 * 
	 * @return double d rounded to dplaces decimal places.
	 */
	public static double roundDouble(double d, int dplaces) {
		BigDecimal bd = new BigDecimal(d);
		BigDecimal rounded = bd.setScale(dplaces, BigDecimal.ROUND_HALF_UP);
		return rounded.doubleValue();
	}

}
