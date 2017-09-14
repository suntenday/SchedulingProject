package com.suntenday.scheduling.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日历类工具
 *
 * @author suntenday
 */
@SuppressLint("SimpleDateFormat")
public class CalendarUtils {

	private int weeks = 0;// 用来全局控制 上一周，本周，下一周的周数变化
	private int MaxDate; // 一月最大天数
	private int MaxYear; // 一年最大天数
	
	public static long ONE_DAY = 24 * 3600000;
	public static long ONE_MINUTE = 60000;// 分
	public static long ONE_HOUR = 3600000;// 小时
	/**
	 * yyyyMMdd
	 * */
	public static final String DATE_BANK_FORMAT = "yyyyMMdd";
	/**
	 * yyyyMMddHH
	 * */
	public static final String DATE_BANK2_FORMAT = "yyyyMMddHH";
	/**
	 * yyyy-MM-dd
	 * */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss 24小时制
	 * */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd hh:mm:ss 12小时制
	 */
	public static final String DATETIME_FORMAT_12HOUR = "yyyy-MM-dd hh:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm
	 * */
	public static final String DATETIME_FORMAT_NOSECOND = "yyyy-MM-dd HH:mm";
	/**
	 * yy-MM-dd HH:mm
	 * */
	public static final String DATETIME2_FORMAT = "yy-MM-dd HH:mm";
	/**
	 * yyyyMMddHHmmss
	 * */
	public static final String DATETIME3_FORMAT = "yyyyMMddHHmmss";
	/**
	 * yyyyMMddHHmmssSSS 年月日时分秒毫秒
	 */
	public static final String DATATIME4_FORMAT = "yyyyMMddHHmmssSSS";
	/**
	 * yyyy年MM月dd日 HH:mm:ss
	 * */
	public static final String DATETIME4_FORMAT = "yyyy年MM月dd日 HH:mm:ss";

	/**
	 * yy年MM月dd日 HH:mm
	 * */
	public static final String DATETIME5_FORMAT = "yy年MM月dd日 HH:mm";

	/**
	 * yy年MM月dd日
	 * */
	public static final String DATETIME6_FORMAT = "yy年MM月dd日";

	/**
	 * 星期信息
	 */
	public static final String WEEK_FORMAT = "EEE";

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		CalendarUtils tt = new CalendarUtils();
		System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));
		System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
		System.out.println("获取本周日的日期~:" + tt.getCurrentWeekday());
		System.out.println("获取上周一日期:" + tt.getPreviousWeekday());
		System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
		System.out.println("获取下周一日期:" + tt.getNextMonday());
		System.out.println("获取下周日日期:" + tt.getNextSunday());
		System.out.println("获得相应周的周六的日期:" + tt.getNowTime("yyyy-MM-dd"));
		System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
		System.out.println("获取本月最后一天日期:" + tt.getDefaultDay());
		System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
		System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
		System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
		System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
		System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
		System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
		System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
		System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
		System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
		System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
		System.out.println("获取本季度第一天:" + tt.getThisSeasonFirstTime(11));
		System.out.println("获取本季度最后一天:" + tt.getThisSeasonFinallyTime(11));
		System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"
				+ CalendarUtils.getTwoDay("2008-12-1", "2008-9-29"));
		System.out.println("获取当前月的第几周：" + tt.getWeekOfMonth());
		System.out.println("获取当前年份：" + tt.getYear());
		System.out.println("获取当前月份：" + tt.getMonth());
		System.out.println("获取今天在本年的第几天：" + tt.getDayOfYear());
		System.out.println("获得今天在本月的第几天(获得当前日)：" + tt.getDayOfMonth());
		System.out.println("获得今天在本周的第几天：" + tt.getDayOfWeek());
		System.out.println("获得半年后的日期："
				+ tt.convertDateToString(tt.getTimeYearNext()));
	}

	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取今天在本年的第几天
	 * @return
	 */
	public static int getDayOfYear() {
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得今天在本月的第几天(获得当前日)
	 * @return
	 */
	public static int getDayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得今天在本周的第几天
	 * @return
	 */
	public static int getDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取当前月的第几周
	 * @return
	 */
	public static int getWeekOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 获得半年后的日期
	 * @return
	 */
	public static Date getTimeYearNext() {
		Calendar.getInstance().add(Calendar.DAY_OF_YEAR, 183);
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 新增秒
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addSecond(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, i);
		return c.getTime();
	}

	/**
	 * 新增分
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMinute(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, i);
		return c.getTime();
	}

	/**
	 * 新增月份
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMonth(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, i);
		return c.getTime();
	}

	/**
	 * 新增日
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addDay(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, i);
		return c.getTime();
	}

	/**
	 * 新增年
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addYear(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, i);
		return c.getTime();
	}

	/**
	 * 转换date成字符串
	 * @param dateTime
	 * @return
	 */
	public static String convertDateToString(Date dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(dateTime);
	}


	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	public static String getWeek(String sdate) {
		return getWeek(sdate, DATE_FORMAT);
	}

	public static String getWeek(String sdate, String format) {
		// 再转换为时间
		Date date = CalendarUtils.strToDate(sdate, format);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}
	
	/**
	 * 获取传入日期的后几天
	 * @param dateStr
	 * @param days
	 * @return
	 */
	public static String getDaysLater(String dateStr, int days){
		Date date = strToDate(dateStr);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); //这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		String daysLaterDateStr = formatter.format(date);
		return daysLaterDateStr;
	}
	
	public static Date getDaysLaterDate(String dateStr, int days){
		Date date = strToDate(dateStr);
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); //这个时间就是日期往后推一天的结果
		return date;
	}

	/**
	 * 字符串转Date
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 字符串转Date
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * Date转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStr(Date date, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date dateFormat(Date date, String format){
		String dateToStr = dateToStr(date, format);
		return strToDate(dateToStr, format);
	}

	/**
	 * 获取两天之间的间隔天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 获取两天之间相差几天几小时(yyyy-MM-dd HH:mm)
	 * @param date1
	 * @param date2
	 * @return  返回字符串参数用, ps: d天h小时,自动替换d为天数,h为小时数
	 */
	public static String getTwoDaysBetweenWithDayAndHour(String date1, String date2, String resultDay, String resultHour, boolean isCarry) {
		if (date1 == null || date1.equals(""))
			return "0";
		if (date2 == null || date2.equals(""))
			return "0";
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		return getTwoDaysBetweenWithDayAndHour(date, mydate, resultDay, resultHour, isCarry);
	}
	
	public static String getTwoDaysBetweenWithDayAndHour(Date date, Date mydate, String resultDay, String resultHour, boolean isCarry) {
		long hour = (mydate.getTime() - date.getTime()) / (60 * 60 * 1000);
//		long minute = (mydate.getTime() - date.getTime()) % (60 * 60 * 1000);
		double fullHour = (mydate.getTime() - date.getTime()) / (60d * 60 * 1000);
		double mins = fullHour - hour;
		long d = hour/24;
		long h = hour%24;
		String showH = h +"";
		double doubleH = h;
		if(mins>0){
			if(isCarry){
				doubleH = h + 1;
			}else {
				doubleH = h + mins;
			}
			showH = doubleH + "";
		}
		showH = StringUtils.decimalKillZeroFormat(showH);
		String result = "";
		if(d<=0&&doubleH>0){
			result = resultHour.replace("h", showH); 
		}else if(d>0&&doubleH<=0){
			result = resultDay.replace("d", d+"");
		}else if(d>0&&doubleH>0){
			result = resultDay.replace("d", d+"")+resultHour.replace("h", showH);
		}else if(mins>0){
			result = resultHour.replace("h", showH);
		}
		return result;
	}
	
	public enum OVERDAY_CHECK_TYPE{
		DAY,HOUR,MINUTE
	}
	
	/**
	 * 是否nextDay大于frontDay
	 * @param dateFrontStr
	 * @param dateNextStr
	 * @param type	类型:DAY按日期判断 HOUR按小时判断 MINUTE按分钟判断
	 * @return
	 */
	public static boolean isOverDay(String dateFrontStr ,String dateNextStr, OVERDAY_CHECK_TYPE type){

		if (dateFrontStr == null || dateFrontStr.equals(""))
			return false;
		if (dateNextStr == null || dateNextStr.equals(""))
			return false;
		
		switch(type){
			case DAY:
				SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
				// 转换为标准时间
				try {
					Date dateFront = formart.parse(dateFrontStr);
					Date dateNext = formart.parse(dateNextStr);
					isOverDay(dateFront, dateNext, type);
				} catch (Exception e) {
				}
				break;
			case HOUR:
				SimpleDateFormat formartHour = new SimpleDateFormat("yyyy-MM-dd HH");
				// 转换为标准时间
				try {
					Date dateFront = formartHour.parse(dateFrontStr);
					Date dateNext = formartHour.parse(dateNextStr);
					isOverDay(dateFront, dateNext, type);
				} catch (Exception e) {
				}
				break;
			case MINUTE:
				SimpleDateFormat formartMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				// 转换为标准时间
				try {
					Date dateFront = formartMinute.parse(dateFrontStr);
					Date dateNext = formartMinute.parse(dateNextStr);
					isOverDay(dateFront, dateNext, type);
				} catch (Exception e) {
				}
				break;
		}
		return false;
	}
	
	public static boolean isOverDay(Date dateFront ,Date dateNext, OVERDAY_CHECK_TYPE type){
		switch(type){
			case DAY:
				// 转换为标准时间
				try {
					long checkSpace = (dateNext.getTime() - dateFront.getTime()) / (24 * 60 * 60 * 1000);
					if(checkSpace>0){
						return true;
					}else{
						return false;
					}
				} catch (Exception e) {
				}
				break;
			case HOUR:
				// 转换为标准时间
				try {
					long checkSpace = (dateNext.getTime() - dateFront.getTime()) / (60 * 60 * 1000);
					if(checkSpace>0){
						return true;
					}else{
						return false;
					}
				} catch (Exception e) {
				}
				break;
			case MINUTE:
				// 转换为标准时间
				try {
					long checkSpace = (dateNext.getTime() - dateFront.getTime()) / (60 * 1000);
					if(checkSpace>0){
						return true;
					}else{
						return false;
					}
				} catch (Exception e) {
				}
				break;
		}
		return false;
	}
	
	/**
	 * 获取本月最后一天日期
	 * @return
	 */
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取上月第一天日期
	 * @return
	 */
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取本月第一天日期
	 * @return
	 */
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取本周日的日期
	 * @return
	 */
	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}
	
	/**
	 * 获取本周六的日期
	 * @return
	 */
	public static String getCurrentSaturday(){
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 5);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取当天日期
	 * @param dateformat
	 * @return
	 */
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String nowDate = dateFormat.format(now);
		return nowDate;
	}
	
	/**
	 * 获取当天日期
	 * @return
	 */
	public static String getCurrentDate() {
		return getCurrentDate(null);
	}

	/**
	 * 获取当天日期
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		String dateformat = "yyyy-MM-dd";
		if(StringUtils.isStrNotEmpty(format)){
			dateformat = format;
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		date.getTime();
		SimpleDateFormat mDateFormat = new SimpleDateFormat(dateformat);
		return mDateFormat.format(date);
	}
	
	public static String getTodayDateStr(){
		return getCurrentDate("yyyy-MM-dd");
	}
	
	public static Date getTodayDate(){
		Date date = new Date();
		return date;
	}
	
	/**
	 * 获取当前第二天日期
	 * @param format
	 * @return
	 */
	public static String getNextDayDateStr(String format){
		String dateformat = "yyyy-MM-dd";
		if(StringUtils.isStrNotEmpty(format)){
			dateformat = format;
		}
		Date date = new Date();
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);

		calendar.add(GregorianCalendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); //这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat(dateformat, Locale.getDefault());
		String nextDateString = formatter.format(date);
		return nextDateString;
	}
	
	public static String getNextDayDateStr(){
		return getNextDayDateStr("yyyy-MM-dd");
	}
	
	public static Date getNextDayDate(String format)
	{
		String nextDayDateStr = getNextDayDateStr(format);
		return strToDate(nextDayDateStr, format);
	}
	
	public static Date getNextDayDate(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(date);

		calendar.add(GregorianCalendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); //这个时间就是日期往后推一天的结果
		return date;
	}
	
	public static Date getNextDayDate(Date nowDate){
		return getNextDayDate(nowDate, 1);
	}
	
	public static Date getNextDayDate(Date nowDate, int offset){
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(nowDate);
		calendar.add(GregorianCalendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
		nowDate = calendar.getTime(); //这个时间就是日期往后推一天的结果
		return nowDate;
	}
	
	public static Date getNextDayDate(int offset){
		Calendar calendar = Calendar.getInstance(Locale.CHINA);

		calendar.add(GregorianCalendar.DATE, offset);//把日期往后增加一天.整数往后推,负数往前移动

		return calendar.getTime();
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获取本周一日期
	 * @return
	 */
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取周六日期
	 * @return
	 */
	public String getSaturday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取上周日日期
	 * @return
	 */
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取上周一日期
	 * @return
	 */
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}


	public String getNextMonday() {
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取下周日日期
	 * @return
	 */
	public String getNextSunday() {

		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	@SuppressWarnings("unused")
	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}


	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取下月第一天日期
	 * @return
	 */
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取下月最后一天日期
	 * @return
	 */
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取明年最后一天日期
	 * @return
	 */
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取明年第一天日期
	 * @return
	 */
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}


	@SuppressWarnings("unused")
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	/**
	 * 获取本年的第一天日期
	 * @return
	 */
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	/**
	 * 获得本年最后一天的日期
	 * @return
	 */
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	/**
	 * 获得上年第一天的日期
	 * @return
	 */
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	/**
	 * 获得上年最后一天的日期
	 * @return
	 */
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	/**
	 * 获取本季度第一天
	 * @param month
	 * @return
	 */
	public String getThisSeasonFirstTime(int month) {
		int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		@SuppressWarnings("unused")
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days;
		return seasonDate;

	}

	/**
	 * 获取本季度最后一天
	 * @param month
	 * @return
	 */
	public String getThisSeasonFinallyTime(int month) {
		int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		@SuppressWarnings("unused")
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		@SuppressWarnings("unused")
		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}


	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}


	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}


	public boolean isLeapYear2(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}
	
	/**
	 * 判断是否过期
	 * @param expireTime
	 * @return
	 */
	public static boolean isExpire(String expireTime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
		try {
			Date dt1 = df.parse(expireTime);
			Date nowTime = new Date();
			if (dt1.getTime() > nowTime.getTime()) {
				return false; //未过期，expireTime在当前时间后面
			} else if (dt1.getTime() <= nowTime.getTime()) {
				return true;//过期，expireTime在当前时间前面
			} 
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static String getWeek(int weekDay) {
		String week = "";
		switch (weekDay) {
			case Calendar.MONDAY:
				week = "周一";
				break;
			case Calendar.TUESDAY:
				week = "周二";
				break;
			case Calendar.WEDNESDAY:
				week = "周三";
				break;
			case Calendar.THURSDAY:
				week = "周四";
				break;
			case Calendar.FRIDAY:
				week = "周五";
				break;
			case Calendar.SATURDAY:
				week = "周六";
				break;
			case Calendar.SUNDAY:
				week = "周日";
				break;
		}
		return week;
	}

	//根据分钟数 得出几天几小时几分钟
	public static String getDatesHoursMinutesByMinutes(long allMinutes){
		String returnStr = "";
		long hours = allMinutes / 60;   //总小时数
		long days = hours / 24 ;       //天数
		long minutes = allMinutes - hours*60;   //剩余分钟数 
		hours = hours-days*24;     //剩余小时数
		
		if(days>0){
			returnStr = returnStr +days+"天";
			if(hours>=0){
				returnStr = returnStr +hours+"小时";
			}
			if(minutes>=0){
				returnStr = returnStr +minutes+"分钟";
			}
		}else{
			if(hours>0){    //天数为0 小时大于0才显示小时数
				returnStr = returnStr +hours+"小时";
			}
			if(minutes>=0){
				returnStr = returnStr +minutes+"分钟";
			}
		}
		return returnStr;
	}

	//判断，今天 明天 后天
	public static String getJMHStr(Date date){
		String str = "";
		String btwDays = CalendarUtils.getTwoDay(new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE).format(date),CalendarUtils.getCurrentDate("yyyy-MM-dd"));
		if(btwDays.equals("0")){
			str = "今天";
		}else if(btwDays.equals("1")){
			str = "明天";
		}else if(btwDays.equals("2")){
			str = "后天";
		}
		return str;
	}

	/**
	 * 获取月的总天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthDays(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					days = 31;
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					days = 30;

			}
		} else {
			// 闰年
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;

		}
		return days;
	}
}