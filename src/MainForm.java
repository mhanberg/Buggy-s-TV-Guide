import java.awt.EventQueue;
import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.fortuna.ical4j.model.ValidationException;
import thetvdbapi.*;
import thetvdbapi.model.Series;

public class MainForm implements ActionListener
{
	private MainForm form = this;
	private TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
	private HashMap<String, ArrayList<String>> series = new HashMap<String, ArrayList<String>>();
	private HashMap<String, Date> dates = new HashMap<String, Date>();
	private ArrayList<String> sortedDates = new ArrayList<String>();
	
	JFrame frame = new JFrame("Buggy's TV Guide");
	private JTextField searchText = new JTextField();
	private JButton searchButton = new JButton("Search");
	private List shows = new List();
	private List times = new List();
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmSave = new JMenuItem("Save");
	private JMenuItem mntmLoad = new JMenuItem("Load");
	private JMenuItem mntmExit = new JMenuItem("Exit");
	private JMenu mnExport = new JMenu("Export To...");
	private JMenuItem mntmTwitter = new JMenuItem("Twitter");
	private JMenuItem mntmFB = new JMenuItem("Facebook");
	private JMenuItem mntmiCal = new JMenuItem("iCal");

	public MainForm()
	{
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				resizeComponents(frame.getWidth(), frame.getHeight());
			}
		});
		
		frame.getContentPane().add(searchText);
		frame.getContentPane().add(searchButton);
		frame.getContentPane().add(shows);
		frame.getContentPane().add(times);
		frame.getContentPane().add(menuBar);

		searchText.addActionListener(this);
		searchButton.addActionListener(this);
		shows.addActionListener(this);
		times.addActionListener(this);
		mntmSave.addActionListener(this);
		mntmLoad.addActionListener(this);
		mntmExit.addActionListener(this);
		mntmTwitter.addActionListener(this);
		mntmFB.addActionListener(this);
		mntmiCal.addActionListener(this);
		
		menuBar.add(mnFile);
		menuBar.add(mnExport);
		
		mnFile.add(mntmSave);
		mnFile.add(mntmLoad);
		mnFile.add(mntmExit);
		
		mnExport.add(mntmTwitter);
		mnExport.add(mntmFB);
		mnExport.add(mntmiCal);
		
		resizeComponents(frame.getWidth(), frame.getHeight());
	}
	
	private void resizeComponents(int width, int height)
	{
		menuBar.setBounds(0, 0, width, 20);
		searchText.setBounds(10, 30, width - 120, 20);
		searchButton.setBounds(width - 105, 30, 80, 20);
		shows.setBounds(10, 60, width / 2 - 25, height - 105);
		times.setBounds(width / 2, 60, width / 2 - 25, height - 105);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == searchText || e.getSource() == searchButton)
		{
			if(!searchText.getText().isEmpty())
			{
				try
				{
					java.util.List<Series> results = tvdb.searchSeries(searchText.getText(), "en");
					
					if(results.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "No Results");
					}
					else if(results.size() == 1)
					{
						try
						{
							Popup p = new Popup(results.get(0), form);
							p.setVisible(true);
						}
						catch(Exception popupException) { JOptionPane.showMessageDialog(null, "Connection to server failed. Check your internet connection."); }
					}
					else
					{
						Search searchResult = new Search(results, form);
						searchResult.setVisible(true);
					}
				}
				catch (Exception asdf)
				{ 
					JOptionPane.showMessageDialog(null, "Database could not be reached. Please check your internet connection and try again."); 
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter a search phrase");
			}
		}
		else if(e.getSource() == shows)
		{
			try
			{
				Popup p = new Popup(tvdb.getSeries(tvdb.searchSeries(shows.getItem(shows.getSelectedIndex()), "en").get(0).getId(), "en"), form);
				p.setVisible(true);
			}
			catch(Exception popupException) { JOptionPane.showMessageDialog(null, "Connection to server failed. Check your internet connection."); }
		}
		else if(e.getSource() == times)
		{
			Iterator it = series.entrySet().iterator();
			
			while(it.hasNext())
			{
				Map.Entry pair = (Map.Entry)it.next();
				String str = (String)pair.getKey();
				ArrayList<String> eps = (ArrayList<String>)pair.getValue();
				
				if(eps.contains(times.getItem(times.getSelectedIndex())))
				{
					try
					{
						Popup p = new Popup(tvdb.getSeries(tvdb.searchSeries(str, "en").get(0).getId(), "en"), form);
						p.setVisible(true);
					}
					catch(Exception popupException) { JOptionPane.showMessageDialog(null, "Connection to server failed. Check your internet connection."); }
					
					break;
				}
			}
		}
		else if(e.getSource() == mntmSave)
		{
			JFileChooser fc = new JFileChooser();
			int val = fc.showSaveDialog(form.frame);
			
			if(val == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				if(!file.exists())
				{
					try
					{
						file.createNewFile();
					}
					catch (IOException e1) { e1.printStackTrace(); }
				}
				else
				{
					int result = JOptionPane.showConfirmDialog(fc, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
		            switch(result)
		            {
		                case JOptionPane.YES_OPTION:
		                	break;
		                default:
		                	return;
		            }
				}
				
				PrintWriter writer;
				try
				{
					writer = new PrintWriter(file);
					
					for(int i=0;i<shows.getItemCount();i++)
						writer.println(shows.getItem(i));
					
					writer.flush();
					writer.close();
				}
				catch (FileNotFoundException e1) {e1.printStackTrace();}
			}
		}
		else if(e.getSource() == mntmLoad)
		{
			JFileChooser fc = new JFileChooser();
			int val = fc.showOpenDialog(form.frame);
			
			if(val == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				if(file.exists())
				{
					try
					{
						shows.removeAll();
						times.removeAll();
						sortedDates.clear();
						dates.clear();
						
						BufferedReader br = new BufferedReader(new FileReader(file));
						String line;
						
						while((line = br.readLine()) != null)
							addShow(line);
						
						br.close();
					}
					catch (IOException e1) { e1.printStackTrace(); }
				}
				else
				{
					JOptionPane.showMessageDialog(form.frame, "File does not exist.");
				}
			}
		}
		else if(e.getSource() == mntmExit)
		{
			form.frame.dispatchEvent(new WindowEvent(form.frame, WindowEvent.WINDOW_CLOSING));
		}
		else if(e.getSource() == mntmTwitter)
		{
			if (shows.getItemCount() > 0) {
				try {
					tvdb.searchSeries(shows.getItems()[0], "en").get(0).getId();
					
					String twitString;
					twitString = "I added ";
				

					for(int i=0;i<shows.getItemCount();i++)
					{
						if(shows.getItemCount() == 1)
						{
							twitString = twitString + shows.getItems()[i] + " ";
							break;
						}
						else if(shows.getItemCount() == 2)
						{
							twitString = twitString + shows.getItems()[0] + " and " + shows.getItems()[1] + " ";
							break;
						}
						else if( i-1 == shows.getItemCount())
						{
							twitString = twitString + "and "+ shows.getItems()[i] + " ";
							break;
						}
						else
						{
							twitString = twitString + shows.getItems()[i] + ", ";
						}
					}
					twitString = twitString + "to Buggy's.";

					try
					{
						exportSocial twit = new exportSocial(0, twitString);

						if(twit.twitStatus == 1)
						{
							System.out.println("Successfully generated URL");
							System.out.println(twit.twitURL);
						}
						else
						{
							System.out.println("Tweet was too long, shortened tweet.");
							System.out.println("Successfully generated URL");
							System.out.println(twit.twitURL);
						}
					}
					catch (Exception e1) { JOptionPane.showMessageDialog(null, "Error posting to Twitter."); }
					
				} catch (Exception a) {
					JOptionPane.showMessageDialog(null, "Database could not be reached. Please check your internet connection and try again.");
				}
				
				

			} else {
				JOptionPane.showMessageDialog(frame, "You haven't added any shows yet!", "Twitter", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource() == mntmFB)
		{
			
			if (shows.getItemCount() > 0) {
				String show;
				int expids[] = new int[15];
				

				for(int i=0;i<shows.getItemCount();i++)
				{
					show = shows.getItems()[i];
					try
					{
						expids[i] = Integer.parseInt(tvdb.searchSeries(show, "en").get(0).getId());
					}
					catch (Exception a) { 
						JOptionPane.showMessageDialog(null, "Database could not be reached. Please check your internet connection and try again."); 
					}

					try
					{
						exportSocial face = new exportSocial(1, Integer.toString(expids[i]));
					}
					catch (Exception e1) { e1.printStackTrace(); }
				}
			} else {
				JOptionPane.showMessageDialog(frame, "You haven't added any shows yet!", "Facebook", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource() == mntmiCal)
		{
			ExportiCal newCalendar = new ExportiCal();
			
			Iterator it = dates.entrySet().iterator();
			
			if(it.hasNext()) {

				while(it.hasNext())
				{
					Map.Entry pair = (Map.Entry)it.next();
					String str = (String)pair.getKey();
					Date d = (Date)pair.getValue();

					try
					{
						newCalendar.addShow(str, d);
					}
					catch (SocketException e1) { e1.printStackTrace(); }
				}

				try
				{
					newCalendar.saveiCalFile(form);
				}
				catch (IOException | ValidationException e1) { 
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame,  ".ics file failed to output","iCal", JOptionPane.WARNING_MESSAGE);
					}
			
			} else {
				JOptionPane.showMessageDialog(frame, "You haven't added any shows yet!", "iCal", JOptionPane.WARNING_MESSAGE);
			}
		} 
			
	}
	
	public boolean showAlreadyInList(String showName)
	{
		for(int i=0;i<shows.getItemCount();i++)
			if(shows.getItems()[i].equals(showName))
				return true;
		
		return false;
	}
	
	public void addShow(String showName)
	{
		if(showAlreadyInList(showName))
			return;
		
		HashMap<String, Date> episodes = NextEp.getEpisodeList(showName);
		
		if(episodes == null)
		{
			JOptionPane.showMessageDialog(null, "Error adding show " + showName);
			return;
		}
		
		ArrayList<String> desc = new ArrayList<String>();
		shows.add(showName);
		
		Iterator it = episodes.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			String str = (String)pair.getKey();
			Date date = (Date)pair.getValue();
			
			desc.add(str);
			dates.put(str, date);
			
			int i = 0;
			while(i < sortedDates.size() && dates.get(sortedDates.get(i)).before(date))
				i++;
			
			sortedDates.add(i, str);
			times.add(str, i);
		}
		
		series.put(showName, desc);
	}
	
	public void removeShow(String showName)
	{
		Iterator it = series.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			String str = (String)pair.getKey();
			ArrayList<String> desc = (ArrayList<String>)pair.getValue();
			
			if(str.equals(showName))
			{
				for(String s : desc)
				{
					sortedDates.remove(s);
					times.remove(s);
					dates.remove(s);
				}
				break;
			}
		}
		
		series.remove(showName);
		shows.remove(showName);
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		});
	}
}
