import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import twitter4j.*;
import java.awt.Desktop;
import java.net.URI;
import java.util.*;


public class exportTwitterGUI {
    static String authURL = "error";
    
    // Send a String to updateStatusGUI.
    // A browser window will pop up where you will authorize this app with your Twitter account
    // You will be provided with a PIN. Enter the PIN to the GUI popup window
    // The string you send to the function will be posted to twitter
    // String must be less that 140 characters
    public static void updateStatusGUI(String tweet) throws Exception{
        
        // Get twitter auth URL
        exportTweet workingTweet = new exportTweet();
        try{
            authURL = workingTweet.GUIauthUser1();
        }catch(Exception e){
            System.out.println("TWITTER GUI AUTH USER 1 ERROR");
        }
        
        
        // Open pin screen in browser
        Desktop d=Desktop.getDesktop();
        d.browse(new URI(authURL));
        
        // Prompt user for PIN and set
        final String pin = JOptionPane.showInputDialog(null,"Please enter activation pin here", null);
        workingTweet.setPin(pin);
        
        // Attempt to finish the authorization and send the tweet
        if(workingTweet.globPin.length() != 0){
            try{
                workingTweet.GUIauthUser2();
                //System.out.println("Auth Success");
                Status status = workingTweet.getTwitter().updateStatus(tweet);
                
            }catch(Exception e){
                System.out.println("TWITTER GUI AUTH USER 2 ERROR"+e);
                
                
            }
        }
        
        
    }
    
    // Tests the above functionality with a tweet you can enter by command line
    public static void main(String[] args) throws Exception {
        System.out.println("\n\n\nA browser window will open where you will authorize the app");
        System.out.println("You will then enter your authentication pin into the window that pops up");
        System.out.println("\nType your Tweet then press enter:");
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();
        updateStatusGUI(message);
    }
}
