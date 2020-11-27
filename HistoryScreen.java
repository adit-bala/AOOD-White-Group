import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 * This class shows all events in an Action Item's history
 */
public class HistoryScreen extends JPanel {
	/*
	 * Instance variables
	 */
	private JLabel pageTitle;
	private List<Event> events;
	private ActionItem item;
	private JLabel nameHistory;
	private JLabel priorityHistory;
	private JLabel commentHistory;
	private GridBagConstraints layoutConstraints = new GridBagConstraints();
	int numberOfNameChanges = 0;
	int numberOfPriorityChanges = 0;
	int numberOfComments = 0;
	
	HistoryScreen (ActionItem item) {
		
		pageTitle = new JLabel("History of [Action Item]");
		nameHistory = new JLabel("Name History");
		priorityHistory = new JLabel("Priority History");
		commentHistory = new JLabel("Comment History");
		
		pageTitle.setFont(new Font("EB Garamond", Font.BOLD, 72));
		nameHistory.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		priorityHistory.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		commentHistory.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		
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
		this.add(nameHistory, layoutConstraints);
		/*
		 * priority history
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(priorityHistory, layoutConstraints);
		/*
		 * comment history
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(commentHistory, layoutConstraints);
	}
	/*
	HistoryScreen (ActionItem item) {
		
	}
	*/
	public void actionPeformed () {
		
	}
	
	public static void main (String[] args) {
		HistoryScreen screen = new HistoryScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
	}
}
