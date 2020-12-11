import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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
	
	JPanel historyPanel;
	JScrollPane historyScroll;
	
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
	int numberOfTitleChanges = 0;
	int numberOfPriorityChanges = 0;
	int numberOfComments = 0;
	private JPanel eventPanel;
	private JPanel eventDescriptionPanel;
	private JLabel eventDescription;
	private JLabel eventTime;
	private ActionItem actionItem;
	
	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80);
	public static final Font HEADER_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 40);
	public static final Font LABEL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 20);
	
	boolean isAlreadyOneClick;
	HistoryScreen (ActionItem item) {
		actionItem = item;
		events = actionItem.getHistory();
		this.setBackground(Color.WHITE);
		for (int i=0; i < item.getHistory().size(); i++) {
			if (((HistoryEvent) item.getHistory().get(i)).getType() == HistoryEvent.TITLE_CHANGE) {
				numberOfTitleChanges++;
				titleChangeEvents.add((HistoryEvent) item.getHistory().get(i));
			} else if (((HistoryEvent) item.getHistory().get(i)).getType() == HistoryEvent.PRIORITY_CHANGE) {
				numberOfPriorityChanges++;
				priorityChangeEvents.add((HistoryEvent) item.getHistory().get(i));
			} else if (((HistoryEvent) item.getHistory().get(i)).getType() == HistoryEvent.COMMENT_CHANGE) {
				numberOfComments++;
				commentChangeEvents.add((HistoryEvent) item.getHistory().get(i));
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
		 * history
		 */
		historyPanel = new JPanel();
		historyPanel.setLayout(new GridLayout(0,1,0,0));
		
		historyScroll = new JScrollPane(historyPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if (events.size() < 10) {
			historyScroll.setBounds(30,145,954,110*events.size());
		} else {
			historyScroll.setBounds(30,145,954,1100);
		}

		addEvents();
		add(historyScroll);	
	}
	
	/*
	 * adds events (JPanels) to the screen
	 * if comment event, also adds mouse listener
	 */
	
	private void addEvents () {
		int num = 0;
		for (int i=0; i < events.size(); i++) {
			eventPanel = new JPanel();
			eventPanel.setLayout(new GridLayout(0,1,0,0));
			eventTime = new JLabel();
			eventTime.setFont(LABEL_FONT);
			eventTime.setSize(964,50);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy hh:mm:ss");
			eventTime.setText(events.get(i).getDateTime().format(formatter));
			eventTime.setBounds(0,0,964,20);
			
			eventDescriptionPanel = new JPanel();
			eventDescription = new JLabel();
			eventDescription.setFont(LABEL_FONT);
			eventDescription.setSize(964,50);
			eventDescription.setText(events.get(i).label());
			
			eventDescriptionPanel.setForeground(Color.decode("#e8e8e8"));
			eventDescriptionPanel.setBounds(0,20,964,80);
			eventDescriptionPanel.add(eventDescription);
			
			eventPanel.add(eventTime);
			eventPanel.add(eventDescriptionPanel);
			eventPanel.setPreferredSize(new Dimension(964,100));
			eventPanel.setSize(new Dimension(964,100));
			eventPanel.setBackground(Color.WHITE);
			historyPanel.add(eventPanel);
			Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
			eventPanel.setBorder(raisedetched);
			
			if (events.get(i).getType() == 2) {
				eventPanel.addMouseListener(this);
			}
			num = i;
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
		HistoryScreen screen = new HistoryScreen(item);
		JFrame frame = new JFrame();
		screen.setPreferredSize(new Dimension(1024, 1366));
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
	}

}
