import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 * This class shows all events in an Action Item's history
 */
public class HistoryScreen extends JPanel {
	/*
	 * Instance variables
	 */
	JPanel titlePanel;
	JLabel titleLabel;
	
	JPanel nameHistoryPanel;
	JLabel nameHistoryLabel;
	
	JPanel priorityHistoryPanel;
	JLabel priorityHistoryLabel;
	
	JPanel commentHistoryPanel;
	JLabel commentHistoryLabel;
	private List<Event> events;
	private List<Event> titleChangeEvents = new List<Event>();
	private List<Event> priorityChangeEvents = new List<Event>();
	private List<Event> commentChangeEvents = new List<Event>();
	private ActionItem currentItem;
	int numberOfTitleChanges = 0;
	int numberOfPriorityChanges = 0;
	int numberOfComments = 0;
	private JPanel eventPanel;
	private JLabel eventDescription;
	HistoryScreen (ActionItem item) {
		currentItem = item;
		events = currentItem.getHistory;
		for (int i=0; i < events.size(); i++) {
			if (events.get(i)/*method getting int value*/ == TITLE_CHANGE value) {
				numberOfTitleChanges++;
				titleChangeEvents.add(events.get(i));
			} else if (events.get(i)/*method getting int value*/ == PRIORITY_CHANGE value) {
				numberOfPriorityChanges++;
				priorityChangeEvents.add(events.get(i));
			} else if (events.get(i)/*method getting int value*/ == COMMENT_CHANGE value) {
				numberOfComments++;
				commentChangeEvents.add(events.get(i));
			}
		}
		
		setLayout(new GridLayout(0,1,0,0));
		
		titleLabel = new JLabel("History of " + item);
		titleLabel.setFont(new Font("EB Garamond", Font.BOLD, 72));
		add(titleLabel);
		
		nameHistoryLabel = new JLabel("Name History");
		nameHistoryLabel.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		add(nameHistoryLabel);
		for (int i=0; i < titleChangeEvents.size(); i++) {
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText("NAME: \n" + titleChangeEvents.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel);
		}
		
		priorityHistoryLabel = new JLabel("Priority History");
		priorityHistoryLabel.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		add(priorityHistoryLabel);
		for (int i=0; i < priorityChangeEvents.size(); i++) {
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText(priorityChangeEvents.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel);
		}

		commentHistoryLabel = new JLabel("Comment History");
		commentHistoryLabel.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		add(commentHistoryLabel);
		for (int i=0; i < commentChangeEvents.size(); i++) {
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText("Commented: \n" + priorityChangeEvents.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel);
		}
	}
	
	public void actionPeformed () {
		
	}
	
	public static void main (String[] args) {
		HistoryScreen screen = new HistoryScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
	}
}
