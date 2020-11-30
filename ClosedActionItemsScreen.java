import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
/*
 * This class shows all events in an Action Item's history
 */
public class ClosedActionItemsScreen extends JPanel {
	/*
	 * Instance variables
	 */
	private JLabel pageTitle;
	private JScrollPane scrollPane;
	private List<ActionItem> completedActionItems;
	//private JLabel dates;
	
	private GridBagConstraints layoutConstraints = new GridBagConstraints();
	
	ClosedActionItemsScreen () {
		
		pageTitle = new JLabel("Closed Action Items");
		//dates = new JLabel("Wednesday, 02 December 2020");
		
		pageTitle.setFont(new Font("EB Garamond", Font.BOLD, 72));
		//dates.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		
		scrollPane = new JScrollPane();
		
		this.setLayout(new GridBagLayout());
		/*
		 * page title
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(pageTitle, layoutConstraints);
		/*
		 * name history
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		//this.add(NewActionItem, layoutConstraints);
		/*
		 * priority history
		 */
	}
	
	
	public void paintComponent(Graphics g) {
		//code will get completedActionItems from the ToDoList Classes and display it to the user
	}
	
	public void actionPeformed () {
		
	}
	
	public static void main (String[] args) {
		ClosedActionItemsScreen screen = new ClosedActionItemsScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
	}
}
