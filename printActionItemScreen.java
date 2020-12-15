import java.awt.Component;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionItem;
import backend.FontLoader;
import backend.HistoryEvent;
import backend.samples.SampleActionItem1;
import backend.samples.SampleToDoList;

public class printActionItemScreen extends JPanel {

	private JLabel title;
	private JLabel urgency;
	private JLabel comment;
	private ArrayList<JLabel> priorityHistory;

	public printActionItemScreen(ActionItem item) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		title = new JLabel("Task Name: " + item.getTitle());
		title.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Bold.ttf", 72));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(title);
		urgency = new JLabel("Urgency: " + Capitalize(item.getPriority().toString().toLowerCase()));
		urgency.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Medium.ttf", 72));
		urgency.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(urgency);
		comment = new JLabel(item.getComment());
		comment.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Regular.ttf", 36));
		comment.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(comment);
		DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("EEEE, d MMMM uuuu");
		ArrayList<HistoryEvent> test = item.getHistory();
		for (HistoryEvent tests : test) {
			System.out.print(tests);
		}
	}

	private String Capitalize(String old) {
		return old.substring(0, 1).toUpperCase() + old.substring(1);
	}

	public static void main(String args[]) {
		SampleActionItem1 test = new SampleActionItem1();
		printActionItemScreen screen = new printActionItemScreen(test);
		screen.setPreferredSize(new Dimension(1000, 1000));
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		screen.repaint();
	}
}
