import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionItem;
import backend.CommentChangeEvent;
import backend.FontLoader;
import backend.HistoryEvent;
import backend.Priority;
import backend.samples.SampleActionItem1;
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
	
	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 100);
	public static final Font HEADER_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 60);
	public static final Font LABEL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 30);
	
	
	boolean isAlreadyOneClick;
	HistoryScreen (ActionItem item) {
		this.setBackground(Color.WHITE);
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
		
		titleLabel = new JLabel("History of " + item.getTitle());
		titleLabel.setFont(TITLE_FONT);
		add(titleLabel);
		
		nameHistoryLabel = new JLabel("Name History");
		nameHistoryLabel.setFont(HEADER_FONT);
		add(nameHistoryLabel);
		addEvents(titleChangeEvents);
		
		priorityHistoryLabel = new JLabel("Priority History");
		priorityHistoryLabel.setFont(HEADER_FONT);
		add(priorityHistoryLabel);
		addEvents(priorityChangeEvents);

		commentHistoryLabel = new JLabel("Comment History");
		commentHistoryLabel.setFont(HEADER_FONT);
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
			eventPanel.setBackground(Color.WHITE);
			eventDescription = new JLabel();
			eventDescription.setText(eventList.get(i).label());
			eventDescription.setFont(LABEL_FONT);
			eventPanel.setSize(100,100);
			eventPanel.add(makeDateLabel(eventList.get(i).getDateTime()));
			eventPanel.add(eventDescription);
			this.add(eventPanel);
			if (eventList == commentChangeEvents) {
				commentPanelList.add(eventPanel);
				eventPanel.addMouseListener(this);
			}
		}
	}
	
	private JPanel makeDateLabel(LocalDate d) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String day = d.getDayOfWeek().toString();
		day = day.substring(0, 1) + day.substring(1).toLowerCase();
		String formattedString = day + ", " + d.format(formatter);
		JLabel label = new JLabel(formattedString);
		label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		label.setFont(HEADER_FONT);
		panel.add(label);
		panel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		return panel;
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
		SampleActionItem1 item = new SampleActionItem1();
		item.setComment("hello");
		//System.out.println(item.getHistory().get(0));
		for (int i=0; i < item.getHistory().size(); i++) {
			System.out.println(item.getHistory().get(i));
		}
		HistoryScreen screen = new HistoryScreen(item);
		JFrame frame = new JFrame();
		//frame.setBackground(Color.WHITE);
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
	}

}
