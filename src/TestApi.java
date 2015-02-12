import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import  thetvdbapi.*;
import thetvdbapi.model.*;

public class TestApi {
	public static void main(String[] args) throws TvDbException
	{
		
		
		NextEp check = new NextEp();
		System.out.println(check.nextEp("The Simpsons"));
		
		/*if haven't returned yet, return a string that says no upcoming episode */
	}
}
