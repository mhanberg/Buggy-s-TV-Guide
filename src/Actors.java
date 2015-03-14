import java.awt.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import thetvdbapi.model.Actor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
//import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import thetvdbapi.TheTVDBApi;
import thetvdbapi.model.*;

public class Actors extends JFrame
{
	private JLabel name;
	private JTextArea description;
	private JScrollPane sp;
	private JLabel picLabel;
	
	public Actors(final Actor actor, final MainForm form)
	{
		super(actor.getName());
		final Actors at = this;
		this.setBounds(250, 250, 600, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		/*at.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				resizeActComponents(at.getWidth(), at.getHeight());
			}
		});*/
		
		JLabel name = new JLabel(actor.getName());
		name.setBounds(10, 11, 200, 14);
		this.getContentPane().add(name);
		
		description = new JTextArea(actor.getRole());
		description.setBounds(10, 36, 200, 94);
		description.setColumns(10);
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		
		sp = new JScrollPane(description);
		sp.setBounds(10, 36, 200, 94);;
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		BufferedImage myPicture;
		try
		{
			myPicture = ImageIO.read(new URL(actor.getImage()));
			picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(220, 11, 350,350);
			this.getContentPane().add(picLabel);
		} catch (IOException e)
		{
			System.out.println("Failed Read");
		}
		
		//resizeActComponents(at.getWidth(), at.getHeight());
	}
	
	/*private void resizeActComponents(int width, int height)
	{
		//name.setBounds(10, 11, 200, 14);
		//addButton.setBounds(width - 105, 10, 80, 20);
		//description.setBounds(10, 40, width / 2 - 25, height / 2 - 45);
		//sp.setBounds(description.getBounds());
		//picLabel.setBounds(width / 2, height / 2, width / 2, height / 2);
	}*/
}

