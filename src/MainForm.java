import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.List;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm
{
	private JFrame frame;
	private JTextField searchText;
	private List times;

	public MainForm()
	{
		frame = new JFrame();
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
				Search searchResult = new Search();
				searchResult.setVisible(true);
			}
		});
		
		List shows = new List();
		shows.setBounds(10, 44, 200, 207);
		frame.getContentPane().add(shows);
		
		times = new List();
		times.setBounds(224, 44, 200, 207);
		frame.getContentPane().add(times);
		
		shows.add("Archer");
		shows.add("It's Always Sunny in Philidelphia");
		
		times.add("Archer - \"Vision Quest\" - 2/5/15 6:30pm - FXX");
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
