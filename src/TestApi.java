import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import  thetvdbapi.*;
import thetvdbapi.model.*;

public class TestApi {
	public static void main(String[] args) throws TvDbException
	{
		TheTVDBApi tvDB = new TheTVDBApi("956FCE4039291BF8");
		List<Series> results = new ArrayList<Series>();
		try{
		results = tvDB.searchSeries("Better Call Saul", "en");
		}catch (Exception a){
			
		}
		Series lost = results.get(0);
		System.out.println(lost.getFirstAired());
		System.out.println(lost.getSeriesName());
		System.out.println(lost.getNetwork());
		
		String id = lost.getId();
		System.out.println(id);
		
		Series fullDetails = tvDB.getSeries(id, "en");
		
		System.out.println(fullDetails.getAirsDayOfWeek());
		System.out.println(fullDetails.getAirsTime());
		
		List<Episode> episodes = new ArrayList<Episode>();
		try {
			episodes = tvDB.getAllEpisodes(id, "en");
		} catch (Exception a) {
			
		}
		
		Episode episodeData = episodes.get(3);
		System.out.println(episodeData.getFirstAired());
		String airDate = episodeData.getFirstAired();
		DateFormat format = new SimpleDateFormat("yyyy-M-d");
		Date date = null;
		try {
			date = format.parse(airDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		
		Date currentDate = new Date();
		//System.out.println(currentDate);
		
		if (date.after(currentDate)) {
			System.out.println("Episode is upcoming");
		} else if (date.before(currentDate)) {
			System.out.println("Episode is already out");
		} else {
			System.out.println("Episode comes out today");
		}
		
	}
}
