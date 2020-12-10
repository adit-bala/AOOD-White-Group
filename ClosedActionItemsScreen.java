import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

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
	private HashMap<String, ArrayList<ActionItemEntry>> dates = new HashMap<String,  ArrayList<ActionItemEntry>>();
	// private JLabel dates;

	ClosedActionItemsScreen() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		pageTitle = new JLabel("Closed Action Items");
		pageTitle.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Bold.ttf", 72));
		pageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(pageTitle);
//		scrollPane = new JScrollPane(this);
//		scrollPane.setPreferredSize(new Dimension(250, 80));
//		scrollPane.setAlignmentX(LEFT_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(0, 50)));
		SampleToDoList test = new SampleToDoList();
		for (int i = test.getNumCompleteItems() - 1; i >= 0; i--) {
			LocalDate currDate = test.getCompleteItemAtIndex(i).getCompletedByDate();
			dates.add("" + Capitalize(currDate.getDayOfWeek().toString().toLowerCase()) + ", "
					+ currDate.getDayOfMonth() + " " + Capitalize(currDate.getMonth().toString().toLowerCase()) + " "
					+ currDate.getYear(), new ActionItemEntry(test.getCompleteItemAtIndex(i)));
		}
		// this.add(scrollPane);

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
		ClosedActionItemsScreen screen = new ClosedActionItemsScreen();
		screen.setPreferredSize(new Dimension(1024, 1366));
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		screen.repaint();

	}
}
