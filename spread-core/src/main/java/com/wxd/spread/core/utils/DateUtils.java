package com.wxd.spread.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间处理工具类
 */
public class DateUtils {
    public final static String DATE = "yyyy-MM-dd";
    public final static String TIME = "HH:mm:ss";
    public final static String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public final static String MONTH = "yyyy-MM";
    public final static String DAY = "dd";
    public final static String YEARMONTH="yyyy/MM";
    public final static String TIMESTR = "yyMMddHH";
    public final static String DATE_CH = "yyyy年M月d日";
    public final static String MONTHDATE = "M月d日";

    public static Date formatDayEnd(Date date){

        Calendar dayEnd = Calendar.getInstance();
        dayEnd.setTime(date);
        dayEnd.set(Calendar.HOUR_OF_DAY, 23);
        dayEnd.set(Calendar.MINUTE, 59);
        dayEnd.set(Calendar.SECOND, 59);

        return dayEnd.getTime();
    }
    
    public static Date formatDayStart(Date date){

        Calendar dayEnd = Calendar.getInstance();
        dayEnd.setTime(date);
        dayEnd.set(Calendar.HOUR_OF_DAY, 0);
        dayEnd.set(Calendar.MINUTE, 0);
        dayEnd.set(Calendar.SECOND, 0);

        return dayEnd.getTime();
    }

    // 将 日期型对象 转换成 字符串型对象
    public static String format(Date date, String format) {
        if (null == date) return null;
        return (new SimpleDateFormat(format)).format(date);
    }

    // 将 字符串型对象 转换成 日期型对象
    public static Date format(String strDate, String format) {
        if (null == strDate || "".equals(strDate)) return null;
        try {
            return (new SimpleDateFormat(format)).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();

            return new Date();
        }
    }

    // 以当前日期为基准，得到偏移量为 offsets 天的日期
    public static Date dayOffsets(int offsets) {
        GregorianCalendar tempCalendar = getCurrentCalendarClone();
        tempCalendar.add(Calendar.DAY_OF_YEAR, offsets);
        return tempCalendar.getTime();
    }

    // 以当前日期为基准，得到偏移量为 offsets 小时的日期
    public static Date hourOffsets(int offsets) {
        GregorianCalendar tempCalendar = getCurrentCalendarClone();
        tempCalendar.add(Calendar.HOUR_OF_DAY, offsets);
        return tempCalendar.getTime();
    }
    
    // 以当前日期为基准，得到偏移量为 offsets 分钟的日期
    public static Date minuteOffsets(int offsets) {
        GregorianCalendar tempCalendar = getCurrentCalendarClone();
        tempCalendar.add(Calendar.MINUTE, offsets);
        return tempCalendar.getTime();
    }
    
    // 以当前日期为准，得到偏移量为offsets 小时的日期
    public static Date minuteOffsets(Date date, int offsets) {
        int year = Integer.parseInt(format(date, "yyyy"));
        int month = Integer.parseInt(format(date, "MM")) - 1;
        int day = Integer.parseInt(format(date, "dd"));
        int hour = Integer.parseInt(format(date, "HH"));
        int minute = Integer.parseInt(format(date, "mm"));
        int second = Integer.parseInt(format(date, "ss"));
        GregorianCalendar tempCalendar = new GregorianCalendar(year, month, day, hour, minute, second);
        tempCalendar.add(Calendar.MINUTE, offsets);
        return tempCalendar.getTime();
    }

    // 以当前日期为准，得到偏移量为offsets 小时的日期
    public static Date hourOffsets(Date date, int offsets) {
        int year = Integer.parseInt(format(date, "yyyy"));
        int month = Integer.parseInt(format(date, "MM")) - 1;
        int day = Integer.parseInt(format(date, "dd"));
        int hour = Integer.parseInt(format(date, "HH"));
        int minute = Integer.parseInt(format(date, "mm"));
        int second = Integer.parseInt(format(date, "ss"));
        GregorianCalendar tempCalendar = new GregorianCalendar(year, month, day, hour, minute, second);
        tempCalendar.add(Calendar.HOUR_OF_DAY, offsets);
        return tempCalendar.getTime();
    }

    // 以给定日期为基准，得到偏移量为 offsets 天的日期
    public static Date dayOffsets(Date date, int offsets) {
        int year = Integer.parseInt(format(date, "yyyy"));
        int month = Integer.parseInt(format(date, "MM")) - 1;
        int day = Integer.parseInt(format(date, "dd"));
        GregorianCalendar tempCalendar = new GregorianCalendar(year, month, day);
        tempCalendar.add(Calendar.DAY_OF_YEAR, offsets);
        return tempCalendar.getTime();
    }

    // 以当前日期为基准，得到偏移量为 offsets 月的第一天日期
    public static Date monthOffsets(int offsets) {
        GregorianCalendar tempCalendar = getCurrentCalendarClone();
        tempCalendar.add(Calendar.MONTH, offsets);
        return tempCalendar.getTime();
    }
    
    // 已给定日期为基准，得到偏移量为 offsets 月的日期
    public static Date monthOffsets(Date date, int offsets) {
    	int year = Integer.parseInt(format(date, "yyyy"));
    	int month = Integer.parseInt(format(date, "MM"))-1;
    	int day = Integer.parseInt(format(date, "dd"));
    	GregorianCalendar tempCalendar = new GregorianCalendar(year, month,day);
    	tempCalendar.add(Calendar.MONTH, offsets);
    	return tempCalendar.getTime();
    }

    // 得到当前周的所有日期
    public static Date[] getCurrentWeekDates() {
        GregorianCalendar tempCalendar = getCurrentCalendarClone();
        Date[] weeks = new Date[7];
        int dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);

        weeks[dayOfWeek - 1] = tempCalendar.getTime();
        for (int i = 1; i < dayOfWeek; i++) {
            weeks[i - 1] = dayOffsets(i - dayOfWeek);
        }
        for (int i = dayOfWeek + 1; i < 8; i++) {
            weeks[i - 1] = dayOffsets(i - dayOfWeek);
        }

        return weeks;
    }

    // 得到指定月份的第一天
    public static Date firstDayOfMonth(Date date) {
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        return gcLast.getTime();
    }
    public static Date firstDayOfMonth(String date) {
        return firstDayOfMonth(format(date, DATE));
    }

    public static Date lastDayOfMonth(Date date) {
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        gcLast.roll(Calendar.DAY_OF_MONTH, -1);
        return gcLast.getTime();
    }
    public static Date lastDayOfMonth(String date) {
        return lastDayOfMonth(format(date, DATE));
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format(date, DATE));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private static GregorianCalendar getCurrentCalendarClone() {
        return (new GregorianCalendar());
    }
    

    public static Date formatDate(long time, String format) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		SimpleDateFormat df = new SimpleDateFormat(format);
		String newDate = df.format(calendar.getTime());
		Date date = df.parse(newDate);
		return date;
	}

    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间  为空(null)则为当前时间
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1,String date2,int stype){
        int n = 0;

        String[] u = {"天","月","年"};
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";

        date2 = date2==null?format(new Date(),DATE):date2;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        //List list = new ArrayList();
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来
            n++;
            if(stype==1){
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            }
            else{
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1
            }
        }

        n = n-1;

        if(stype==1){
            n = (int)n%12;
        }

        if(stype==2){
            n = (int)n/365;
        }

        System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);
        return n;
    }

    public static void main(String[] args) {
    	System.out.println(System.currentTimeMillis() / 1000);
//        System.out.println(formatDayEnd(new Date()));
//        int ss = compareDate("2015-06-01",null,1);
//        System.out.print(ss);
    	
    	/*int betweenDays = betweenDays(new Date(),format("2016-07-10",DATE));
    	System.out.println(betweenDays);
    	
        System.out.println(DateUtils.format(new Date(), "yyyy-M-d"));*/
//        System.out.println(getDayOfWeek("2014-01-26"));

        //        System.out.println(DateUtils.format("2011/1/1", "yyyy/MM/dd"));
//        getCurrentWeekDates();
//        System.out.println(format(firstDayOfMonth("2013-05-04"), DATE));
//        System.out.println(format(lastDayOfMonth("2013-02-04"), DATE));

//        Timestamp d1 = new Timestamp(DateUtils.format("2013-01-01", DATE).getTime());
//        Timestamp d2 = new Timestamp(DateUtils.format("2013-01-01", DATE).getTime());

//        System.out.println(d1.equals(d2));

//        Integer i1 = 1234567;
//        Integer i2 = 1234567;
//        System.out.println(i1 == i2);
//        System.out.println(i1.equals(i2));
    }
    
    /** 格式化Date MM月dd日 HH:mm */
	public static String getDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm",
				Locale.CHINA);
		return sdf.format(date);
	}

	public static Date getTodayStart(){
		Calendar currentDate = new GregorianCalendar();   
		  
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);

		return (Date)currentDate.getTime().clone();
	}
	
	/**
	 * 距今多久，比如42分钟以前
	 * 。PS：System.out.println("wisely--"+DateUtils.getTimeIntervalCurrent(new
	 * Date(System.currentTimeMillis()-8*12*30*24*60*60*1000)));
	 * 这句代码打印的是个负值，Integer的最大值为21亿多
	 * ，核算成时间的话，应该不到25天的总毫秒数，所以只要超过25天的时间，都要强转为long类型
	 */
	public static String getTimeIntervalCurrent(Date date) {
		if(date == null) return "";
		long interval = System.currentTimeMillis() - date.getTime();
		long time;
		time = new Double(Math.ceil((double)interval / (1 * 1000))).longValue();
		if (time == 0) {
			return "刚刚";
		}
		if (time < 60) {
			return time + "秒前";
		}
		time = new Double(Math.ceil((double)interval / (60 * 1000))).longValue();
		if (time < 60) {
			return time + "分钟前";
		}
		time = new Double(Math.ceil((double)interval / (60 * 60 * 1000))).longValue();
		if (time < 24) {
			return time + "小时前";
		}

		time = new Double(Math.ceil((double)interval / (24 * 60 * 60 * 1000))).longValue();
		if (time < 30) {
			return time + "天前";
		}

		time = new Double(Math.ceil((double)interval / ((long)30 * 24 * 60 * 60 * 1000))).longValue();
		if (time < 12) {
			return time + "月前";
		}

		time = new Double(Math.ceil((double)interval / ((long)12 * 30 * 24 * 60 * 60 * 1000))).longValue();
		return time + "年前";
	}
	
	/**
	 * 距今多久，比如42分钟以前
	 * 1. 1小时内按分钟展示，如23分钟以前
	 * 2. 24小时内按小时展示，如23小时以前
	 * 3. 24小时以外按天展示，如3天前
	 * 4. 3天外按日期计算，如16-06-06
	 * 。PS：System.out.println("wisely--"+DateUtils.getTimeIntervalCurrent(new
	 * Date(System.currentTimeMillis()-8*12*30*24*60*60*1000)));
	 * 这句代码打印的是个负值，Integer的最大值为21亿多
	 * ，核算成时间的话，应该不到25天的总毫秒数，所以只要超过25天的时间，都要强转为long类型
	 */
	public static String getTimeIntervalCurrent_v2(Date date) {
		if(date == null) return "";
		long interval = System.currentTimeMillis() - date.getTime();
		long time;
		time = new Double(Math.ceil((double)interval / (1 * 1000))).longValue();
		if (time == 0) {
			return "刚刚";
		}
		if (time < 60) {
			return time + "秒前";
		}
		time = new Double(Math.ceil((double)interval / (60 * 1000))).longValue();
		if (time < 60) {
			return time + "分钟前";
		}
		time = new Double(Math.ceil((double)interval / (60 * 60 * 1000))).longValue();
		if (time < 24) {
			return time + "小时前";
		}

		time = new Double(Math.ceil((double)interval / (24 * 60 * 60 * 1000))).longValue();
		if (time < 4) {
			return time + "天前";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		return format.format(date);
	}
	
	public static long between(Date date1,Date date2){
		return date1.getTime()-date2.getTime();
	}
	
	/**
	 * 将微信时间戳转换为正常的日期
	 * @param timestamp
	 * @return
	 */
	public static Date wechatTimestamp2Date(long timestamp) {
		return new Date(timestamp * 1000);
	}
	
	/**
	 * 将微信时间戳转换为正常的日期
	 * @param date	空则是当前时间
	 * @return
	 */
	public static long date2wechatTimestamp(Date date) {
		if (date != null) {
			return date.getTime() / 1000;
		}
		return System.currentTimeMillis() / 1000;
	}
}
