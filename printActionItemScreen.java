import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
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
public class printActionItemScreen extends JPanel implements ActionListener {
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
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		historyPanel.add(itemPanel, c);
		
		/*
		 * current stuff
		 */
		/*
		 * priority
		 */
		JLabel priorityLabel = new JLabel("Priority: ");
		priorityLabel.setFont(LABEL_FONT);
		priorityLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel priorityDescriptionPanel = new RoundedPanel(20,
				Color.decode("#e8e8e8"));
		priorityDescriptionPanel.setBackground(Color.WHITE);
		JLabel priorityDescription = new JLabel();
		priorityDescription.setFont(LABEL_FONT);
		priorityDescription.setForeground(Color.decode("#56997F"));
		priorityDescription.setText("<html><p style=\"width:640px\">"
					+ actionItem.getPriority()
					+ "</p></html>");
		priorityDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		priorityDescriptionPanel.add(priorityDescription);
		priorityDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel priorityPanel = new JPanel();
		priorityPanel.setLayout(new BoxLayout(priorityPanel, BoxLayout.Y_AXIS));
		priorityPanel.add(priorityLabel);
		priorityPanel.add(priorityDescriptionPanel);
		priorityPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		priorityPanel
				.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
		priorityPanel.setBackground(Color.WHITE);
		c.gridy = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.LINE_START;
		historyPanel.add(priorityPanel, c);
		
		/*
		 * dates
		 */
		int gridyPlaceholder = 2;
		/*
		 DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			JLabel eventTime = new JLabel(events.get(events.size() - 1 - i)
					.getDateTime().format(formatter));
		 */
		if (actionItem.getActiveByDate() != null) {
			JLabel activeByDateLabel = new JLabel("Active By Date: ");
			activeByDateLabel.setFont(LABEL_FONT);
			activeByDateLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			DateTimeFormatter formatter = DateTimeFormatter
						.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			
			JPanel activeByDateDescriptionPanel = new RoundedPanel(20,
					Color.decode("#e8e8e8"));
			activeByDateDescriptionPanel.setBackground(Color.WHITE);
			JLabel activeByDateDescription = new JLabel();
			activeByDateDescription.setFont(LABEL_FONT);
			activeByDateDescription.setForeground(Color.decode("#56997F"));
			activeByDateDescription.setText("<html><p style=\"width:640px\">"
						+ actionItem.getActiveByDate().format(formatter)
						+ "</p></html>");
			activeByDateDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			activeByDateDescriptionPanel.add(activeByDateDescription);
			activeByDateDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel activeByDatePanel = new JPanel();
			activeByDatePanel.setLayout(new BoxLayout(activeByDatePanel, BoxLayout.Y_AXIS));
			activeByDatePanel.add(activeByDateLabel);
			activeByDatePanel.add(activeByDateDescriptionPanel);
			activeByDatePanel.add(Box.createRigidArea(new Dimension(0, 8)));
			activeByDatePanel
					.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			activeByDatePanel.setBackground(Color.WHITE);
			c.gridy = gridyPlaceholder;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			historyPanel.add(activeByDatePanel, c);
			gridyPlaceholder++;
		}
		if (actionItem.getCompletedByDate() != null) {
			JLabel completedByDateLabel = new JLabel("Completed By Date: ");
			completedByDateLabel.setFont(LABEL_FONT);
			completedByDateLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			
			JPanel completedByDateDescriptionPanel = new RoundedPanel(20,
					Color.decode("#e8e8e8"));
			completedByDateDescriptionPanel.setBackground(Color.WHITE);
			JLabel completedByDateDescription = new JLabel();
			completedByDateDescription.setFont(LABEL_FONT);
			completedByDateDescription.setForeground(Color.decode("#56997F"));
			completedByDateDescription.setText("<html><p style=\"width:640px\">"
						+ actionItem.getCompletedByDate().format(formatter)
						+ "</p></html>");
			completedByDateDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			completedByDateDescriptionPanel.add(completedByDateDescription);
			completedByDateDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel completedByDatePanel = new JPanel();
			completedByDatePanel.setLayout(new BoxLayout(completedByDatePanel, BoxLayout.Y_AXIS));
			completedByDatePanel.add(completedByDateLabel);
			completedByDatePanel.add(completedByDateDescriptionPanel);
			completedByDatePanel.add(Box.createRigidArea(new Dimension(0, 8)));
			completedByDatePanel
					.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			completedByDatePanel.setBackground(Color.WHITE);
			c.gridy = gridyPlaceholder;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			historyPanel.add(completedByDatePanel, c);
			gridyPlaceholder++;
		}
		if (actionItem.getCurrentByDate() != null) {
			JLabel currentByDateLabel = new JLabel("Current By Date: ");
			currentByDateLabel.setFont(LABEL_FONT);
			currentByDateLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			
			JPanel currentByDateDescriptionPanel = new RoundedPanel(20,
					Color.decode("#e8e8e8"));
			currentByDateDescriptionPanel.setBackground(Color.WHITE);
			JLabel currentByDateDescription = new JLabel();
			currentByDateDescription.setFont(LABEL_FONT);
			currentByDateDescription.setForeground(Color.decode("#56997F"));
			currentByDateDescription.setText("<html><p style=\"width:640px\">"
						+ actionItem.getCurrentByDate().format(formatter)
						+ "</p></html>");
			currentByDateDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			currentByDateDescriptionPanel.add(currentByDateDescription);
			currentByDateDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel currentByDatePanel = new JPanel();
			currentByDatePanel.setLayout(new BoxLayout(currentByDatePanel, BoxLayout.Y_AXIS));
			currentByDatePanel.add(currentByDateLabel);
			currentByDatePanel.add(currentByDateDescriptionPanel);
			currentByDatePanel.add(Box.createRigidArea(new Dimension(0, 8)));
			currentByDatePanel
					.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			currentByDatePanel.setBackground(Color.WHITE);
			c.gridy = gridyPlaceholder;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			historyPanel.add(currentByDatePanel, c);
			gridyPlaceholder++;
		}
		if (actionItem.getEventualByDate() != null) {
			JLabel eventualByDateLabel = new JLabel("Eventual By Date: ");
			eventualByDateLabel.setFont(LABEL_FONT);
			eventualByDateLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			
			JPanel eventualByDateDescriptionPanel = new RoundedPanel(20,
					Color.decode("#e8e8e8"));
			eventualByDateDescriptionPanel.setBackground(Color.WHITE);
			JLabel eventualByDateDescription = new JLabel();
			eventualByDateDescription.setFont(LABEL_FONT);
			eventualByDateDescription.setForeground(Color.decode("#56997F"));
			eventualByDateDescription.setText("<html><p style=\"width:640px\">"
						+ actionItem.getEventualByDate().format(formatter)
						+ "</p></html>");
			eventualByDateDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			eventualByDateDescriptionPanel.add(eventualByDateDescription);
			eventualByDateDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel eventualByDatePanel = new JPanel();
			eventualByDatePanel.setLayout(new BoxLayout(eventualByDatePanel, BoxLayout.Y_AXIS));
			eventualByDatePanel.add(eventualByDateLabel);
			eventualByDatePanel.add(eventualByDateDescriptionPanel);
			eventualByDatePanel.add(Box.createRigidArea(new Dimension(0, 8)));
			eventualByDatePanel
					.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			eventualByDatePanel.setBackground(Color.WHITE);
			c.gridy = gridyPlaceholder;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			historyPanel.add(eventualByDatePanel, c);
			gridyPlaceholder++;
		}
		if (actionItem.getUrgentByDate() != null) {
			JLabel urgentByDateLabel = new JLabel("Urgent By Date: ");
			urgentByDateLabel.setFont(LABEL_FONT);
			urgentByDateLabel.setAlignmentX(LEFT_ALIGNMENT);
			
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu: hh:mm a");
			
			JPanel urgentByDateDescriptionPanel = new RoundedPanel(20,
					Color.decode("#e8e8e8"));
			urgentByDateDescriptionPanel.setBackground(Color.WHITE);
			JLabel urgentByDateDescription = new JLabel();
			urgentByDateDescription.setFont(LABEL_FONT);
			urgentByDateDescription.setForeground(Color.decode("#56997F"));
			urgentByDateDescription.setText("<html><p style=\"width:640px\">"
						+ actionItem.getUrgentByDate().format(formatter)
						+ "</p></html>");
			urgentByDateDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			urgentByDateDescriptionPanel.add(urgentByDateDescription);
			urgentByDateDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel urgentByDatePanel = new JPanel();
			urgentByDatePanel.setLayout(new BoxLayout(urgentByDatePanel, BoxLayout.Y_AXIS));
			urgentByDatePanel.add(urgentByDateLabel);
			urgentByDatePanel.add(urgentByDateDescriptionPanel);
			urgentByDatePanel.add(Box.createRigidArea(new Dimension(0, 8)));
			urgentByDatePanel
					.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			urgentByDatePanel.setBackground(Color.WHITE);
			c.gridy = gridyPlaceholder;
			c.gridx = 0;
			c.anchor = GridBagConstraints.LINE_START;
			historyPanel.add(urgentByDatePanel, c);
			gridyPlaceholder++;
		}
		
		/*
		 * comment
		 */
		JLabel commentLabel = new JLabel("Comment: ");
		commentLabel.setFont(LABEL_FONT);
		commentLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel commentDescriptionPanel = new RoundedPanel(20,
				Color.decode("#e8e8e8"));
		commentDescriptionPanel.setBackground(Color.WHITE);
		JLabel commentDescription = new JLabel();
		commentDescription.setFont(LABEL_FONT);
		commentDescription.setForeground(Color.decode("#56997F"));
		commentDescription.setText("<html><p style=\"width:640px\">"
					+ actionItem.getComment()
					+ "</p></html>");
		commentDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		commentDescriptionPanel.add(commentDescription);
		commentDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JPanel commentPanel = new JPanel();
		commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
		commentPanel.add(commentLabel);
		commentPanel.add(commentDescriptionPanel);
		commentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		commentPanel
				.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
		commentPanel.setBackground(Color.WHITE);
		c.gridy = gridyPlaceholder;
		c.gridx = 0;
		c.anchor = GridBagConstraints.LINE_START;
		historyPanel.add(commentPanel, c);
		gridyPlaceholder++;
		/*
		 * history
		 */
		c.gridy = gridyPlaceholder;
		JPanel dividerPanel = new JPanel();
		JLabel dividerLabel = new JLabel("<html><p style=\"width:640px\">"+"History"+"</p></html>");
		dividerLabel.setFont(HEADER_FONT);
		dividerLabel.setAlignmentX(LEFT_ALIGNMENT);
		dividerPanel.add(dividerLabel);
		dividerPanel.setBackground(Color.WHITE);
		historyPanel.add(dividerPanel, c);
		gridyPlaceholder++;
		
		c.gridy = gridyPlaceholder;
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
		/*
		 * print button
		 */
		c.gridy++;
		JButton commitButton = new JButton("PRINT");
		commitButton.addActionListener(this);
		commitButton.setActionCommand("print");
		commitButton.setFont(LABEL_FONT);
		commitButton.setBackground(Color.decode("#56997F"));
		commitButton.setForeground(Color.WHITE);
		commitButton.setFocusable(false);
		commitButton.setBorder(BorderFactory.createEtchedBorder());
		commitButton.setPreferredSize(new Dimension(0, 60));
		commitButton.setMinimumSize(new Dimension(0, 60));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0 / 3;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.LINE_END;
		add(commitButton, c);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		if (eventName.contentEquals("print")) {
			System.out.println("print");
		}
		
	}
}
