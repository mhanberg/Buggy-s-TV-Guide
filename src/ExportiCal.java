import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;


public class ExportiCal {
	net.fortuna.ical4j.model.Calendar icsCalendar;
	
	public ExportiCal() {
		icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		icsCalendar.getProperties().add(CalScale.GREGORIAN);
	}
	
	public void addShow(String air) throws SocketException {
		
		String[] info = air.split(" - ");
		String[] dateInfo = info[2].split(" ");
		if (dateInfo[2].indexOf("0") == 0) {
			dateInfo[2] = dateInfo[2].replaceFirst("0", "");
		}
		String[] timeInfo = info[3].split(" "); //only need to use index 1
		String[] hourMinuteInfo = timeInfo[0].split(":");
	
		
		System.out.println(air);
		
		
		
		// Create a TimeZone
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone("America/Mexico_City");
		VTimeZone tz = timezone.getVTimeZone();

		java.util.Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(timezone);
		
		if (dateInfo[1] == "Jan") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
		} else if (dateInfo[1] == "Feb") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.FEBRUARY);
		} else if (dateInfo[1] == "Mar") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
		} else if (dateInfo[1] == "Apr") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		} else if (dateInfo[1] == "May") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.MAY);
		} else if (dateInfo[1] == "Jun") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.JUNE);
		} else if (dateInfo[1] == "Jul") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.JULY);
		} else if (dateInfo[1] == "Aug") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.AUGUST);
		} else if (dateInfo[1] == "Sep") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.SEPTEMBER);
		} else if (dateInfo[1] == "Oct") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
		} else if (dateInfo[1] == "Nov") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
		} else if (dateInfo[1] == "Dec") {
			startDate.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
		} 
		startDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dateInfo[2]));
		startDate.set(java.util.Calendar.YEAR, Integer.parseInt(dateInfo[4]));
		startDate.set(java.util.Calendar.HOUR, Integer.parseInt(hourMinuteInfo[0]));
		startDate.set(java.util.Calendar.MINUTE, Integer.parseInt(hourMinuteInfo[1]));
		startDate.set(java.util.Calendar.SECOND, 0);
		if (timeInfo[1] == "AM") {
			startDate.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
		} else {
			startDate.set(java.util.Calendar.AM_PM, java.util.Calendar.PM);
		}

		java.util.Calendar endDate = new GregorianCalendar();
		endDate.setTimeZone(timezone);
		
		if (dateInfo[1] == "Jan") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
		} else if (dateInfo[1] == "Feb") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.FEBRUARY);
		} else if (dateInfo[1] == "Mar") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
		} else if (dateInfo[1] == "Apr") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		} else if (dateInfo[1] == "May") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.MAY);
		} else if (dateInfo[1] == "Jun") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.JUNE);
		} else if (dateInfo[1] == "Jul") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.JULY);
		} else if (dateInfo[1] == "Aug") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.AUGUST);
		} else if (dateInfo[1] == "Sep") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.SEPTEMBER);
		} else if (dateInfo[1] == "Oct") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.OCTOBER);
		} else if (dateInfo[1] == "Nov") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.NOVEMBER);
		} else if (dateInfo[1] == "Dec") {
			endDate.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
		} 
		endDate.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dateInfo[2]));
		endDate.set(java.util.Calendar.YEAR, Integer.parseInt(dateInfo[4]));
		if (hourMinuteInfo[0] == "12") {
			endDate.set(java.util.Calendar.HOUR, 1);
		} else if ( hourMinuteInfo[0] == "11" ) {
			if (timeInfo[1] == "AM") {
				endDate.set(java.util.Calendar.AM_PM, java.util.Calendar.PM);
			} else {
				endDate.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
			}
		} else {
			endDate.set(java.util.Calendar.HOUR, (Integer.parseInt(hourMinuteInfo[0]) + 1));
			endDate.set(java.util.Calendar.MINUTE, Integer.parseInt(hourMinuteInfo[1]));
			endDate.set(java.util.Calendar.SECOND, 0);
			if (timeInfo[1] == "AM") {
				endDate.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
			} else {
				endDate.set(java.util.Calendar.AM_PM, java.util.Calendar.PM);
			}
		}
		
		// Create the event
		String eventName = info[0] + " - " + info[1];
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		VEvent meeting = new VEvent(start, end, eventName);

		// add timezone info..
		meeting.getProperties().add(tz.getTimeZoneId());

		// generate unique identifier..
		UidGenerator ug = new UidGenerator("uidGen");
		Uid uid = ug.generateUid();
		meeting.getProperties().add(uid);

		// Add the event
		icsCalendar.getComponents().add(meeting);
	
	}
	
	public void saveiCalFile() throws IOException, ValidationException {
		FileOutputStream fout = new FileOutputStream("myshows.ics");

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(this.icsCalendar, fout);
	}
}
