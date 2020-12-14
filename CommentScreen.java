import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import backend.ActionItem;
import backend.CommentChangeEvent;
import backend.FontLoader;
import backend.samples.SampleActionItem1;

/*
 * This class allows the user to enter a comment
 */
public class CommentScreen extends JPanel implements ActionListener {
	/*
	 * Instance variables
	 */
	JFrame frame;
	
	JPanel titlePanel;
	JLabel titleLabel;
	
	JPanel commentAreaPanel;
	JTextArea commentInput;
	JScrollPane scrollPane;
	
	JButton commitButton;
	JButton deleteButton;
	
	CommentChangeEvent commentEvent;
	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 100);
	public static final Font LABEL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 30);
	private ActionItem actionItem;

	CommentScreen(ActionItem item, JFrame frame) {
		this.frame = frame;
		actionItem = item;	
		/*
		 * title of screen
		 */
		setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));
	    setLayout(new GridBagLayout());
		titleLabel = new JLabel("Comment");
		titleLabel.setFont(TITLE_FONT);
		titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel underline = new JPanel();
		underline.setBorder(new LineBorder(Color.decode("#56997F"), 5, true));
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		titleLabel = new JLabel("Comment");
		titleLabel.setFont(TITLE_FONT);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_START;
		add(titlePanel, c);
		
		/*
		 * commenting area
		 */
		commentInput = new JTextArea();
		commentInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollPane = new JScrollPane(commentInput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commentInput.setText(item.getComment());
		commentInput.setBackground(Color.decode("#e8e8e8"));
		commentInput.setForeground(Color.decode("#56997F"));
		
		commentInput.setLineWrap(true);
		commentInput.setWrapStyleWord(true);
		commentInput.setFont(LABEL_FONT);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(20, 0, 20, 0);
		add(scrollPane, c);
		
		/*
		 * buttons
		 */
		
		commitButton = new JButton("COMMIT");
		commitButton.addActionListener(this);
		commitButton.setActionCommand("commit");
		commitButton.setFont(LABEL_FONT);
		commitButton.setBackground(Color.decode("#56997F"));
		commitButton.setForeground(Color.WHITE);
		commitButton.setFocusable(false);
		commitButton.setBorder(BorderFactory.createEtchedBorder());
		commitButton.setPreferredSize(new Dimension(0, 60));
		commitButton.setMinimumSize(new Dimension(0, 60));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0 / 3;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.LINE_START;
		add(commitButton, c);
		
		JPanel empty = new JPanel();
		empty.setPreferredSize(new Dimension(0, 0));
		empty.setMinimumSize(new Dimension(0, 0));
		c.gridx = 1;
		add(empty, c);
		
		deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("delete");
		deleteButton.setFont(LABEL_FONT);
		deleteButton.setBackground(Color.decode("#56997F"));
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFocusable(false);
		deleteButton.setBorder(BorderFactory.createEtchedBorder());
		deleteButton.setPreferredSize(new Dimension(0, 60));
		deleteButton.setMinimumSize(new Dimension(0, 60));
		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		add(deleteButton, c);
	}
	
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("commit")) {
			actionItem.updateActionItem(actionItem.getTitle(), actionItem.getPriority(), actionItem.getUrgentByDate(),
					actionItem.getCurrentByDate(), actionItem.getEventualByDate(), this.getComment());
		} else if (eventName.contentEquals("delete")) {
			actionItem.updateActionItem(actionItem.getTitle(), actionItem.getPriority(), actionItem.getUrgentByDate(),
					actionItem.getCurrentByDate(), actionItem.getEventualByDate(), "");
			commentInput.setText("");
		}
		((MenuBar)frame.getJMenuBar()).actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Back"));
	}

	public String getComment() {
		String comment = "";
		if (commentInput.getText() != "") {
			comment = commentInput.getText();
		}
		return comment;
	}

	public static void main(String[] args) {
		SampleActionItem1 item = new SampleActionItem1();
		JFrame frame = new JFrame();
		CommentScreen screen = new CommentScreen(item, frame);
		screen.setPreferredSize(new Dimension(1024, 1366));
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		
	}
}
