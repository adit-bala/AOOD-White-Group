import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionItem;
import backend.FontLoader;
import backend.HistoryEvent;
import backend.Priority;

/*
 * This class shows all events in an Action Item's history
 */
public class PrintActionItemPanel extends JPanel {
	/*
	 * Instance variables
	 */
	private List<HistoryEvent> events;
	private ActionItem actionItem;

	public static final Font TITLE_FONT = FontLoader
			.loadFont("/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 40);
	public static final Font HEADER_FONT = FontLoader
			.loadFont("/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 25);
	public static final Font ITEM_FONT = FontLoader
			.loadFont("/res/Chivo/Chivo-Regular.ttf", 20);
	public static final Font TEXT_FONT = FontLoader
			.loadFont("/res/Chivo/Chivo-Regular.ttf", 16);
	public static final Font LABEL_FONT = FontLoader
			.loadFont("/res/Chivo/Chivo-Bold.ttf", 16);

	boolean isAlreadyOneClick;

	PrintActionItemPanel(ActionItem item) {
		actionItem = item;
		setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(500, 648));
		setMaximumSize(new Dimension(500, 9999));
		
		events = actionItem.getHistory();
		/*
		 * title
		 */
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel titleLabel = new JLabel(item.getTitle());
		titleLabel.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel underline = new RoundedPanel(6, Color.decode("#56997F"),
				Color.WHITE);
		underline.setMaximumSize(new Dimension(360, 6));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		titlePanel.setBackground(Color.WHITE);
		add(titlePanel);
		add(Box.createRigidArea(new Dimension(0, 10)));

		addEvents();
	}

	private void addLabel(String name, String value, Font valueFont) {
		JLabel header = new JLabel(name + ": ");
		header.setFont(HEADER_FONT);
		JLabel item = new JLabel(value);
		item.setFont(valueFont);
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.add(header);
		p.add(item);
		p.setAlignmentX(LEFT_ALIGNMENT);
		p.setBackground(Color.WHITE);
		add(p);
		add(Box.createRigidArea(new Dimension(0, 5)));
	}

	private void addEvents() {
		/*
		 * priority
		 */
		Priority p = actionItem.getPriority();
		Font f;
		if (p == Priority.URGENT)
			f = FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 30);
		else if (p == Priority.INACTIVE)
			f = FontLoader.loadFont("/res/Chivo/Chivo-Italic.ttf", 30);
		else if (p == Priority.EVENTUAL)
			f = FontLoader.loadFont("/res/Chivo/Chivo-LightItalic.ttf", 30);
		else
			f = ITEM_FONT;
		addLabel("Priority", p.toString(), f);

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("EEEE, d MMMM uuuu");
		if (actionItem.getPriority() == Priority.COMPLETED) {
			if (actionItem.getCompletedByDate() != null) {
				addLabel("Completed On",
						actionItem.getCompletedByDate().format(formatter),
						ITEM_FONT);
			}
		} else {
			if (actionItem.getUrgentByDate() != null) {
				addLabel("Urgent By",
						actionItem.getUrgentByDate().format(formatter),
						ITEM_FONT);
			}
			if (actionItem.getCurrentByDate() != null) {
				addLabel("Current By",
						actionItem.getCurrentByDate().format(formatter),
						ITEM_FONT);
			}
			if (actionItem.getEventualByDate() != null) {
				addLabel("Eventual By",
						actionItem.getEventualByDate().format(formatter),
						ITEM_FONT);
			}
		}

		/*
		 * comment
		 */
		if (!actionItem.getComment().equals("")) {
			JLabel commentLabel = new JLabel("Comment:");
			commentLabel.setFont(HEADER_FONT);
			commentLabel.setAlignmentX(LEFT_ALIGNMENT);
			add(commentLabel);
			add(Box.createRigidArea(new Dimension(0, 5)));

			JLabel comment = new JLabel(
					"<html><p style=\"width:300px\">" + actionItem.getComment() + "</p></html>");
			comment.setFont(TEXT_FONT);
			comment.setAlignmentX(LEFT_ALIGNMENT);
			add(comment);
			add(Box.createRigidArea(new Dimension(0, 10)));
		}
		/*
		 * history
		 */
		formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM uuuu 'at' hh:mm a");
		if (events.size() > 0) {
			JLabel dividerLabel = new JLabel("History");
			dividerLabel.setFont(HEADER_FONT);
			dividerLabel.setAlignmentX(LEFT_ALIGNMENT);
			add(dividerLabel);
			add(Box.createRigidArea(new Dimension(0, 10)));

			for (int i = 0; i < events.size(); i++) {
				JLabel eventTime = new JLabel(events.get(events.size() - 1 - i)
						.getDateTime().format(formatter));
				eventTime.setFont(LABEL_FONT);
				eventTime.setAlignmentX(LEFT_ALIGNMENT);

				JPanel eventDescriptionPanel = new RoundedPanel(20,
						Color.decode("#e8e8e8"), Color.WHITE);
				eventDescriptionPanel.setBackground(Color.WHITE);
				JLabel eventDescription = new JLabel();
				eventDescription.setFont(LABEL_FONT);
				eventDescription.setForeground(Color.decode("#56997F"));
				eventDescription.setText(
						"<html><p style=\"width:280px\">" + events.get(events.size() - 1 - i).label() + "</p></html>");
				eventDescription
						.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				eventDescriptionPanel.add(eventDescription);
				eventDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
				eventDescriptionPanel.setMaximumSize(new Dimension(
						eventDescriptionPanel.getMaximumSize().width,
						eventDescriptionPanel.getPreferredSize().height));

				JPanel eventPanel = new JPanel();
				eventPanel
						.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
				eventPanel.add(eventTime);
				eventPanel.add(Box.createRigidArea(new Dimension(0, 8)));
				eventPanel.add(eventDescriptionPanel);
				add(eventPanel);
				add(Box.createRigidArea(new Dimension(0, 15)));
				eventPanel.setBackground(Color.WHITE);
			}
		}
		add(Box.createVerticalGlue());
	}
}
