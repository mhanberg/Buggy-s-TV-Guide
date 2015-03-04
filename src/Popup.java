import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.List;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import thetvdbapi.TheTVDBApi;
import thetvdbapi.model.*;

import java.util.*;

public class Popup extends JFrame
{
	private JTextArea description;

	public Popup(final Series show, final MainForm form)
	{
		super(show.getSeriesName());
		final Popup p = this;
		this.setBounds(200, 200, 450, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel name = new JLabel(show.getSeriesName());
		name.setBounds(10, 11, 315, 14);
		this.getContentPane().add(name);
		
		java.util.List<String> actorsInShow = show.getActors();
		
		List actors = new List();
		for (int i = 0; i < actorsInShow.size(); i++)
			actors.add(actorsInShow.get(i));
		
		actors.setBounds(224, 36, 200, 216);
		this.getContentPane().add(actors);
		
		description = new JTextArea(show.getOverview());
		description.setBounds(10, 36, 200, 94);
		description.setColumns(10);
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(description);
		sp.setBounds(10, 36, 200, 94);;
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		List times = new List();
		times.setBounds(10, 136, 200, 116);
		this.getContentPane().add(times);
		
		String addShow;
		NextEp check = new NextEp();
		addShow = (String)check.nextEp(show.getSeriesName());
		times.add(addShow);
		
		String buttonString = "Add";
		if(form.showAlreadyInList(show.getSeriesName()))
			buttonString = "Remove";
		
		JButton addButton = new JButton(buttonString);
		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(form.showAlreadyInList(show.getSeriesName()))
					form.removeShow(show.getSeriesName());
				else
					form.addShow(show.getSeriesName());
				
				p.dispatchEvent(new WindowEvent(p, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		 MouseListener mouseListener = new MouseListener() {
		      public void mouseClicked(MouseEvent mouseEvent) {
		        if (mouseEvent.getClickCount() == 2) {
		          int index = actors.getSelectedIndex();
		          if (index >= 0) {
		        	  final TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
		        	  java.util.List<Actor> showactors;
		        	  try{
		        		  showactors = tvdb.getActors(show.getId());
		        		  Popup p = new Popup(showactors.get(index), form);
		        		  p.setVisible(true);	  
		        	  } catch (Exception e){

		        	  }
		        	  
		          }
		        }
		      }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		actors.addMouseListener(mouseListener);

		addButton.setBounds(335, 7, 89, 23);
		this.getContentPane().add(addButton);
	}
	
	public Popup(final Actor actor, final MainForm form)
	{
		super(actor.getName());
		final Popup p = this;
		this.setBounds(250, 250, 600, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel name = new JLabel(actor.getName());
		name.setBounds(10, 11, 200, 14);
		this.getContentPane().add(name);
		
		
		description = new JTextArea(actor.getRole());
		description.setBounds(10, 36, 200, 94);
		description.setColumns(10);
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(description);
		sp.setBounds(10, 36, 200, 94);;
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		BufferedImage myPicture;
		try{
			myPicture = ImageIO.read(new URL(actor.getImage()));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(220, 11, 350,350);
			this.getContentPane().add(picLabel);
		} catch (IOException e){
			System.out.println("Failed Read");
		}
				
		
	}
}
