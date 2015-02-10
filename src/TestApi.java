import java.util.*;
import  thetvdbapi.*;
import thetvdbapi.model.*;

public class TestApi {
	public static void main(String[] args)
	{
		TheTVDBApi tvDB = new TheTVDBApi("956FCE4039291BF8");
		List<Series> results = new ArrayList<Series>();
		try{
		results = tvDB.searchSeries("Lost", "en");
		}catch (Exception a){
			
		}
		Series lost = results.get(0);
		System.out.println(lost.getFirstAired());
	}
}
