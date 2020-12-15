import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import backend.samples.*;

/*
 * This class shows all events in an Action Item's history
 */
public class ClosedActionItemsScreen extends JPanel {
	/*
	 * Instance variables
	 */
	private HashSet<LocalDate> dates = new HashSet<LocalDate>();
	// private JLabel dates;

	ClosedActionItemsScreen(List<ActionItem> test) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.WHITE);
		JLabel titleLabel = new JLabel("Closed Action Items");
		titleLabel.setFont(FontLoader
				.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80));
		JPanel underline = new RoundedPanel(10, Color.decode("#56997F"), Color.WHITE);
		underline.setMaximumSize(new Dimension(610, 10));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		titlePanel.setAlignmentX(LEFT_ALIGNMENT);
		this.add(titlePanel);
	
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		itemPanel.setBackground(Color.WHITE);
		for (ActionItem item : test) {
			dates.add(item.getCompletedByDate().toLocalDate());
		}
		for (LocalDate date : dates) {
			itemPanel.add(Box.createVerticalStrut(15));
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("EEEE, d MMMM uuuu");
			JLabel newDate = new JLabel(date.format(formatter));
			newDate.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Bold.ttf", 30));
			newDate.setAlignmentX(LEFT_ALIGNMENT);
			itemPanel.add(newDate);
			itemPanel.add(Box.createVerticalStrut(5));
			for (ActionItem item : test) {
				if (item.getCompletedByDate().toLocalDate().equals(date)) {
					ActionItemEntry entry = new ActionItemEntry(item);
					entry.setAlignmentX(LEFT_ALIGNMENT);
					itemPanel.add(entry);
				}
			}
		}
		itemPanel.add(Box.createVerticalStrut(15));
		itemPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		JScrollPane scrollPane = new JScrollPane(itemPanel);
		scrollPane.setBorder(null);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		this.add(scrollPane);
	}
	
	public static void main(String[] args) {
		SampleToDoList test = new SampleToDoList();
		List<ActionItem> sample = new ArrayList<ActionItem>();
		for (int i = 0; i < test.getNumCompleteItems(); i++) {
			sample.add(test.getCompleteItemAtIndex(i));
		}
		ClosedActionItemsScreen screen = new ClosedActionItemsScreen(sample);
		screen.setPreferredSize(new Dimension(1024, 1366));
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
