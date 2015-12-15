package com.kaoshidian.oa.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author lip 创建于 2012-2-20 上午11:45:21
 */
public class DateUtils {
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
		"HH:mm:ss");
	private static final SimpleDateFormat weekFormat = new SimpleDateFormat(
		"EEEE");
	private static final SimpleDateFormat dateWeekFormat = new SimpleDateFormat(
	"yyyy-MM-dd EEE");

	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @param pattern
	 *            格式化模式，详见{@link SimpleDateFormat}构造器
	 *            <code>SimpleDateFormat(String pattern)</code>
	 * @return
	 */
	public static String formatDatetime(Date date, String pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat
		.clone();
		customFormat.applyPattern(pattern);
		return customFormat.format(date);
	}
	
	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}

	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}
    
	
	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 获得当前时间的<code>java.util.Date</code>对象
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static Calendar calendar() {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		return cal;
	}

	/**
	 * 获得当前时间的毫秒数
	 * <p>
	 * 详见{@link System#currentTimeMillis()}
	 * 
	 * @return
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	/**
	 * 
	 * 获得当前Chinese月份
	 * 
	 * @return
	 */
	public static int month() {
		return calendar().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得月份中的第几天
	 * 
	 * @return
	 */
	public static int dayOfMonth() {
		return calendar().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 今天是星期的第几天
	 * 
	 * @return
	 */
	public static int dayOfWeek() {
		return calendar().get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 判断是否是周末 
	 * @param date 表示日期的字符串，比如20100424 
	 * @param pattern 描述日期和时间格式的模式，比如yyyyMMdd 
	 * @return 
	 * @throws ParseException 
	 */  
	   public static boolean isWeekend(String date) throws ParseException  
	   {  
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date time = df.parse(date);  
	    return isWeekend(time);
	  }
	   
	   public static boolean isWeekend(Date time) {
		   Calendar cal = Calendar.getInstance();  
		    cal.setTime(time);       
		    int day = cal.get(Calendar.DAY_OF_WEEK);  	      
		    //if (day == Calendar.SATURDAY || day == Calendar.SUNDAY)
		    if (day == Calendar.SUNDAY)  
		    {  
		        return true;  
		    }  
		    else  
		    {  
		        return false;  
		    }  
	   }
	/**
	 * 今天是年中的第几天
	 * 
	 * @return
	 */
	public static int dayOfYear() {
		return calendar().get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断原日期是否在目标日期之前
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isBefore(Date src, Date dst) {
		return src.before(dst);
	}

	/**
	 * 判断原日期是否在目标日期之后
	 * 
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean isAfter(Date src, Date dst) {
		return src.after(dst);
	}

	/**
	 * 判断两日期是否相同
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqual(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	/**
	 * 判断某个日期是否在某个日期范围
	 * 
	 * @param beginDate
	 *            日期范围开始
	 * @param endDate
	 *            日期范围结束
	 * @param src
	 *            需要判断的日期
	 * @return
	 */
	public static boolean between(Date beginDate, Date endDate, Date src) {
		return beginDate.before(src) && endDate.after(src);
	}

	/**
	 * 获得当前月的最后一天
	 * <p>
	 * HH:mm:ss为0，毫秒为999
	 * 
	 * @return
	 */
	public static Date lastDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置零
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		int n = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
		cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
		return cal.getTime();
	}

	/**
	 * 获得当前月的第一天
	 * <p>
	 * HH:mm:ss SS为零
	 * 
	 * @return
	 */
	public static Date firstDayOfMonth() {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
		cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
		cal.set(Calendar.MINUTE, 0);// m置零
		cal.set(Calendar.SECOND, 0);// s置零
		cal.set(Calendar.MILLISECOND, 0);// S置零
		return cal.getTime();
	}
	
	private static Date weekDay(int week) {
		Calendar cal = calendar();
		cal.set(Calendar.DAY_OF_WEEK, week);
		return cal.getTime();
	}

	/**
	 * 获得周五日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date friday() {
		return weekDay(Calendar.FRIDAY);
	}

	/**
	 * 获得周六日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date saturday() {
		return weekDay(Calendar.SATURDAY);
	}

	/**
	 * 获得周日日期
	 * <p>
	 * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
	 * 
	 * @return
	 */
	public static Date sunday() {
		return weekDay(Calendar.SUNDAY);
	}

	/**
	 * 将字符串日期时间转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetime(String datetime) throws ParseException {
		return datetimeFormat.parse(datetime);
	}

	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 日期时间格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}
	
	/**
	 * 将字符串日期转换成java.util.Date类型
	 * <p>
	 * 时间格式 HH:mm:ss
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String time) throws ParseException {
		return timeFormat.parse(time);
	}
	/**
	 * 根据自定义pattern将字符串日期转换成java.util.Date类型
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDatetime(String datetime, String pattern)
	throws ParseException {
		SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
		format.applyPattern(pattern);
		return format.parse(datetime);
	}
	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
			.getTime());
		return dayBefore;
	}
	/**
	 * 获得指定日期的前n天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay,int differ) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - differ);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
			.getTime());
		return dayBefore;
	}
	/**
	 * 获得指定日期的前n天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(Date specifiedDay,int differ) {
		Calendar c = Calendar.getInstance();
		Date date = specifiedDay;
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - differ);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
			.getTime());
		return dayBefore;
	}
	
	/**
	 * 获得指定日期的前N天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static Date getDSpecifiedDayBefore(Date specifiedDay,int differ) {
		Calendar c = Calendar.getInstance();
		Date date = specifiedDay;
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - differ);
		return c.getTime();
	}
	
	/**
	 * 获得指定时间的前几小时
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static Date getSpecifiedMinbefore(Date specifiedDay,int btime) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		 int min = c.get(Calendar.MINUTE);
		 min -= btime;
		 c.set(Calendar.MINUTE, min);	 
		 return c.getTime();
	}
		
	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static Date getSpecifiedDayAfter(Date specifiedDay) {
		Calendar c = Calendar.getInstance();

		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		return c.getTime();
	}
	
	/**
	 * 获得指定日期的后n天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static Date getSpecifiedDayAfter(Date specifiedDay,int differ) {
		Calendar c = Calendar.getInstance();

		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + differ);

		return c.getTime();
	}
	/**
	 * 获得指定日期和后n天
	 *  
	 * @param specifiedDay
	 * @return
	 */
	public static List<String> getListDayAfter(Date specifiedDay,int differ,int isNext)
	{
		List<String> list = new ArrayList<String>();
		//Date specifiedDay = new Date();
		for (int i = 0; i < differ; i++) 
		{			
			if(isNext==1){
				list.add(datetimeFormat.format(specifiedDay));
			specifiedDay = getSpecifiedDayAfter(specifiedDay);
			}
			else
			{
				specifiedDay = getSpecifiedDayAfter(specifiedDay);
				list.add(datetimeFormat.format(specifiedDay));
			}
		}
		
		return list;
	}
	
	
	/**
	 * 获得指定日期和前n天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static List<String> getListDayBefore(Date specifiedDay,int differ)
	{
		List<String> list = new ArrayList<String>();
		Date bDay = new Date();
		for (int i = 0; i < differ; i++) {
			
			bDay =getDSpecifiedDayBefore(specifiedDay,differ-i);
			list.add(datetimeFormat.format(bDay));
		}
		return list;
	}
	
	/**
	 * 获得指定日期和后n天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static Map<String,String> getMapDayAfter(int differ)
	{
		Map<String,String> dateMap = new LinkedHashMap<String,String>();
		Date today = new Date();
		for (int i = 0; i < differ; i++) {
			//list.add(dateWeekFormat.format(today));
			dateMap.put(i+"", dateWeekFormat.format(today));
			//today = getSpecifiedDayAfter(today);
		}
		
		return dateMap;
	}
	
	
	/** 返回从今天开始的一周
	 * @return
	 */
	public static List<String> getWeekBeginWithToday()
	{
		List<String> list = new ArrayList();
		Date today = new Date();
		for(int i=0;i<7;i+=1)
		{
			list.add(weekFormat.format(today));
			today = getSpecifiedDayAfter(today);
		}
		return list;
	}

	/**	oDate-fDate 算出相差天數
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int dayDiffer(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * 得到当前周的开始日期和结束日期，以周一为起始，0是起始日期，1结束日期
	 * @return
	 * @author <p>Innate Solitary 于 2012-3-8 下午12:16:09</p>
	 */
	public static Date[] getStartAndEndDateForWeek() {
		Date[] dates = new Date[2];
		Calendar startCalendar = calendar();
		setCalendar(startCalendar, null, null, null, 0, 0, 0, 0);
		startCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Calendar endCalendar = calendar();
		endCalendar.add(endCalendar.get(Calendar.WEEK_OF_MONTH), 1);
		setCalendar(endCalendar, null, endCalendar.get(Calendar.MONDAY), null, 0, 0, 0, -1);
		dates[0] = startCalendar.getTime();
		dates[1] = endCalendar.getTime();
		return dates;
	}
	
	/**
	 * 得到当前季度的第一天和最后一天
	 * @return
	 */
	public static Date[] getStartAndEndDateForQuarter() {
		Date[] dates = new Date[2];
		Calendar cal = calendar();
		int curMonth = cal.get(Calendar.MONTH);
		int curQuarter = (curMonth + 1) / 3 + 1;
		int endMonth = curQuarter * 3 - 1;
		int startMonth = endMonth - 2;
		Calendar startCal = calendar();
		setCalendar(startCal, null, startMonth, 1, 0, 0, 0, 0);
		Calendar endCal = calendar();
		setCalendar(endCal, null, endMonth + 1, 1, 0, 0, 0, -1);
		dates[0] = startCal.getTime();
		dates[1] = endCal.getTime();
		return dates;
	}
	
	/**
	 * 得到今天的起始和结束实现
	 * @return
	 */
	public static Date[] getStartAndEndDateForToday() {
		Date[] dates = new Date[2];
		Calendar startCal = calendar();
		setCalendar(startCal, null, null, null, 0, 0, 0, 0);
		Calendar endCal = calendar();
		setCalendar(endCal, null, null, endCal.get(Calendar.DAY_OF_MONTH) + 1, 0 , 0, 0, -1);
		dates[0] = startCal.getTime();
		dates[1] = endCal.getTime();
		return dates;
	}
	
	/**
	 * cal不能为空，其他的值如果为空则不设置
	 * @param cal
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hourOfMonth
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @author <p>Innate Solitary 于 2012-3-29 上午9:44:21</p>
	 * @formatter:off
	 */
	public static void setCalendar(Calendar cal, Integer year, Integer month, Integer dayOfMonth, Integer hourOfDay, Integer minute, Integer second, Integer millisecond) {
		if(cal == null) {
			throw new IllegalArgumentException("参数 cal[Calendar] 不能为空");
		}
		
		if (year != null) {
	        cal.set(Calendar.YEAR, year);
        }
		if (month != null) {
	        cal.set(Calendar.MONTH, month);
        }
		if (dayOfMonth != null) {
	        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
		if (hourOfDay != null) {
	        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        }
		if (minute != null) {
	        cal.set(Calendar.MINUTE, minute);
        }
		if (second != null) {
	        cal.set(Calendar.SECOND, second);
        }
		if (millisecond != null) {
	        cal.set(Calendar.MILLISECOND, millisecond);
        }
	}
	
	/**两者返回较早的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getEarlierDate(Date date1,Date date2)
	{
		if(date1.before(date2))
		{
			return date1;
		}
		return date2;
	}
	
	/**两者返回较晚的日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getLaterDate(Date date1,Date date2)
	{
		if(date1.after(date2))
		{
			return date1;
		}
		return date2;
	}
	
	public static void main(String args[]){
		Date[] dates = getStartAndEndDateForWeek();
		System.out.println(DateUtils.formatDatetime(dates[0]));
		System.out.println(DateUtils.formatDatetime(dates[1]));
		
	}
	
	/**获取当前时间的起始时间
	 * 
	 * @return
	 */
	public static Date getStartTime(Date stime){  
		Calendar todayStart = Calendar.getInstance(); 
		todayStart.setTime(stime);
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
       // todayStart.set(Calendar.MILLISECOND, 0);  
        
        Long dd=todayStart.getTime().getTime();
        return todayStart.getTime(); 
    }  
      /**获取当前时间的结束时间
       * 
       * @return
       */
    public static Date getEndTime(Date etime){  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.setTime(etime);
        todayEnd.set(Calendar.HOUR, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        //todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTime();  
    } 
    
    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static long getDistanceTime(String str1, String str2)
    {
       // DateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = datetimeFormat.parse(str1);
            two = datetimeFormat.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
            if(hour>0)
            {
            	min=min+hour*60;
            }
        } 
        catch (ParseException e) 
          {
            e.printStackTrace();
          }
        return  min;
    }
}