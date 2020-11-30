import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private JLabel pageTitle;
	private JLabel nameHistory;
	private JLabel priorityHistory;
	private JLabel commentHistory;
	private List<Event> events;
	private List<Event> titleChangeEvents = new List<Event>();
	private List<Event> priorityChangeEvents = new List<Event>();
	private List<Event> commentChangeEvents = new List<Event>();
	private ActionItem currentItem;
	private GridBagConstraints layoutConstraints = new GridBagConstraints();
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
		
		pageTitle = new JLabel("History of [Action Item]");
		nameHistory = new JLabel("Name History");
		priorityHistory = new JLabel("Priority History");
		commentHistory = new JLabel("Comment History");
		
		pageTitle.setFont(new Font("EB Garamond", Font.BOLD, 72));
		nameHistory.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		priorityHistory.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		commentHistory.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		
		this.setLayout(new GridBagLayout());
		/*
		 * page title
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(pageTitle, layoutConstraints);
		/*
		 * name history
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(nameHistory, layoutConstraints);
		
		for (int i=0; i < titleChangeEvents.size(); i++) {
			layoutConstraints.gridx = 0;
			layoutConstraints.gridy = 2+i;
			layoutConstraints.gridheight = 1;
			layoutConstraints.gridwidth = 8;
			layoutConstraints.fill = GridBagConstraints.BOTH;
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText("NAME: \n" + titleChangeEvents.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel, layoutConstraints);
		}
		/*
		 * priority history
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2+titleChangeEvents.size()+1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(priorityHistory, layoutConstraints);
		
		for (int i=0; i < priorityChangeEvents.size(); i++) {
			layoutConstraints.gridx = 0;
			layoutConstraints.gridy = 2+titleChangeEvents.size()+1+i;
			layoutConstraints.gridheight = 1;
			layoutConstraints.gridwidth = 8;
			layoutConstraints.fill = GridBagConstraints.BOTH;
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText(priorityChangeEvents.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel, layoutConstraints);
		}
		
		/*
		 * comment history
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(commentHistory, layoutConstraints);
		
		for (int i=0; i < commentChangeEvents.size(); i++) {
			layoutConstraints.gridx = 0;
			layoutConstraints.gridy = 2+titleChangeEvents.size()+1+priorityChangeEvents.size()+1+i;
			layoutConstraints.gridheight = 1;
			layoutConstraints.gridwidth = 8;
			layoutConstraints.fill = GridBagConstraints.BOTH;
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText("Commented: \n" + priorityChangeEvents.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel, layoutConstraints);
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
