package org.jcs.dss.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
	
	public static String getCurTimeInGMTString() {
		Date date = new Date();
		String dateGMTStr = date.toGMTString();
		Calendar calendar = Calendar.getInstance();
		Date curTime = calendar.getTime();
		String curTimeStr = new SimpleDateFormat("EE", Locale.ENGLISH)
				.format(curTime.getTime());
		String dateStr = curTimeStr + ", " + dateGMTStr;
		return dateStr;
	}

}
