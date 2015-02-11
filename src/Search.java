import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import thetvdbapi.model.*;

import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.FlowLayout;

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
	
	public Search(){
		setBounds(100, 100, 459, 386);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 322, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(335, 10, 89, 23);
		getContentPane().add(btnSearch);
		
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
	
	public Search(List<Series> searchResults){
		setBounds(100, 100, 459, 386);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 322, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(335, 10, 89, 23);
		getContentPane().add(btnSearch);
		
		listModel = new DefaultListModel();
		for (int i=0; i<searchResults.size(); i++){
			listModel.addElement(searchResults.get(i).getSeriesName());
		}
		
		
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
}
