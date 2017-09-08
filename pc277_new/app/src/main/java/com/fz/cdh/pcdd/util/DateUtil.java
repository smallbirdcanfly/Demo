package com.fz.cdh.pcdd.util;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期处理工具类
 */
public class DateUtil {

	public static long CURTIME = 0;
	private static final long MINUTE_DISTANCE = 1000*60;//分钟
	private static final long HOUR_DISTANCE = 1000*60*60;//小时
	private static final long DAY_DISTANCE = 1000*60*60*24;//天
	private static final long DAY2_DISTANCE = 1000*60*60*24*2;//两天
	
	public final static String DATE_YMD = "yyyy-MM-dd";
	public final static String DATETIME_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public final static String DATETIME_HMS = "HH:mm:ss";
	public final static String DATETIME_STAMP="yyyyMMddHHmmss";
	
	private static final SimpleDateFormat SDF_DATE_YMD = new SimpleDateFormat(DATE_YMD);
	private static final SimpleDateFormat SDF_DATE_MD = new SimpleDateFormat("MM-dd");
	private static final SimpleDateFormat SDF_DATE_HM = new SimpleDateFormat("HH:mm");
	
	/**
	 * 当前时间:yyyy-MM-dd
	 * @return
	 */
	public static String getCurrDate(){
		return new SimpleDateFormat(DATE_YMD).format(new Date());
	}
	
	/**
	 * 当前时间:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrDateTime(){
		return new SimpleDateFormat(DATETIME_YMDHMS).format(new Date());
	}
	
	/**
	 * 当前时间:HH:mm:ss
	 * @return
	 */
	public static String getCurrTime(){
		return new SimpleDateFormat(DATETIME_YMDHMS).format(new Date());
	}
	
	public static String getDateTimeStr(Date date){
		return new SimpleDateFormat(DATETIME_YMDHMS).format(date);
	}
	
	public static String getDateStr(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	
	public static Date parseDate(String dateStr,String pattern){
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDateText(long date){
		
		Calendar recordCal = GregorianCalendar.getInstance();
		recordCal.setTimeInMillis(date);
		
		Calendar currentCal = GregorianCalendar.getInstance();
		
		long current = currentCal.getTimeInMillis();
		long sub = current - date;
		
		if(sub < MINUTE_DISTANCE){
			return "刚刚";
		}else if(sub < HOUR_DISTANCE){
			return (sub / (1000*60)) +" 分钟前";//显示:1分钟前 - 60 分钟前
		}
		if(recordCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR)){
			
			if(recordCal.get(Calendar.DAY_OF_YEAR) == currentCal.get(Calendar.DAY_OF_YEAR)){//当天
				SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
				return "今天 "+SDF_DATE_HM.format(recordCal.getTime());//显示： HH:mm
			}else if(recordCal.get(Calendar.DAY_OF_YEAR) == (currentCal.get(Calendar.DAY_OF_YEAR) -1)){
				SDF_DATE_HM.setTimeZone(TimeZone.getDefault());
				return "昨天 " + SDF_DATE_HM.format(recordCal.getTime());//显示：昨天 HH:mm
			}else{
				SDF_DATE_MD.setTimeZone(TimeZone.getDefault());
				return SDF_DATE_MD.format(recordCal.getTime());//显示：MM-dd
			}
			
		}else{
			SDF_DATE_YMD.setTimeZone(TimeZone.getDefault());
			return SDF_DATE_YMD.format(recordCal.getTime());//显示：yyyy-MM-dd
		}
	}
	
	public static String getTime(long time,int code) {
		SimpleDateFormat format = null;
		switch (code) {
		case 0:
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		case 1:
			format = new SimpleDateFormat("MM-dd HH:mm");
		case 2:
			format = new SimpleDateFormat(DATE_YMD);
			break;
		default:
			break;
		}
		return format.format(new Date(time));
	}
	
	public static String getDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return getTime(c.getTimeInMillis(), 2);
	}
	
	public static String getTime(String time) {
		Date date = StringToDate(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(date);
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}
	
	public static String getPeriodOfValidity(Context mContext,String startTime,String endTime){
		if(startTime == null||endTime==null){
			return "";
		}
		Date sd = StringToDate(startTime);
		Date ed = StringToDate(endTime);
		Date nd = new Date(System.currentTimeMillis());
		SimpleDateFormat yf = new SimpleDateFormat("yyyy");
		SimpleDateFormat mf = new SimpleDateFormat("MM");
		SimpleDateFormat df = new SimpleDateFormat("dd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		
		if(ed.getTime()<nd.getTime()){
			return "时间不符合";
		}
		if(yf.format(ed).equals(yf.format(nd))){
			if(mf.format(ed).equals(mf.format(nd))){
				int eday = Integer.parseInt(df.format(ed));
				int nday = Integer.parseInt(df.format(nd));
				if((eday - nday) == 1){
					return "时间不符合";
				}else if(eday - nday>10){
					format.format(ed);
				}else
				return "时间不符合";
			}else{
				return format.format(ed);
			}
		}
		return format.format(ed);
	}
	
	public static Date StringToDate(String s){
		Date time=new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			 time=sd.parse(s);
		}
		catch (ParseException e) { 
			System.out.println("输入的日期格式有误！"); 
		}
		return time;
	}
	
	/**
	 * 将yyyy-MM-dd格式字符串转换成long
	 */
	public static long StringToLong(String dateStr) {
		return StringToLong(dateStr, DATE_YMD);
	}
	
	public static long StringToLong(String dateStr, String pattern) {
		if(TextUtils.isEmpty(dateStr))
			return 0;
		return parseDate(dateStr, pattern).getTime();
	}
	
	/**
	 * 计算月份差
	 */
	public static int calculMonthDifference(String dateStr) {
		Date date = parseDate(dateStr, DATE_YMD);
		Calendar beforeCalendar = Calendar.getInstance();
		beforeCalendar.setTime(date);
		Calendar curCalendar = Calendar.getInstance();
		
		int beforeYear = beforeCalendar.get(Calendar.YEAR);
		int beforeMonth = beforeCalendar.get(Calendar.MONTH);
		int curYear = curCalendar.get(Calendar.YEAR);
		int curMonth = curCalendar.get(Calendar.MONTH);
		
		int month = (curYear-beforeYear)*12 + (curMonth-beforeMonth);
		return Math.abs(month);
	}
	
	public static int getYear(String dateStr) {
		Date date = parseDate(dateStr, DATE_YMD);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	public static int getMonth(String dateStr) {
		Date date = parseDate(dateStr, DATE_YMD);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH)+1;
	}
	
	public static int getWeek(String dateStr) {
		Date date = parseDate(dateStr, DATE_YMD);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 比较日期大小（精确到天）
	 * 0: t1=t2  -1: t1<t2  1: t1>t2
	 */
	public static int compareDay(long t1, long t2) {
		String str1 = getTime(t1, 2);
		String str2 = getTime(t2, 2);
		return compareDay(str1, str2);
	}
	
	public static int compareDay(String ymd1, String ymd2) {
		try {
			Date d1 = SDF_DATE_YMD.parse(ymd1);
			Date d2 = SDF_DATE_YMD.parse(ymd2);
			int r = d2.compareTo(d1);
			if(r < 0)
				return -1;
			else if(r > 0)
				return 1;
			else
				return 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getChatTime(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(timesamp);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(timesamp);
			break;
		case 2:
			result = "前天 " + getHourAndMin(timesamp);
			break;

		default:
			// result = temp + "天前 ";
			result = getTime(timesamp,1);
			break;
		}

		return result;
	}
}