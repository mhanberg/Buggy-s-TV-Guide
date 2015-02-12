import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import thetvdbapi.*;

public class MainForm
{
	private JFrame frame;
	private JTextField searchText;
	private List times;
	private List shows;

	public MainForm()
	{
		final MainForm form = this;
		frame = new JFrame("Buggy's TV Guide");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(335, 30, 89, 23);
		frame.getContentPane().add(searchButton);
		
		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent e)
			{
				if(!searchText.getText().isEmpty())
				{
					TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
					try
					{
						Search searchResult = new Search(tvdb.searchSeries(searchText.getText(), "en"), form);
						
						searchResult.setVisible(true);
					}
					catch (Exception asdf) { JOptionPane.showMessageDialog(null, "Error"); }
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter a search phrase");
				}
			}
		});
		
		searchText = new JTextField();
		searchText.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!searchText.getText().isEmpty())
				{
					TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
					try
					{
						Search searchResult = new Search(tvdb.searchSeries(searchText.getText(), "en"), form);
						searchResult.setVisible(true);
					}
					catch (Exception asdf) { JOptionPane.showMessageDialog(null, "Error");  }
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter a search phrase");
				}
			}
		});
		
		searchText.setBounds(10, 31, 315, 20);
		frame.getContentPane().add(searchText);
		searchText.setColumns(10);
		
		shows = new List();
		shows.setBounds(10, 57, 200, 194);
		frame.getContentPane().add(shows);
		
		times = new List();
		times.setBounds(224, 57, 200, 194);
		frame.getContentPane().add(times);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
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
						catch (IOException e2) { e2.printStackTrace(); }
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
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
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
							String line;
							while((line = br.readLine()) != null)
							{
								addShow(line);
							}
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
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				form.frame.dispatchEvent(new WindowEvent(form.frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnExport = new JMenu("Export To...");
		menuBar.add(mnExport);
		
		JMenuItem mntmTwitter = new JMenuItem("Twitter");
		mntmTwitter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//Mitch: Make it export to twitter here
				String twitString;
				twitString = "I added ";
				
				for(int i=0;i<shows.getItemCount();i++){
					if(shows.getItemCount() == 1){
						twitString = twitString + shows.getItems()[i] + " ";
						break;
					}else if(shows.getItemCount() == 2){
						twitString = twitString + shows.getItems()[0] + " and " + shows.getItems()[1] + " ";
						break;
					}else if( i-1 == shows.getItemCount()){
						twitString = twitString + "and "+ shows.getItems()[i] + " ";
						break;
					}else{
						twitString = twitString + shows.getItems()[i] + ", ";
					}
				}
				twitString = twitString + "to Buggy's.";
				
				try {
					exportSocial twit = new exportSocial(0, twitString);
		            if(twit.twitStatus == 1){
		            	System.out.println("Successfully generated URL");
		            	System.out.println(twit.twitURL);
		            }else{
		            	System.out.println("Tweet was too long.");
		            }
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("ERROR POSTING TO TWITTER");
				}

			}
		});
		mnExport.add(mntmTwitter);
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
		String addShow;
		NextEp check = new NextEp();
		addShow = (String)check.nextEp(showName);
		times.add(addShow);
		shows.add(showName);
		Arrays.sort(shows.getItems());
	}
	
	public void removeShow(String showName)
	{
		int index;
		for(index = 0;index<shows.getItems().length;index++)
			if(shows.getItems()[index].equals(showName))
				break;
		
		shows.remove(index);
		times.remove(index);
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
