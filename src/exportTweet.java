// Compile and run with
// "make twitter"

// Generates URL you enter into browser. Gives user a pin when authorized. Enter pin into program. Will post test tweet.

// Uses the twitter4j library see below for info
// http://twitter4j.org/en/

import twitter4j.*;
import twitter4j.auth.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class exportTweet {
    Twitter globalTwit;
    String message;
    RequestToken requestToken = null;
    AccessToken accessToken = null;
    String globPin;
    
    public Twitter getTwitter(){
        return globalTwit;
    }
    
    public void setPin(String incoming){
        globPin = incoming;
    }
    
    public exportTweet(){
        globalTwit = null;
        message = "";
        globPin = "";
    }
    
    // Auths a user to prepare for tweeting
    public static Twitter authUser() throws Exception{
        Twitter twitter = TwitterFactory.getSingleton();
        String shortKEY = "NULL";
        String longKEY = "NULL";
        if(shortKEY.equals("NULL") || longKEY.equals("NULL")){
            System.out.println("You must enter auth keys");
            return null;
        }
        twitter.setOAuthConsumer(shortKEY, longKEY);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        Scanner sc = new Scanner(System.in);
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = sc.next();
            try{
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Unable to get the access token.");
                }else{
                    te.printStackTrace();
                }
            }
        }
        return twitter;
    }
    
// Functions split and modified to work in graphica environment
    public String GUIauthUser1() throws Exception{
        globalTwit = TwitterFactory.getSingleton();
        String shortKEY = "NULL";
        String longKEY = "NULL";
        if(shortKEY.equals("NULL") || longKEY.equals("NULL")){
            System.out.println("***You must enter auth keys***");
            return null;
        }
        globalTwit.setOAuthConsumer(shortKEY, longKEY);
        this.requestToken = globalTwit.getOAuthRequestToken();
        
        
        return(requestToken.getAuthorizationURL());
    }
    
    public void GUIauthUser2() throws Exception{
        //System.out.println("PIN - "+globPin);
        try{
            accessToken = globalTwit.getOAuthAccessToken(requestToken, globPin);
        } catch (TwitterException te) {
            if(401 == te.getStatusCode()){
                System.out.println("Unable to get the access token.");
            }else{
                te.printStackTrace();
            }
        }
        
    }

    
    // Posts to twitter from command line - for debugging purposes
    public static int postTwitter(String updateText){
        Twitter t1;
        try{
            t1 = authUser();
        }catch(Exception e){
            System.out.println("Could not auth user");
            return -1;
        }
        
        try{
            Status status = t1.updateStatus(updateText);
            System.out.println("Successfully updated" + status.getUser().getScreenName() +"'s Twitter status");
            System.out.println("View at: http://twitter.com/" + status.getUser().getScreenName());
        }catch(Exception e){
            System.out.println("Could not auth user");
            return -1;
        }
        
        
        return 1;
        
        
    }
    
    // Test post twitter function for debugging
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a status to update");
        String update = sc.nextLine();
        postTwitter(update);
    }
    
}
