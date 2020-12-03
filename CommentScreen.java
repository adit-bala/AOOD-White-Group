import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
public class CommentScreen extends JPanel implements ActionListener {
	/*
	 * Instance variables
	 */
	JPanel titlePanel;
	JLabel titleLabel;
	JTextArea commentInput;
	JScrollPane textScrollPane;
	JButton commitButton;
	JButton deleteButton;
	JPanel buttonPanel;
	JPanel buttonSpacer;
	JLabel spacerLabel;
	GridBagConstraints gbc = new GridBagConstraints();

	// private ActionItem actionItem = new ActionItem();
	CommentScreen(/* ActionItem item */) {
		// actionItem = item;
		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5, 5, 5, 5);

		// title
		titlePanel = new JPanel();
		titleLabel = new JLabel("Comment");
		titleLabel.setFont(new Font("EB Garamond", Font.BOLD, 72));
		titlePanel.add(titleLabel);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(titlePanel, gbc);

		// button
		buttonPanel = new JPanel();

		commitButton = new JButton("COMMIT");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		buttonPanel.add(commitButton, gbc);
		commitButton.setActionCommand("commit");
		commitButton.addActionListener(this);

		buttonSpacer = new JPanel();
		spacerLabel = new JLabel(
				"                                                                  ");
		buttonSpacer.add(spacerLabel);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		buttonPanel.add(buttonSpacer, gbc);

		deleteButton = new JButton("DELETE");
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		buttonPanel.add(deleteButton, gbc);
		deleteButton.setActionCommand("delete");
		deleteButton.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 4;

		add(buttonPanel, gbc);

		// comment area
		commentInput = new JTextArea("", 30, 30);
		gbc.gridx = 0;
		gbc.gridy = 1;

		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.fill = GridBagConstraints.BOTH;
		commentInput.setLineWrap(true);
		textScrollPane = new JScrollPane(commentInput,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(textScrollPane, gbc);

	}

	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("commit")) {
			System.out.println(this.getComment());
			// actionItem.setComment(this.getComment());
		} else if (eventName.contentEquals("delete")) {

		}
	}

	public String getComment() {
		String comment = commentInput.getText();
		return comment;
	}

	public static void main(String[] args) {
		CommentScreen screen = new CommentScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);

	}
}
