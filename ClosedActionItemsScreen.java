import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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
	private JPanel ItemList;
	private JScrollPane scrollPane;
	private List<ActionItem> completedActionItems;
	// private JLabel dates;


	ClosedActionItemsScreen() {
		
		pageTitle = new JLabel("Closed Action Items");
		pageTitle.setFont(FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-Bold.ttf", 72));
		scrollPane = new JScrollPane();
		ItemList = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		/*
		 * page title
		 */
		this.add(pageTitle);
		//this.add(scrollPane);
		
		
		
	
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.decode("#56997F"));	
		g2d.fillRect(50, 100, 250, 10);
		SampleToDoList test = new SampleToDoList();
		completedActionItems = test.getCompleteItems();
		for(int i = completedActionItems.size()-1; i >= 0; i--) {
			this.add(new ActionItemEntry(completedActionItems.get(i)));
		}
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
