import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.List;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import thetvdbapi.*;
import thetvdbapi.model.*;

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
		
		searchText = new JTextField();
		searchText.setBounds(10, 11, 315, 20);
		frame.getContentPane().add(searchText);
		searchText.setColumns(10);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(335, 10, 89, 23);
		frame.getContentPane().add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
				Search searchResult;
				try{
					searchResult = new Search(tvdb.searchSeries(searchText.getText(), "en"), form);
				} catch (Exception asdf){
					searchResult = new Search(form);
				}
				searchResult.setVisible(true);
			}
		});
		
		shows = new List();
		shows.setBounds(10, 44, 200, 207);
		frame.getContentPane().add(shows);
		
		times = new List();
		times.setBounds(224, 44, 200, 207);
		frame.getContentPane().add(times);
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
