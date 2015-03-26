import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;


public class ExportiCal {
	net.fortuna.ical4j.model.Calendar icsCalendar;
	
	public ExportiCal() {
		icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		icsCalendar.getProperties().add(CalScale.GREGORIAN);
		icsCalendar.getProperties().add(Version.VERSION_2_0);
	}
	
	public void addShow(String name, Date d) throws SocketException {
		
		
		
		
		
		// Create a TimeZone
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone("America/Mexico_City");
		VTimeZone tz = timezone.getVTimeZone();

		
		java.util.Calendar startDate = new GregorianCalendar();
		startDate.setTime(d);
		java.util.Calendar endDate = new GregorianCalendar();
		endDate.setTime(d);
		endDate.add(java.util.Calendar.HOUR_OF_DAY, 1);
		
		// Create the event
		String[] info = name.split(" - ");
		String eventName = info[0] + ": " + info[1];
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
