import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import thetvdbapi.*;
import thetvdbapi.model.*;

public class NextEp
{
	public static HashMap<String, Date> getEpisodeList(String show)
	{
		TheTVDBApi tvDB = new TheTVDBApi("956FCE4039291BF8");
		List<Series> results = new ArrayList<Series>();
		HashMap<String, Date> retEpisodes = new HashMap<String, Date>();
		
		try
		{
			results = tvDB.searchSeries(show, "en");
		}
		catch (Exception a) { }
		
		if(results.isEmpty())
			return null;
		
		Series series = results.get(0);
		
		if(!series.getSeriesName().equals(show))
			return null;
		
		String id = series.getId();
		Series fullDetails = null;
		
		try
		{
			fullDetails = tvDB.getSeries(id, "en");
		}
		catch (TvDbException e1) { e1.printStackTrace(); }
		
		List<Episode> episodes = new ArrayList<Episode>();
		
		try
		{
			episodes = tvDB.getAllEpisodes(id, "en");
		}
		catch (Exception a) { }
		
		
		for (int i = 0; i < episodes.size(); i++)
		{
			Episode episodeData = episodes.get(i);
			String airDate = episodeData.getFirstAired();
			DateFormat format = new SimpleDateFormat("yyyy-M-d-hh:mm a");
			Date date = null;
			
			try
			{
				if (airDate.equals(""))
					continue;
				
				date = format.parse(airDate + "-" + fullDetails.getAirsTime());
			}
			catch (ParseException e)
			{
				try
				{
					date = format.parse(airDate);
				}
				catch (ParseException e1) { }
			}
			
			Date currentDate = new Date();
			String curCheck = currentDate.toString();
			curCheck = curCheck.replaceAll("00:00:00 ", "");
			String concatDate = "";
			if(date != null)
				concatDate = date.toString().substring(0, date.toString().indexOf(':')-2);
			concatDate = concatDate.replaceAll("00:00:00 ", "");

			if (date != null && date.compareTo(currentDate) >= 0)
				retEpisodes.put(show + " - " + episodeData.getEpisodeName() + " - " + concatDate + " - " + fullDetails.getAirsTime(), date);
		}
		
		return retEpisodes;
	}
}
