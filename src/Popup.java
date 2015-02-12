import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.List;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import thetvdbapi.model.*;

public class Popup extends JFrame
{
	private JTextField description;

	public Popup(final Series show, final MainForm form)
	{
		final Popup p = this;
		this.setBounds(100, 100, 450, 300);
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
		
		description = new JTextField(show.getOverview());
		description.setBounds(10, 36, 200, 94);
		this.getContentPane().add(description);
		description.setColumns(10);
		description.setEditable(false);
		
		List times = new List();
		times.setBounds(10, 136, 200, 116);
		this.getContentPane().add(times);
		
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
