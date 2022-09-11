package com.genesis.genesisapi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FindToFromDate {
	public static Date StringToDate(String s){

	    Date result = null;
	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        result  = dateFormat.parse(s);
	    }

	    catch(ParseException e){
	        e.printStackTrace();

	    }
	    return result ;
	}
    public static Date StringToDate1(String s){

	    Date result = null;
	    try{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        result  = dateFormat.parse(s);
	    }

	    catch(ParseException e){
	        e.printStackTrace();

	    }
	    return result ;
	}
	public static Date getNextMonth(Date now) {
		//Date now = new Date();    
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(now);    
		myCal.add(Calendar.MONTH, +1);    
		now = myCal.getTime();
		//System.out.println("Bef sending data of getNextMonth: "+now);
		return now;
	}
	
	public static List<String> getToFromDate(String str) {
		//SimpleDateFormat sdfmt1 = new SimpleDateFormat("dd/MM/yy");
				Date date = StringToDate(str);
				Date myDate = date;
				System.out.println("Storage Start Date: "+new SimpleDateFormat("dd-MM-yyyy").format(myDate));
				
				Date currentDate = new Date();
				//System.out.println("Current Date: "+new SimpleDateFormat("dd-MM-yyyy").format(currentDate));
				
				Date nextMonthDate = getNextMonth(new Date());
				//System.out.println("Next Month Date:"+new SimpleDateFormat("dd-MM-yyyy").format(nextMonthDate));
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(nextMonthDate);
				int month = cal.get(Calendar.MONTH);
				
				cal.setTime(date);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				
				cal.setTime(nextMonthDate);
				int year = cal.get(Calendar.YEAR);
				
				int nextMonth = month + 1;//because January is 0
				//System.out.println("Next Month:"+nextMonth);
				int startDay = day;
				//System.out.println("Day of next month:"+startDay);
				//System.out.println("Year:"+year);
				
				String newFromDate = String.valueOf(startDay)+"-"+String.valueOf(nextMonth)+"-"+String.valueOf(year);
				System.out.println("New From Date: "+newFromDate);
				
				//For To Date
				Date date1 = StringToDate1(newFromDate);
				Date afterAddMonth = getNextMonth(date1);
				//System.out.println("After adding month in fromDate:"+new SimpleDateFormat("dd-MM-yyyy").format(afterAddMonth));
				Date ToDate = new Date(afterAddMonth.getTime() - 1 * 24 * 3600 * 1000L );
				System.out.println("New To Date:"+new SimpleDateFormat("dd-MM-yyyy").format(ToDate));
				String newToDate = new SimpleDateFormat("dd-MM-yyyy").format(ToDate);
				List<String> strList = new ArrayList<String>();
				strList.add(String.valueOf(newFromDate));
				strList.add(String.valueOf(newToDate));
				return strList;
				
	}
}
