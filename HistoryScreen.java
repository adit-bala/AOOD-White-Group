import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionItem;
import backend.HistoryEvent;
/*
 * This class shows all events in an Action Item's history
 */
public class HistoryScreen extends JPanel implements MouseListener {
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
	private List<JPanel> commentPanelList = new ArrayList<JPanel>();
	private ActionItem currentItem;
	int numberOfTitleChanges = 0;
	int numberOfPriorityChanges = 0;
	int numberOfComments = 0;
	private JPanel eventPanel;
	private JLabel eventDescription;
	
	boolean isAlreadyOneClick;
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
	
	/*
	 * adds events (JPanels) to the screen
	 * if comment event, also adds mouse listener
	 */
	private void addEvents (List<HistoryEvent> eventList) {
		for (int i=0; i < eventList.size(); i++) {
			eventPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setText(eventList.get(i).label());
			eventPanel.setSize(100,100);
			eventPanel.add(eventDescription);
			this.add(eventPanel);
			if (eventList == commentChangeEvents) {
				commentPanelList.add(eventPanel);
				eventPanel.addMouseListener(this);
			}
		}
	}
	
	/*
	 * checks for double clicks
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (isAlreadyOneClick) {
	        //open up comment screen
	        isAlreadyOneClick = false;
	    } else {
	        isAlreadyOneClick = true;
	        Timer t = new Timer("doubleclickTimer", false);
	        t.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                isAlreadyOneClick = false;
	            }
	        }, 500);
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main (String[] args) {
		HistoryScreen screen = new HistoryScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
	}
}
