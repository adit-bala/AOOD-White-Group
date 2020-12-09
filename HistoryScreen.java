import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
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
import javax.swing.JScrollPane;

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
	JPanel nameHistory;
	JScrollPane nameHistoryScroll;
	
	JPanel priorityHistoryPanel;
	JLabel priorityHistoryLabel;
	JPanel priorityHistory;
	JScrollPane priorityHistoryScroll;
	
	JPanel commentHistoryPanel;
	JLabel commentHistoryLabel;
	JPanel commentHistory;
	JScrollPane commentHistoryScroll;
	
	List<HistoryEvent> events;
	List<HistoryEvent> titleChangeEvents = new ArrayList<HistoryEvent>();
	List<HistoryEvent> priorityChangeEvents = new ArrayList<HistoryEvent>();
	List<HistoryEvent> commentChangeEvents = new ArrayList<HistoryEvent>();
	List<JPanel> commentPanelList = new ArrayList<JPanel>();
	private ActionItem currentItem;
	int numberOfTitleChanges = 0;
	int numberOfPriorityChanges = 0;
	int numberOfComments = 0;
	private JPanel eventPanel;
	private JLabel eventDescription;
	
	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80);
	public static final Font HEADER_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 40);
	public static final Font LABEL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 20);
	
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
		/*
		 * title
		 */
		setLayout(null);
		titleLabel = new JLabel("History of " + item.getTitle());
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setBounds(30,0,994,120);
		add(titleLabel);
		
		/*
		 * green line underneath title
		 */
		GreenLinePanel greenLine = new GreenLinePanel();
		greenLine.setBounds(0,120,250,25);
		add(greenLine);
		
		/*
		 * name history
		 */
		nameHistoryLabel = new JLabel("Name History");
		nameHistoryLabel.setFont(HEADER_FONT);
		nameHistoryLabel.setBounds(30,145,994,100);
		add(nameHistoryLabel);
		//addEvents(titleChangeEvents);
		
		nameHistory = new JPanel();
		nameHistory.setPreferredSize(new Dimension(964,300));
		nameHistory.setLayout(new GridLayout(0,1,0,0));
		nameHistoryScroll = new JScrollPane(nameHistory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		nameHistoryScroll.setBounds(30,245,964,300);
		add(nameHistoryScroll);
		/*
		 * priority history
		 */
		priorityHistoryLabel = new JLabel("Priority History");
		priorityHistoryLabel.setFont(HEADER_FONT);
		priorityHistoryLabel.setBounds(30,545,994,100);
		add(priorityHistoryLabel);
		//addEvents(priorityChangeEvents);

		priorityHistory = new JPanel();
		priorityHistory.setPreferredSize(new Dimension(964,300));
		priorityHistory.setLayout(new GridLayout(0,1,0,0));
		priorityHistoryScroll = new JScrollPane(priorityHistory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		priorityHistoryScroll.setBounds(30,645,964,300);
		add(priorityHistoryScroll);
		/*
		 * comment history
		 */
		commentHistoryLabel = new JLabel("Comment History");
		commentHistoryLabel.setFont(HEADER_FONT);
		commentHistoryLabel.setBounds(30,945,994,100);
		add(commentHistoryLabel);
		//addEvents(commentChangeEvents);
		
		
		
		
		commentHistory = new JPanel();
		commentHistory.setPreferredSize(new Dimension(964,300));
		commentHistory.setLayout(new GridLayout(0,1,0,0));
		commentHistoryScroll = new JScrollPane(commentHistory, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commentHistoryScroll.setBounds(30,1045,964,300);
		addEvents(commentHistoryScroll, commentChangeEvents);
		/*
		eventPanel = new JPanel();
		eventPanel.setBackground(Color.WHITE);
		eventDescription = new JLabel();
		eventDescription.setText(commentChangeEvents.get(0).label());
		eventDescription.setFont(LABEL_FONT);
		eventPanel.setPreferredSize(new Dimension(964,100));
		eventPanel.add(eventDescription);
		commentHistory.add(eventPanel);
		commentPanelList.add(eventPanel);
		eventPanel.addMouseListener(this);
		
		eventPanel = new JPanel();
		eventPanel.setBackground(Color.WHITE);
		eventDescription = new JLabel();
		eventDescription.setText(commentChangeEvents.get(0).label());
		eventDescription.setFont(LABEL_FONT);
		eventPanel.setPreferredSize(new Dimension(964,100));
		eventPanel.add(eventDescription);
		commentHistory.add(eventPanel);
		commentPanelList.add(eventPanel);
		eventPanel.addMouseListener(this);
		*/
		
		add(commentHistoryScroll);
		
	}
	
	/*
	 * adds events (JPanels) to the screen
	 * if comment event, also adds mouse listener
	 */
	private void addEvents (JScrollPane scrollPanel, List<HistoryEvent> eventList) {
		for (int i=0; i < eventList.size(); i++) {
			eventPanel = new JPanel();
			eventPanel.setBackground(Color.WHITE);
			eventDescription = new JLabel();
			eventDescription.setText(eventList.get(i).label());
			eventDescription.setFont(LABEL_FONT);
			eventPanel.setPreferredSize(new Dimension(964,100));
			eventPanel.add(eventDescription);
			scrollPanel.add(eventPanel);
			if (eventList == commentChangeEvents) {
				commentPanelList.add(eventPanel);
				eventPanel.addMouseListener(this);
			}
		}
	}
	
	public class GreenLinePanel extends JPanel {
		GreenLinePanel() {
			this.setBackground(Color.WHITE);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.decode("#56997F"));
			g.fillRect(50, 0, 250, 10);
		}
	}
	
	/*
	 * checks for double clicks
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (isAlreadyOneClick) {
			System.out.println("double clicked");
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
		item.updateActionItem(item.getTitle(), item.getPriority(), item.getUrgentByDate(),
				item.getCurrentByDate(), item.getEventualByDate(), "sdfsfdfs");
		item.updateActionItem(item.getTitle(), item.getPriority(), item.getUrgentByDate(),
				item.getCurrentByDate(), item.getEventualByDate(), "adfadas");
		HistoryScreen screen = new HistoryScreen(item);
		JFrame frame = new JFrame();
		screen.setPreferredSize(new Dimension(1024, 1366));
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
	}

}
