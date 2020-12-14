import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private JLabel pageTitle;
	private JScrollPane scrollPane;
	private JPanel closedItemList;
	private HashSet<LocalDateTime> dates = new HashSet<LocalDateTime>();
	// private JLabel dates;

	ClosedActionItemsScreen(List<ActionItem> test) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		pageTitle = new JLabel("Closed Action Items");
		pageTitle.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Bold.ttf", 72));
		pageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(pageTitle);
		closedItemList = new JPanel();
//		scrollPane = new JScrollPane(this);
//		scrollPane.setPreferredSize(new Dimension(250, 80));
//		scrollPane.setAlignmentX(LEFT_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(20, 50)));
		for (ActionItem item : test) {
			dates.add(item.getCompletedByDate());
		}
		for (LocalDateTime date : dates) {
			JLabel newDate = new JLabel(
					"" + Capitalize(date.getDayOfWeek().toString().toLowerCase()) + ", " + date.getDayOfMonth() + " "
							+ Capitalize(date.getMonth().toString().toLowerCase()) + " " + date.getYear());
			newDate.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Bold.ttf", 36));
			this.add(newDate);
			for (ActionItem item : test) {
				if (item.getCompletedByDate().toLocalDate().equals(date.toLocalDate())) {
					this.add(new ActionItemEntry(item));
				}
			}
		}

	}

	private String Capitalize(String old) {
		return old.substring(0, 1).toUpperCase() + old.substring(1);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.decode("#56997F"));
		g2d.fillRect(50, 100, 250, 10);

	}

	public void actionPeformed() {

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
		screen.repaint();

	}
}
