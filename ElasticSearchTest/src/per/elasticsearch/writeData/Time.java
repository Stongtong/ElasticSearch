package per.elasticsearch.writeData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

	public static String getRandomInTime() throws ParseException{
		Date startDate = new Date();
		Date endDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
		startDate = format.parse("2000.06.05 21:17:57 CST");
		endDate = format.parse("2020.06.05 21:17:57 CST");
		long timeSpace = startDate.getTime() + 
				(long)(Math.random() * (endDate.getTime() - startDate.getTime()));
		String time = format.format(timeSpace);
		
		return time;
	}
	
	public static String getRandomOutTime() throws ParseException{
		Date startDate = new Date();
		Date endDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
		startDate = format.parse("2020.06.05 21:17:57 CST");
		endDate = format.parse("2050.06.05 21:17:57 CST");
		long timeSpace = startDate.getTime() + 
				(long)(Math.random() * (endDate.getTime() - startDate.getTime()));
		String time = format.format(timeSpace);
		
		return time;
	}

}
