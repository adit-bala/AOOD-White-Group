import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionItem;
import backend.HistoryEvent;
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
	private List<HistoryEvent> events;
	private List<HistoryEvent> titleChangeEvents = new ArrayList<HistoryEvent>();
	private List<HistoryEvent> priorityChangeEvents = new ArrayList<HistoryEvent>();
	private List<HistoryEvent> commentChangeEvents = new ArrayList<HistoryEvent>();
	private ActionItem currentItem;
	int numberOfTitleChanges = 0;
	int numberOfPriorityChanges = 0;
	int numberOfComments = 0;
	private JPanel eventPanel;
	private JLabel eventDescription;
	HistoryScreen (ActionItem item) {
		currentItem = item;
		events = currentItem.getHistory();
		for (int i=0; i < events.size(); i++) {
			if (events.get(i).getType() == HistoryEvent.TITLE_CHANGE) {
				numberOfTitleChanges++;
				titleChangeEvents.add(events.get(i));
			} else if (events.get(i).getType() == HistoryEvent.PRIORITY_CHANGE) {
				numberOfPriorityChanges++;
				priorityChangeEvents.add(events.get(i));
			} else if (events.get(i).getType() == HistoryEvent.COMMENT_CHANGE) {
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
		addEvents(titleChangeEvents);
		
		priorityHistoryLabel = new JLabel("Priority History");
		priorityHistoryLabel.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		add(priorityHistoryLabel);
		addEvents(priorityChangeEvents);

		commentHistoryLabel = new JLabel("Comment History");
		commentHistoryLabel.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		add(commentHistoryLabel);
		addEvents(commentChangeEvents);
	}
	
	public void actionPeformed () {
		
	}
	
	private void addEvents (List<HistoryEvent> eventList) {
		for (int i=0; i < eventList.size(); i++) {
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText(eventList.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel);
		}
	}
	
	public static void main (String[] args) {
		HistoryScreen screen = new HistoryScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
	}
}
