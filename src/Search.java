import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;

import thetvdbapi.TheTVDBApi;
import thetvdbapi.model.*;

public class Search extends JFrame
{
	private Search search = this;
	private JList list;
	private DefaultListModel listModel;
	JScrollPane scroll;
	private JButton btnNewButton;
	private ArrayList<String> ids = new ArrayList<String>();
	
	public Search(List<Series> searchResults, final MainForm form)
	{
		super("Search Results");
		setBounds(150, 150, 450, 300);
		getContentPane().setLayout(null);
		
		listModel = new DefaultListModel();
		
		for (int i=0; i<searchResults.size(); i++)
		{
			listModel.addElement(searchResults.get(i).getSeriesName() + " - " + searchResults.get(i).getOverview());
			ids.add(searchResults.get(i).getId());
		}
		
		if (searchResults.size() == 0)
		{
			listModel.addElement("No search results available");
		}
		
		final TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
		list = new JList(listModel);
		
		scroll = new JScrollPane(list);
		getContentPane().add(scroll);
		
		btnNewButton = new JButton("Details");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(list.getSelectedIndex() != -1)
				{
					try
					{
						Popup p = new Popup(tvdb.getSeries(ids.get(list.getSelectedIndex()), "en"), form);
						p.setVisible(true);
					}
					catch(Exception popupException) { JOptionPane.showMessageDialog(null, "Connection to server failed. Check your internet connection.");}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose a show");
				}
			}
		});
		getContentPane().add(btnNewButton);
		
		if (searchResults.size() == 0)
		{
			btnNewButton.setEnabled(false);	
		}
		
		MouseListener mouseListener = new MouseListener() 
		 {
		      public void mouseClicked(MouseEvent mouseEvent) 
		      {
		        if (mouseEvent.getClickCount() == 2) 
		        {
		          int index = list.getSelectedIndex();
		          if (index >= 0) 
		          {
		        	  try
		        	  {
		        		  Popup p = new Popup(tvdb.getSeries(ids.get(list.getSelectedIndex()), "en"), form);
		        		  p.setVisible(true);  
		        	  } catch (Exception popupException)
		        	  {
		        		  JOptionPane.showMessageDialog(null, "Connection to server failed. Check your internet connection.");
		        	  }  
		          }
		          else
		          {
						JOptionPane.showMessageDialog(null, "Please choose a show");
		          }
		          
		        }
		        
		      }

			@Override
			public void mouseEntered(MouseEvent arg0) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent arg0) 
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent arg0) 
			{
				// TODO Auto-generated method stub	
			}
		};
		list.addMouseListener(mouseListener);
		
		resizeComponents(this.getWidth(), this.getHeight());
	}
	
	private void resizeComponents(int width, int height)
	{
		list.setBounds(10, 10, width - 10, height - 50);
		scroll.setBounds(10, 10, width - 35, height - 100);
		btnNewButton.setBounds(width - 125, height - 75, 100, 25);
	}
}
