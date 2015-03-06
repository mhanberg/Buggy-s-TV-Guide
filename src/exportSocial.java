import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Scanner;

public class exportSocial{
	int twitStatus;
	int faceStatus;
	String twitURL;
	String faceURL;

	// Takes int for network: 0 for Twitter: 1 for Facebook
	// Also takes message(must be less than 127 chars if Twitter)
    public exportSocial(int network, String message) throws Exception {
     	
    	// Share by Twitter
    	if(network == 0){
    		// Make sure message is of appropriate length
	    	if(message.length() <= 126){
		    	// Format URL
		    	twitURL = "http://twitter.com/intent/tweet?text=" + URLEncoder.encode(message, "UTF-8")+ "&via=BuggysTV"+"&related=BuggysTV";
		  
		    	// Post tweet
		        Desktop d=Desktop.getDesktop();
		        d.browse(new URI(twitURL));
		        twitStatus = 1;
	    	}else{
	    		// Too long
	    		System.out.println("Tweet is too long!");
	    		twitStatus = 0;
	    	}
	    	return;
    	}
	    	
    	
    // Share by Facebook
    	if(network == 1){
    	faceURL = "http://facebook.com/sharer/sharer.php?u=" + URLEncoder.encode("http://thetvdb.com/?tab=series&id="+message, "UTF-8");
            Desktop d=Desktop.getDesktop();
            d.browse(new URI(faceURL));
            faceStatus = 1;
	}
    	
    	
    
    }
    
    // Tests the above functionality with a tweet you can enter by command line
    public static void main(String[] args) throws Exception {
    	
    	
    	// What network? - Skip Facebook currently non working
    	
    	System.out.println("Enter 0 for Twitter or 1 for Facebook:");
        Scanner sc = new Scanner(System.in);
        int network = sc.nextInt();
        
        
        // What to post?
    	System.out.println("Please enter your message");
    	Scanner sc1 = new Scanner(System.in);
        String message = sc1.nextLine();
       
        // Post it to appropriate place
        if(network == 0){
        	// Send tweet
        	exportSocial twit = new exportSocial(0, message);
            if(twit.twitStatus == 1){
            	System.out.println("Successfully generated URL");
            	System.out.println(twit.twitURL);
            }else{
            	System.out.println("Tweet was too long.");
            }
        }else if(network == 1){
        	// Send facebook
        	exportSocial face = new exportSocial(1, message);
        	System.out.println(face.faceURL);
        }else{
        	System.out.println("Invalid network choice. Please retry.");
        }
        
       
    }
	
    
}

