import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import backend.ActionItem;
import backend.FontLoader;
import backend.samples.SampleActionItem1;

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
	//JPanel greenLinePanel;
	GridBagConstraints gbc = new GridBagConstraints();
	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 100);
	//public static final Font HEADER_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 60);
	public static final Font LABEL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 30);

	private ActionItem actionItem;
	CommentScreen(ActionItem item) {
		actionItem = item;
		
		setLayout(new GridBagLayout());
		//gbc.insets = new Insets(5, 5, 5, 5);
		
		// title
		
		titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(0,1,0,0));
		titleLabel = new JLabel("Comment");
		
		JPanel greenLinePanel = new greenLinePanel();
		
		titleLabel.setFont(TITLE_FONT);
		
		titlePanel.add(titleLabel);
		titlePanel.add(greenLinePanel);
		
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
		commitButton.setBackground(Color.decode("#56997F"));
		commitButton.setForeground(Color.WHITE);
		
		

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
		deleteButton.setBackground(Color.decode("#56997F"));
		deleteButton.setForeground(Color.WHITE);

		gbc.gridx = 0;
		gbc.gridy = 5;
		
		add(buttonPanel, gbc);

		// comment area
		commentInput = new JTextArea(item.getComment(), 30, 30);
		commentInput.setSelectedTextColor(Color.decode("#56997F"));
		gbc.gridx = 0;
		gbc.gridy = 2;

		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.fill = GridBagConstraints.BOTH;
		commentInput.setLineWrap(true);
		textScrollPane = new JScrollPane(commentInput,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(textScrollPane, gbc);
		textScrollPane.setFont(LABEL_FONT);

	}
	
	public class greenLinePanel extends JPanel {
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.decode("#56997F"));
			g.fillRect(10, 0, 580, 20);
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("commit")) {
			//System.out.println(this.getComment());
			actionItem.setComment(this.getComment());
		} else if (eventName.contentEquals("delete")) {
			actionItem.setComment("");
			commentInput.setText("");
		}
	}

	public String getComment() {
		String comment = commentInput.getText();
		return comment;
	}

	public static void main(String[] args) {
		SampleActionItem1 item = new SampleActionItem1();
		//System.out.println(item.getComment());
		CommentScreen screen = new CommentScreen(item);
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);

	}
}
