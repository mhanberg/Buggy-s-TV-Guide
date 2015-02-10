import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JButton;


public class Popup extends JFrame
{
	private JTextField show;
	private JButton addShow;
	
	public Popup()
	{	
		super("Archer");
		setLayout(new FlowLayout());
		
		show = new JTextField("Archer", 10);
		show.setEditable(false);
		add(show);
		
		addShow = new JButton("Add Show");
		addShow.setBounds(335, 10, 89, 23);
		add(addShow);
	}

}
