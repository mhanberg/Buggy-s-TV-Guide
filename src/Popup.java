import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.List;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import thetvdbapi.model.*;

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
		addButton.setBounds(335, 7, 89, 23);
		this.getContentPane().add(addButton);
	}
}
