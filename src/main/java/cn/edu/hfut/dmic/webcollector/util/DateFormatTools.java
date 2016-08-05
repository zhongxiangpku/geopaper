package cn.edu.hfut.dmic.webcollector.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateFormatTools {
	
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
		/** yyyyMMddHHmmss */
	    public static String WXFORMATE = "yyyyMMddHHmmss";
	    /** yyyy年MM月dd日 */
	    public static String WX_FORMATE_2 = "yyyy年MM月dd日";
	    /** yyyy年MM月dd日 HH:mm */
	    public static String WX_FORMATE_4 = "yyyy年MM月dd日 HH:mm";
	    /**  yyyy/MM/dd */
	    public static String WX_FORMATE_3 = "yyyy/MM/dd";
	    /**  yyyy年MM月dd日 HH:mm:ss */
	    public static String WX_FORMATE_5 = "yyyy年MM月dd日 HH:mm:ss";
	    /**  yyyyMMdd */
	    public static String WX_FORMATE_6 = "yyyyMMdd";
	    /**  yyyyMMdd */
	    public static String WX_FORMATE_7 = "yyyy-MM-dd HH:mm:ss";
	    /**  yyyyMMdd */
	    public static String WX_FORMATE_8 = "yyyy-MM-dd HH:mm";
	    /**  yyyyMMdd */
	    public static String WX_FORMATE_9 = "yyyy-MM-dd";
	    public static String getFormate(Date d, String regex) {
			String time = "";
			if(d == null){
				return time;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(regex);
			time = sdf.format(d);
			return time;
		}
		
	    public static Date getNewDateFromDate(Date date){
	    	String dstr = getFormate(date,WX_FORMATE_7);
	    	return getDateFromString(dstr);
	    }
		/**
		 * 
		 * @param dateStr
		 * @return
		 */
		public static Date getDateFromString(String dateStr){
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date date = new Date();
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		
		/**
		 * 
		 * @param dateStr
		 * @return
		 */
		public static Date getDateFromString(String dateStr,String regex){
			
			SimpleDateFormat sdf = new SimpleDateFormat(regex);
			
			Date date = new Date();
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		/**
		 * 数字转日期
		 * 
		 * @param str 待转换的字符
		 * 
		 * @return 转换后的日期数字
		 */
		public static Date parseLongStrToDate(String str)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.valueOf(str.substring(0,4)));
			cal.set(Calendar.MONTH, Integer.valueOf(str.substring(4,6))-1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(str.substring(6,8)));
			cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(str.substring(8,10)));
			cal.set(Calendar.MINUTE, Integer.valueOf(str.substring(10,12)));
			
			return cal.getTime();
		}
		
		
		/**
		 * 日期字符转数字
		 * 
		 * @param str 待转换的字符
		 * 
		 * @return 转换后的日期数字
		 */
		public static Long parseTimeToLong(String str)
		{
			Long rtn = 0L;
			if(str.length()>8)
			{
				try
				{
					//2015年01月13日11:40
					String yy,MM,dd,h,m;

					yy = str.substring(0,4);
					MM = str.substring(5,7);
					dd = str.substring(8,10);
					h = str.substring(11,13);
					m = str.substring(14,16);
					
					rtn = Long.parseLong(yy+MM+dd+h+m);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					return 0L;
				}
			}
			return rtn;
		}

}
