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

/*
 * This class shows all events in an Action Item's history
 */
public class HistoryScreen extends JPanel implements MouseListener {
	/*
	 * Instance variables
	 */
	private JFrame frame;

	private JPanel historyPanel;
	private JScrollPane historyScroll;
	private JLabel[] eventDescriptions;

	private List<HistoryEvent> events;
	private ActionItem actionItem;

	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80);
	public static final Font HEADER_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf",
			40);
	public static final Font LABEL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 20);
	private final Color bgColor = new Color(230, 230, 230);

	boolean isAlreadyOneClick;

	HistoryScreen(ActionItem item, JFrame frame) {
		this.frame = frame;
		actionItem = item;
		setBackground(Color.WHITE);
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
		JLabel titleLabel = new JLabel("History of " + item.getTitle());
		titleLabel.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel underline = new RoundedPanel(10, Color.decode("#56997F"), Color.WHITE);
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		titlePanel.setBackground(Color.WHITE);
		add(titlePanel, c);

		addEvents();
	}

	/*
	 * adds events (JPanels) to the screen if comment event, also adds mouse
	 * listener
	 */

	private void addEvents() {
		/*
		 * history
		 */
		historyPanel = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[100];
		gbl.rowWeights = new double[100];
		gbl.rowWeights[99] = Double.MIN_VALUE;
		historyPanel.setLayout(gbl);
		historyPanel.setBackground(bgColor);

		eventDescriptions = new JLabel[events.size()];
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		for (int i = 0; i < events.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM uuuu 'at' hh:mm a");
			JLabel eventTime = new JLabel(events.get(events.size() - 1 - i).getDateTime().format(formatter));
			eventTime.setFont(LABEL_FONT);
			eventTime.setAlignmentX(LEFT_ALIGNMENT);
			JPanel eventDescriptionPanel = new RoundedPanel(20, Color.WHITE, bgColor);
			eventDescriptionPanel.setBackground(bgColor);
			JLabel eventDescription = new JLabel();
			eventDescription.setFont(LABEL_FONT);
			eventDescription.setForeground(Color.decode("#56997F"));
			eventDescription.setText(
					"<html><p style=\"width:640px\">" + events.get(events.size() - 1 - i).label() + "</p></html>");
			eventDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			eventDescriptions[i] = eventDescription;
			eventDescriptionPanel.add(eventDescription);
			eventDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);

			JPanel eventPanel = new JPanel();
			eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
			eventPanel.add(eventTime);
			eventPanel.add(Box.createRigidArea(new Dimension(0, 8)));
			eventPanel.add(eventDescriptionPanel);
			historyPanel.add(eventPanel, c);
			eventPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
			eventPanel.setBackground(bgColor);

			if (events.get(i).getType() == 2) {
				eventPanel.addMouseListener(this);
			}
			c.gridy++;
		}

		JPanel container = new JPanel();
		container.setBackground(bgColor);
		container.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1;
		container.add(historyPanel, c);

		historyScroll = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(20, 0, 0, 0);
		add(historyScroll, c);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int newWidth = Math.min(640, Math.max(280, (int) (e.getComponent().getWidth() / 1.3) - 130));
				for (JLabel eventDescription : eventDescriptions) {
					eventDescription.setText(eventDescription.getText().replaceFirst("[0-9]+px", newWidth + "px"));
				}
			}
		});

		historyPanel.add(Box.createRigidArea(new Dimension(0, 30)), c);
	}

	/*
	 * checks for double clicks
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (isAlreadyOneClick) {
			setCommentScreen(actionItem);
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

	public void refreshEvents() {
		remove(historyScroll);
		addEvents();
	}

	private void setCommentScreen(ActionItem item) {
		frame.setContentPane(new CommentScreen(item, frame));
		frame.revalidate();
		frame.repaint();
		((MenuBar) frame.getJMenuBar()).addPrevPanel(this);
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
}
