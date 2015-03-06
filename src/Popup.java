import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import thetvdbapi.TheTVDBApi;
import thetvdbapi.model.*;

public class Popup extends JFrame
{
	private JLabel name;
	private JButton addButton;
	private JTextArea description;
	private JScrollPane sp;
	private List times;
	private List actors;

	public Popup(final Series show, final MainForm form)
	{
		super(show.getSeriesName());
		final Popup p = this;
		this.setBounds(200, 200, 450, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		p.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				resizeComponents(p.getWidth(), p.getHeight());
			}
		}
		);
		
		name = new JLabel(show.getSeriesName());
		name.setBounds(10, 11, 315, 14);
		this.getContentPane().add(name);
		
		java.util.List<String> actorsInShow = show.getActors();
		
		actors = new List();
		for (int i = 0; i < actorsInShow.size(); i++)
			actors.add(actorsInShow.get(i));
		
		this.getContentPane().add(actors);
		
		description = new JTextArea(show.getOverview());
		description.setColumns(10);
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		sp = new JScrollPane(description);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		times = new List();
		this.getContentPane().add(times);
		
		String addShow;
		NextEp check = new NextEp();
		addShow = (String)check.nextEp(show.getSeriesName());
		times.add(addShow);
		
		String buttonString = "Add";
		if(form.showAlreadyInList(show.getSeriesName()))
			buttonString = "Remove";
		
		addButton = new JButton(buttonString);
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
		}
		);
		
		 MouseListener mouseListener = new MouseListener() 
		 {
		      public void mouseClicked(MouseEvent mouseEvent) 
		      {
		        if (mouseEvent.getClickCount() == 2) 
		        {
		          int index = actors.getSelectedIndex();
		          if (index >= 0) 
		          {
		        	  final TheTVDBApi tvdb = new TheTVDBApi("956FCE4039291BF8");
		        	  java.util.List<Actor> showactors;
		        	  try
		        	  {
		        		  showactors = tvdb.getActors(show.getId());
		        		  Popup p = new Popup(showactors.get(index), form);
		        		  p.setVisible(true);	  
		        	  } catch (Exception e)
		        	  {
		        		  JOptionPane.showMessageDialog(null, "Actor/actress cannot be displayed");
		        	  }
		        	  
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
		actors.addMouseListener(mouseListener);

		this.getContentPane().add(addButton);
		
		resizeComponents(p.getWidth(), p.getHeight());
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
		
		sp = new JScrollPane(description);
		sp.setBounds(10, 36, 200, 94);;
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		BufferedImage myPicture;
		try
		{
			myPicture = ImageIO.read(new URL(actor.getImage()));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(220, 11, 350,350);
			this.getContentPane().add(picLabel);
		} catch (IOException e)
		{
			System.out.println("Failed Read");
		}
	}
	
	private void resizeComponents(int width, int height)
	{
		name.setBounds(10, 10, width - 120, 20);
		addButton.setBounds(width - 105, 10, 80, 20);
		description.setBounds(10, 40, width / 2 - 25, height / 2 - 45);
		sp.setBounds(description.getBounds());
		times.setBounds(10, height / 2, width / 2 - 25, height / 2 - 45);
		actors.setBounds(width / 2, 40, width / 2 - 25, height - 85);
	}
}
