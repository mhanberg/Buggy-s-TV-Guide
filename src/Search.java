import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import thetvdbapi.TheTVDBApi;
import thetvdbapi.model.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;


public class Search extends JFrame {
	private JTextField textField;
	private JList list;
	private DefaultListModel listModel;
	JScrollPane scroll;
	private JButton btnNewButton;
	private ArrayList<String> ids = new ArrayList<String>();
	
	public Search(final MainForm form){
		super("Search Results");
		setBounds(100, 100, 459, 386);
		getContentPane().setLayout(null);
		
		listModel = new DefaultListModel();
		listModel.addElement("Archer");
		
		list = new JList(listModel);
		list.setBounds(20, 42, 404, 241);
		scroll = new JScrollPane(list);
		scroll.setSize(322, 275);
		scroll.setLocation(10, 42);
		getContentPane().add(scroll);
		
		btnNewButton = new JButton("Go To Show");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(335, 159, 89, 23);
		getContentPane().add(btnNewButton);
		
		
	}
	
	public Search(List<Series> searchResults, final MainForm form){
		super("Search Results");
		setBounds(100, 100, 459, 386);
		getContentPane().setLayout(null);
		
		listModel = new DefaultListModel();
		for (int i=0; i<searchResults.size(); i++){
			listModel.addElement(searchResults.get(i).getSeriesName() + " - " + searchResults.get(i).getOverview());
			ids.add(searchResults.get(i).getId());
		}
		
		final TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
		list = new JList(listModel);

		list.setBounds(20, 42, 404, 241);
		scroll = new JScrollPane(list);
		scroll.setSize(322, 275);
		scroll.setLocation(10, 42);
		getContentPane().add(scroll);
		
		btnNewButton = new JButton("Go To Show");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex() != -1)
				{
					try
					{
						Popup p = new Popup(tvdb.getSeries(ids.get(list.getSelectedIndex()), "en"), form);
						p.setVisible(true);
					}
					catch(Exception popupException) { }
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please choose a show");
				}
			}
		});
		btnNewButton.setBounds(335, 159, 89, 23);
		getContentPane().add(btnNewButton);
	}
}
