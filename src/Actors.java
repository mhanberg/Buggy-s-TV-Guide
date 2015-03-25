import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import thetvdbapi.model.Actor;

public class Actors extends JFrame
{
	private Actors actors = this;
	private JLabel name;
	private JTextArea description;
	private JScrollPane sp;
	private JLabel picLabel;
	
	public Actors(final Actor actor, final MainForm form)
	{
		super(actor.getName());
		this.setBounds(250, 250, 600, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		this.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent e)
			{
				resizeComponents(actors.getWidth(), actors.getHeight());
			}
		});
		
		name = new JLabel(actor.getName());
		this.getContentPane().add(name);
		
		description = new JTextArea(actor.getRole());
		description.setColumns(10);
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		
		sp = new JScrollPane(description);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(sp);
		
		BufferedImage myPicture;
		try
		{
			myPicture = ImageIO.read(new URL(actor.getImage()));
			picLabel = new JLabel(new ImageIcon(myPicture));
			this.getContentPane().add(picLabel);
		}
		catch (IOException e) { }
		
		resizeComponents(this.getWidth(), this.getHeight());
	}
	
	private void resizeComponents(int width, int height)
	{
		name.setBounds(10, 10, width, 20);
		description.setBounds(10, 40, width / 2 - 25, height - 85);
		sp.setBounds(description.getBounds());
		picLabel.setBounds(width / 2, 40, width / 2 - 35, height - 85);
	}
}

