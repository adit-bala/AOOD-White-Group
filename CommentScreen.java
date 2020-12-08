import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

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

	CommentScreen(ActionItem item) {
		actionItem = item;	
		/*
		 * title of screen
		 */
	    setLayout(null);
	    titlePanel = new JPanel();
		titleLabel = new JLabel("Comment");
		titleLabel.setFont(TITLE_FONT);
		titlePanel.setBounds(30,0,420,120);
		titlePanel.add(titleLabel);
		add(titlePanel);
		
		/*
		 * green line underneath title
		 */
		GreenLinePanel greenLine = new GreenLinePanel();
		greenLine.setBounds(0,120,250,25);
		add(greenLine);
		
		/*
		 * commenting area
		 */
		commentAreaPanel = new JPanel();
		commentInput = new JTextArea(24,37);
		scrollPane = new JScrollPane(commentInput, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commentInput.setText(item.getComment());
		commentInput.setBackground(Color.decode("#e8e8e8"));
		commentInput.setForeground(Color.decode("#56997F"));
		
		commentInput.setLineWrap(true);
		commentInput.setWrapStyleWord(true);
		commentInput.setFont(LABEL_FONT);
		commentAreaPanel.setBounds(30,150,964,900);
		
		commentAreaPanel.add(scrollPane);
		add(commentAreaPanel);
		
		/*
		 * buttons
		 */
		commitButton = new JButton("COMMIT");
		commitButton.addActionListener(this);
		commitButton.setActionCommand("commit");
		commitButton.setFont(LABEL_FONT);
		commitButton.setPreferredSize(new Dimension(100,100));
		commitButton.setMaximumSize(commitButton.getSize());
		commitButton.setBackground(Color.decode("#56997F"));
		commitButton.setForeground(Color.WHITE);
		commitButton.setFocusable(false);
		commitButton.setBorder(BorderFactory.createEtchedBorder());
		commitButton.setBounds(30,1080,200,200);
		add(commitButton);
		
		deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(this);
		deleteButton.setActionCommand("delete");
		deleteButton.setFont(LABEL_FONT);
		deleteButton.setPreferredSize(new Dimension(100,100));
		deleteButton.setMaximumSize(deleteButton.getSize());
		deleteButton.setBackground(Color.decode("#56997F"));
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFocusable(false);
		deleteButton.setBorder(BorderFactory.createEtchedBorder());
		deleteButton.setBounds(794,1080,200,200);
		add(deleteButton);
	}
	public class GreenLinePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.decode("#56997F"));
			g.fillRect(50, 0, 250, 10);
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("commit")) {
			actionItem.updateActionItem(actionItem.getTitle(), actionItem.getPriority(), actionItem.getUrgentByDate(),
					actionItem.getCurrentByDate(), actionItem.getEventualByDate(), this.getComment());
		} else if (eventName.contentEquals("delete")) {
			actionItem.setComment("");
			commentInput.setText("");
		}
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
		CommentScreen screen = new CommentScreen(item);
		JFrame frame = new JFrame();
		screen.setPreferredSize(new Dimension(1024, 1366));
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		
	}
}
