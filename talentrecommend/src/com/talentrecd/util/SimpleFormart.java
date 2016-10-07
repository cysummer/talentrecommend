package com.talentrecd.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SimpleFormart {
	
	public String DateToString(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	
	public String DayString(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public long DateToTime(Date date){
		return date.getTime();
	}
	
	public String timeToString(long time){
		Date date=new Date(time);
		return this.DateToString(date);
	}
	
	public Date addDate(Date date){
		Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_MONTH,15);
        return cd.getTime();
	}
}
