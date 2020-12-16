import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import backend.ActionItem;
import backend.FontLoader;
import backend.HistoryEvent;
import backend.samples.SampleActionItem1;

/*
 * This class shows all events in an Action Item's history
 */
public class printActionItemScreen extends JPanel {
	/*
	 * Instance variables
	 */
	private JFrame frame;

	private JPanel itemNamePanel;
	
	private JPanel historyPanel;
	private JScrollPane scroll;
	private JLabel[] eventDescriptions;

	private JPanel priorityPanel;
	
	private List<HistoryEvent> events;
	private ActionItem actionItem;

	public static final Font TITLE_FONT = FontLoader
			.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80);
	public static final Font ITEM_FONT = FontLoader
			.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 60);
	public static final Font HEADER_FONT = FontLoader
			.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 40);
	public static final Font LABEL_FONT = FontLoader
			.loadFont("src/res/Chivo/Chivo-Bold.ttf", 20);

	boolean isAlreadyOneClick;

	printActionItemScreen(ActionItem item, JFrame frame) {
		this.frame = frame;
		actionItem = item;
		setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
		events = actionItem.getHistory();
//		for (int i = 0; i < item.getHistory().size(); i++) {
//			if (((HistoryEvent) item.getHistory().get(i))
//					.getType() == HistoryEvent.TITLE_CHANGE) {
//				numberOfTitleChanges++;
//				titleChangeEvents.add((HistoryEvent) item.getHistory().get(i));
//			} else if (((HistoryEvent) item.getHistory().get(i))
//					.getType() == HistoryEvent.PRIORITY_CHANGE) {
//				numberOfPriorityChanges++;
//				priorityChangeEvents
//						.add((HistoryEvent) item.getHistory().get(i));
//			} else if (((HistoryEvent) item.getHistory().get(i))
//					.getType() == HistoryEvent.COMMENT_CHANGE) {
//				numberOfComments++;
//				commentChangeEvents
//						.add((HistoryEvent) item.getHistory().get(i));
//			}
//		}
		/*
		 * title
		 */
		setLayout(new GridBagLayout());
		JLabel titleLabel = new JLabel("Print Action Item");
		titleLabel.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel underline = new JPanel();
		underline.setBorder(new LineBorder(Color.decode("#56997F"), 5, true));
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		add(titlePanel, c);
		
		/*
		 * scroll pane
		 */
		historyPanel = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[100];
		gbl.rowWeights = new double[100];
		gbl.rowWeights[99] = Double.MIN_VALUE;
		historyPanel.setLayout(gbl);
		historyPanel.setBackground(Color.WHITE);

		JPanel container = new JPanel();
		container.setBackground(Color.WHITE);
		container.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1;
		container.add(historyPanel, c);

		scroll = new JScrollPane(container,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		addEvents();
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(20, 0, 0, 0);
		add(scroll, c);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int newWidth = Math.min(640, Math.max(280,
						(int) (e.getComponent().getWidth() / 1.3) - 130));
				for (JLabel eventDescription : eventDescriptions) {
					eventDescription.setText(eventDescription.getText()
							.replaceFirst("[0-9]+px", newWidth + "px"));
				}
			}
		});
	}
	/*
	 * adds events (JPanels) to the screen if comment event, also adds mouse
	 * listener
	 */
	private void addEvents() {
		eventDescriptions = new JLabel[events.size()];
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		/*
		 * name of item
		 */
		JPanel itemPanel = new JPanel();
		JLabel itemLabel = new JLabel("<html><p style=\"width:640px\">"+actionItem.getTitle()+"</p></html>");
		itemLabel.setFont(ITEM_FONT);
		itemLabel.setAlignmentX(LEFT_ALIGNMENT);
		itemPanel.add(itemLabel);
		itemPanel.setBackground(Color.WHITE);
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		historyPanel.add(itemPanel, c);
		
		/*
		 * current stuff
		 */
		JPanel priorityPanel = new RoundedPanel(20,
				Color.decode("#e8e8e8"));
		priorityPanel.setBackground(Color.WHITE);
		JLabel priorityLabel = new JLabel("Priority: "+actionItem.getPriority());
		priorityLabel.setFont(LABEL_FONT);
		priorityLabel.setForeground(Color.decode("#56997F"));
		priorityLabel.setAlignmentX(LEFT_ALIGNMENT);
		priorityLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel datePanel = new RoundedPanel(20,
				Color.decode("#e8e8e8"));
		datePanel.setBackground(Color.WHITE);
		JLabel dateLabel = new JLabel(actionItem.getActiveByDate()+"; "+actionItem.getCompletedByDate()+"; "+actionItem.getCurrentByDate()+"; "+actionItem.getEventualByDate()+"; "+actionItem.getUrgentByDate());
		dateLabel.setFont(LABEL_FONT);
		dateLabel.setForeground(Color.decode("#56997F"));
		dateLabel.setAlignmentX(LEFT_ALIGNMENT);
		dateLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel commentPanel = new RoundedPanel(20,
				Color.decode("#e8e8e8"));
		commentPanel.setBackground(Color.WHITE);
		JLabel commentLabel = new JLabel("Comment: "+actionItem.getComment());
		commentLabel.setFont(LABEL_FONT);
		commentLabel.setForeground(Color.decode("#56997F"));
		commentLabel.setAlignmentX(LEFT_ALIGNMENT);
		commentLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		c.insets = new Insets(5,5,5,5);
		c.gridy = 2;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		priorityPanel.add(priorityLabel);
		historyPanel.add(priorityPanel, c);
		
		c.gridy = 3;
		datePanel.add(dateLabel);
		historyPanel.add(datePanel, c);
		
		c.gridy = 4;
		commentPanel.add(commentLabel);
		historyPanel.add(commentPanel, c);
		/*
		 * history
		 */
		c.gridy = 5;
		JPanel dividerPanel = new JPanel();
		JLabel dividerLabel = new JLabel("<html><p style=\"width:640px\">"+"History"+"</p></html>");
		dividerLabel.setFont(HEADER_FONT);
		dividerLabel.setAlignmentX(LEFT_ALIGNMENT);
		dividerPanel.add(dividerLabel);
		dividerPanel.setBackground(Color.WHITE);
		historyPanel.add(dividerPanel, c);
		
		c.gridy = 6;
		for (int i = 0; i < events.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			JLabel eventTime = new JLabel(events.get(events.size() - 1 - i)
					.getDateTime().format(formatter));
			eventTime.setFont(LABEL_FONT);
			eventTime.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel eventDescriptionPanel = new RoundedPanel(20,
					Color.decode("#e8e8e8"));
			eventDescriptionPanel.setBackground(Color.WHITE);
			JLabel eventDescription = new JLabel();
			eventDescription.setFont(LABEL_FONT);
			eventDescription.setForeground(Color.decode("#56997F"));
			eventDescription.setText("<html><p style=\"width:640px\">"
					+ events.get(events.size() - 1 - i).label()
					+ "</p></html>");
			eventDescription
					.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			eventDescriptions[i] = eventDescription;
			eventDescriptionPanel.add(eventDescription);
			eventDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);

			JPanel eventPanel = new JPanel();
			eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
			eventPanel.add(eventTime);
			eventPanel.add(Box.createRigidArea(new Dimension(0, 8)));
			eventPanel.add(eventDescriptionPanel);
			historyPanel.add(eventPanel, c);
			eventPanel
					.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			eventPanel.setBackground(Color.WHITE);
			c.gridy++;
		}
		historyPanel.add(Box.createRigidArea(new Dimension(0, 30)), c);
	}

	public static void main(String[] args) {
		SampleActionItem1 item = new SampleActionItem1();
		JFrame frame = new JFrame();
		printActionItemScreen screen = new printActionItemScreen(item, frame);
		screen.setPreferredSize(new Dimension(1024, 1366));
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class RoundedPanel extends JPanel {
		private Color backgroundColor;
		private int cornerRadius = 15;

		public RoundedPanel(int radius) {
			super();
			cornerRadius = radius;
		}

		public RoundedPanel(int radius, Color bgColor) {
			super();
			cornerRadius = radius;
			backgroundColor = bgColor;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Dimension arcs = new Dimension(cornerRadius, cornerRadius);
			int width = getWidth();
			int height = getHeight();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			// Draws the rounded panel with borders.
			if (backgroundColor != null) {
				graphics.setColor(backgroundColor);
			} else {
				graphics.setColor(getBackground());
			}
			graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width,
					arcs.height); // paint background
			graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width,
					arcs.height); // paint border
		}
	}
}
