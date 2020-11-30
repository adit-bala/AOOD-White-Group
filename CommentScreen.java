  import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/*
 * This class allows the user to enter a comment
 */
public class CommentScreen extends JPanel implements ActionListener{
	/*
	 * Instance variables
	 */
	private JLabel pageTitle;
	private JTextArea commentField;
	private JButton commitButton;
	private JButton deleteButton;
	private GridBagConstraints layoutConstraints = new GridBagConstraints();
	private JPanel spacerBetweenButtons;
	private JPanel spacerBetweenTextFieldAndButtons;
	private JScrollPane textFieldScroll = new JScrollPane();
	private ActionItem actionItem = new ActionItem();
	CommentScreen (ActionItem item) {
		layoutConstraints.gridwidth = 8;
		layoutConstraints.gridheight = 8;
		pageTitle = new JLabel("Comment");
		pageTitle.setFont(new Font("EB Garamond", Font.BOLD, 72));
		commentField = new JTextArea("Add Comment...",5,30);
		commitButton = new JButton("COMMIT");
		deleteButton = new JButton("DELETE");
		spacerBetweenButtons = new JPanel();
		spacerBetweenTextFieldAndButtons = new JPanel();
		
		actionItem = item;
		
		commentField.setFont(new Font("Chivo Regular", Font.PLAIN, 30));
		
		this.setLayout(new GridBagLayout());
		/*
		 * pageTitle
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(pageTitle, layoutConstraints);
		
		/*
		 * commentField
		 */
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(commentField, layoutConstraints);
		
		/*
		 * spacer
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 2;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 8;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(spacerBetweenTextFieldAndButtons, layoutConstraints);
		
		/*
		 * commitButton
		 */
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 4;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(commitButton, layoutConstraints);
		
		commitButton.setActionCommand("commit");
		commitButton.addActionListener(this);
		
		/*
		 * spacer
		 */
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 3;
		layoutConstraints.gridheight = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(spacerBetweenButtons, layoutConstraints);
		
		/*
		 * deleteButton
		 */
		layoutConstraints.gridy = 3;
		layoutConstraints.gridx = 9;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		this.add(deleteButton, layoutConstraints);
		
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("commit")) {
			System.out.println("commit");
			System.out.println(this.getComment());
			actionItem.setComment(this.getComment());
		} else if (eventName.contentEquals("delete")) {
			System.out.println("delete");
		}
	}
	
	public String getComment () {
		String comment = commentField.getText();
		return comment;
	}
	
	public static void main (String[] args) {
		
		CommentScreen screen = new CommentScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
        frame.setVisible(true);
        
	}
}
