import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

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

import thetvdbapi.*;
import thetvdbapi.model.*;

import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				if(!searchText.getText().isEmpty())
				{
					TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
					Search searchResult;
					try{
						searchResult = new Search(tvdb.searchSeries(searchText.getText(), "en"), form);
					} catch (Exception asdf){
						searchResult = new Search(form);
					}
					searchResult.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter a search phrase");
				}
			}
		});
		
		searchText = new JTextField();
		searchText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!searchText.getText().isEmpty())
				{
					TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
					Search searchResult;
					try{
						searchResult = new Search(tvdb.searchSeries(searchText.getText(), "en"), form);
					} catch (Exception asdf){
						searchResult = new Search(form);
					}
					searchResult.setVisible(true);
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
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int val = fc.showSaveDialog(form.frame);
				if(val == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
					if(!file.exists()) {
						try
						{
							file.createNewFile();
						}
						catch (IOException e2) {e2.printStackTrace();}
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
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
								shows.add(line);
								//Joe: add showTimes
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
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				form.frame.dispatchEvent(new WindowEvent(form.frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnExport = new JMenu("Export To...");
		menuBar.add(mnExport);
		
		JMenuItem mntmTwitter = new JMenuItem("Twitter");
		mntmTwitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Mitch: Make it export to twitter here
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
		shows.add(showName);
	}
	
	public void removeShow(String showName)
	{
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
