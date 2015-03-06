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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

//import net.fortuna.ical4j.model.ValidationException;
import thetvdbapi.*;

public class MainForm
{
	private MainForm form = this;
	private MainFormActionListener listener = new MainFormActionListener();
	private TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
	
	private JFrame frame = new JFrame("Buggy's TV Guide");
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

		searchText.addActionListener(listener);
		searchButton.addActionListener(listener);
		mntmSave.addActionListener(listener);
		mntmLoad.addActionListener(listener);
		mntmExit.addActionListener(listener);
		mntmTwitter.addActionListener(listener);
		mntmFB.addActionListener(listener);
		//mntmiCal.addActionListener(listener);
		
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
	
	public class MainFormActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == searchText || e.getSource() == searchButton)
			{
				if(!searchText.getText().isEmpty())
				{
					TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
					try
					{
						Search searchResult = new Search(tvdb.searchSeries(searchText.getText(), "en"), form);
						
						searchResult.setVisible(true);
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
							BufferedReader br = new BufferedReader(new FileReader(file));
							shows.removeAll();
							times.removeAll();
							String line;
							boolean worked = true;
							
							while((line = br.readLine()) != null)
							{
								if(!addShow(line))
									worked = false;
							}
							
							br.close();
							
							if(!worked)
								JOptionPane.showMessageDialog(null, "There was a problem loading your file.");
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
		            	System.out.println("Tweet was too long.");
		            }
				}
				catch (Exception e1) { System.out.println("ERROR POSTING TO TWITTER"); }
			}
			else if(e.getSource() == mntmFB)
			{
				String show;
				TheTVDBApi tvDB1 = new TheTVDBApi("956FCE4039291BF8");
				int expids[] = new int[15];
				for(int i=0;i<shows.getItemCount();i++)
				{
					show = shows.getItems()[i];
					try{
						expids[i] = Integer.parseInt(tvDB1.searchSeries(show, "en").get(0).getId());
						}catch (Exception a){	
						}
				System.out.println(expids[i]);
				try {
					exportSocial face = new exportSocial(1, Integer.toString(expids[i]));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
				
			}
			else if(e.getSource() == mntmiCal)
			{
				/*ExportiCal newCalendar = new ExportiCal();
				
				for(int i = 0; i<shows.getItemCount();i++)
				{
					try
					{
						newCalendar.addShow(times.getItem(i));
					}
					catch (SocketException e1) { e1.printStackTrace(); }
				}
				
				try
				{
					newCalendar.saveiCalFile();
				}
				catch (IOException | ValidationException e1) { e1.printStackTrace(); }*/
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
	
	public boolean addShow(String showName)
	{
		if(showAlreadyInList(showName))
			return false;
		
		String addShow;
		try
		{
			NextEp check = new NextEp();
			addShow = (String)check.nextEp(showName);
			times.add(addShow);
			shows.add(showName);
		}
		catch(Exception e) { return false; }
		
		return true;
	}
	
	public boolean removeShow(String showName)
	{
		if(!showAlreadyInList(showName))
			return false;
		
		int index;
		for(index = 0;index<shows.getItems().length;index++)
			if(shows.getItems()[index].equals(showName))
				break;
		
		shows.remove(index);
		times.remove(index);
		
		return true;
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
