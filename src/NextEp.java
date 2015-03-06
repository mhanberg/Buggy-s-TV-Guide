import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import  thetvdbapi.*;
import thetvdbapi.model.*;

public class NextEp {
	
	public NextEp() {
		
	}
	
	public Object nextEp(String show) {
		TheTVDBApi tvDB = new TheTVDBApi("956FCE4039291BF8");
		List<Series> results = new ArrayList<Series>();
		try{
		results = tvDB.searchSeries(show, "en");
		}catch (Exception a){
			
		}
		Series series = results.get(0);
		
		
		String id = series.getId();
		
		Series fullDetails = null;
		try {
			fullDetails = tvDB.getSeries(id, "en");
		} catch (TvDbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		List<Episode> episodes = new ArrayList<Episode>();
		try {
			episodes = tvDB.getAllEpisodes(id, "en");
		} catch (Exception a) {
			
		}
		
		int size = episodes.size();
		String retString = "";
		
		for (int i = 0; i < size; i++) {
			Episode episodeData = episodes.get(i);
			
			String airDate = episodeData.getFirstAired();
			DateFormat format = new SimpleDateFormat("yyyy-M-d");
			Date date = null;
			try {
				if (airDate.equals("")) {
					continue;
				}
				date = format.parse(airDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			Date currentDate = new Date();
			String curCheck = currentDate.toString();
			curCheck = curCheck.replaceAll("00:00:00 ", "");
			String concatDate = date.toString();
			concatDate = concatDate.replaceAll("00:00:00 ", "");


			if (date.after(currentDate)) {
				String newString = (show + " - " + episodeData.getEpisodeName() + " - " + concatDate + " - " + fullDetails.getAirsTime());
				retString += newString + " | \n";
			} else if (curCheck.equals(concatDate) == true) {
				String newString = (show + " - " + episodeData.getEpisodeName() + " - " + concatDate + " - " + fullDetails.getAirsTime());
				retString += newString + " | \n";
			} else {

			}
		
		}
		
		if (retString.equals("") == true) {
			return "No upcoming episode";
		} else {
			retString = retString.substring(0, retString.length() - 4);
			return retString;
		}
		/*if haven't returned yet, return a string that says no upcoming episode */
	}
	
}


