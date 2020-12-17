import backend.ActionItem;
import backend.FileUtilities;
import backend.FontLoader;
import backend.Priority;
import backend.samples.SampleActionItem1;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;

import javax.swing.*;

/* Displays ways for users to manipulate action item data.

 */
public class EditActionItemScreen extends JPanel implements ActionListener, FocusListener {
	private JFrame frame;

	private JLabel pageTitle;
	private JTextField name, uYear, cYear, eYear;
	private JComboBox<String> uMonth, cMonth, eMonth, uDay, cDay, eDay;
	private JRadioButton urgent, current, eventual, inactive,
	complete;
	private JCheckBox urgentBy, currentBy, eventualBy;
	JButton save, cancel, comment, history, print;
	private ActionItem actionItem;

	// added JLabels for "Priority" and "Scheduling" headers
	private JLabel pHead, sHead;
	JPanel titlePane;
	JPanel namePane;
	JPanel pPane;
	JPanel sPane;
	JPanel buttonsPane;

	public static final Font TITLE_FONT = FontLoader.loadFont
			("src/res/EBGaramond/static/EBGaramond-ExtraBold"
					+ ".ttf", 72);
	public static final Font HEAD_FONT = FontLoader.loadFont
			("src/res/EBGaramond/static/EBGaramond-ExtraBold"
					+ ".ttf", 36);
	public static final Font BODY_FONT = FontLoader.loadFont
			("src/res/Chivo/Chivo-Regular.ttf", 30);
	public static final Font BUTTON_FONT = FontLoader.loadFont
			("src/res/Chivo/Chivo-Bold.ttf", 30);

	public static final Color EMERALD = new Color(19,77,55);
	public static final Color TEAL = new Color(86,153,127);
	public static final Color DUST = new Color(235,235,235);
	public static final Color TEAL_SELECT = new Color(86,153,127,200);

	GridBagConstraints gbc = new GridBagConstraints();

	EditActionItemScreen(ActionItem item, JFrame frame) {
		this.frame = frame;
		actionItem = item;

		/* main panel setup */
		setLayout(new GridBagLayout());
		gbc.insets = new Insets(5,5,5,5);
		setBackground(Color.white);
		gbc.anchor=GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;

		/* title */
		titlePane = new JPanel();
		titlePane.setLayout(new BoxLayout(titlePane,
				BoxLayout.Y_AXIS));
		pageTitle = new JLabel("Edit Action Item");
		pageTitle.setFont(TITLE_FONT);
		titlePane.setBackground(Color.WHITE);
		titlePane.add(pageTitle);
		GreenLinePanel greenLine = new GreenLinePanel();
		greenLine.setBackground(Color.WHITE);
		greenLine.setBounds(0,120,250,25);
		titlePane.add(greenLine);
		gbc.gridwidth=3;
		gbc.gridx=0;
		gbc.gridy=0;
		add(titlePane,gbc);
		gbc.gridwidth=1;

		/* action item name */
		name = new JTextField(actionItem.getTitle());
		name.setFont(BODY_FONT);
		name.setBackground(DUST);
		name.setForeground(EMERALD);
		name.setBorder(BorderFactory.createEmptyBorder(2,5,2,5));
		name.setSelectionColor(TEAL_SELECT);
		name.setSelectedTextColor(Color.white);
		name.addActionListener(this);
		name.addFocusListener(this);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=3;
		add(name,gbc);
		gbc.gridwidth=1;

		/* priority panel and header */
		pPane = new JPanel();
		pPane.setLayout(new BoxLayout(pPane,BoxLayout.Y_AXIS));
		pPane.setBackground(Color.WHITE);
		pHead = new JLabel("Priority");
		pHead.setFont(HEAD_FONT);
		pPane.add(pHead);

		/* priority radio buttons */
		ButtonGroup group = new ButtonGroup();
		urgent = new JRadioButton("Urgent");
		current = new JRadioButton("Current");
		eventual = new JRadioButton("Eventual");
		inactive = new JRadioButton("Inactive");
		complete = new JRadioButton("Complete");
		urgent.setFont(BODY_FONT);
		current.setFont(BODY_FONT);
		eventual.setFont(BODY_FONT);
		inactive.setFont(BODY_FONT);
		complete.setFont(BODY_FONT);
		urgent.setForeground(EMERALD);
		current.setForeground(EMERALD);
		eventual.setForeground(EMERALD);
		inactive.setForeground(EMERALD);
		complete.setForeground(EMERALD);
		urgent.setBackground(Color.WHITE);
		current.setBackground(Color.WHITE);
		eventual.setBackground(Color.WHITE);
		inactive.setBackground(Color.WHITE);
		complete.setBackground(Color.WHITE);
		group.add(urgent);
		group.add(current);
		group.add(eventual);
		group.add(inactive);
		group.add(complete);
		pPane.add(urgent);
		pPane.add(current);
		pPane.add(eventual);
		pPane.add(inactive);
		pPane.add(complete);
		urgent.addActionListener(this);
		current.addActionListener(this);
		eventual.addActionListener(this);
		inactive.addActionListener(this);
		complete.addActionListener(this);

		/* add priority panel to main */
		gbc.gridx=0;
		gbc.gridy=2;
		add(pPane, gbc);

		/* scheduling panel and header */
		sPane = new JPanel();
		GridBagConstraints sgbc = new GridBagConstraints();
		sgbc.anchor=GridBagConstraints.LINE_START;
		sPane.setLayout(new GridBagLayout());
		sPane.setBackground(Color.WHITE);
		sgbc.gridx=0;
		sgbc.gridy=0;
		sHead = new JLabel("Scheduling");
		sHead.setFont(HEAD_FONT);
		sPane.add(sHead,sgbc);

		/* scheduling checkboxes and comboboxes */
		urgentBy = new JCheckBox("Urgent by: ");
		currentBy = new JCheckBox("Current by: ");
		eventualBy = new JCheckBox("Eventual by: ");
		urgentBy.setFont(BODY_FONT);
		currentBy.setFont(BODY_FONT);
		eventualBy.setFont(BODY_FONT);
		urgentBy.setForeground(EMERALD);
		currentBy.setForeground(EMERALD);
		eventualBy.setForeground(EMERALD);
		urgentBy.setBackground(Color.WHITE);
		currentBy.setBackground(Color.WHITE);
		eventualBy.setBackground(Color.WHITE);
		sgbc.gridy=1;
		sPane.add(urgentBy,sgbc);
		sgbc.gridy=2;
		sPane.add(currentBy,sgbc);
		sgbc.gridy=3;
		sPane.add(eventualBy,sgbc);

		sgbc.gridx=1;
		String[] months = {"mm","Jan", "Feb", "Mar", "Apr", "May", 
				"Jun","Jul","Aug", "Sep", "Oct", "Nov", "Dec"};
		uMonth = new JComboBox<>(months);
		cMonth = new JComboBox<>(months);
		eMonth = new JComboBox<>(months);
		uMonth.setFont(BODY_FONT);
		cMonth.setFont(BODY_FONT);
		eMonth.setFont(BODY_FONT);
		uMonth.setForeground(EMERALD);
		cMonth.setForeground(EMERALD);
		eMonth.setForeground(EMERALD);
		uMonth.setBackground(DUST);
		cMonth.setBackground(DUST);
		eMonth.setBackground(DUST);
		uMonth.setSelectedIndex(0);
		cMonth.setSelectedIndex(0);
		eMonth.setSelectedIndex(0);
		uMonth.addActionListener(uMonth);
		cMonth.addActionListener(cMonth);
		eMonth.addActionListener(eMonth);
		sgbc.gridy=1;
		sPane.add(uMonth,sgbc);
		sgbc.gridy=2;
		sPane.add(cMonth,sgbc);
		sgbc.gridy=3;
		sPane.add(eMonth,sgbc);

		sgbc.gridx=2;
		String[] days = new String[32];
		days[0]="dd";
		for (int i=1;i<32;i++) {
			days[i]=Integer.toString(i);
		}
		uDay = new JComboBox<>(days);
		cDay = new JComboBox<>(days);
		eDay = new JComboBox<>(days);
		uDay.setFont(BODY_FONT);
		cDay.setFont(BODY_FONT);
		eDay.setFont(BODY_FONT);
		uDay.setForeground(EMERALD);
		cDay.setForeground(EMERALD);
		eDay.setForeground(EMERALD);
		uDay.setBackground(DUST);
		cDay.setBackground(DUST);
		eDay.setBackground(DUST);
		uDay.setSelectedIndex(0);
		cDay.setSelectedIndex(0);
		eDay.setSelectedIndex(0);
		uDay.addActionListener(uDay);
		cDay.addActionListener(cDay);
		eDay.addActionListener(eDay);
		sgbc.gridy=1;
		sPane.add(uDay,sgbc);
		sgbc.gridy=2;
		sPane.add(cDay,sgbc);
		sgbc.gridy=3;
		sPane.add(eDay,sgbc);

		sgbc.gridx=3;
		uYear = new JTextField("yyyy");
		cYear = new JTextField("yyyy");
		eYear = new JTextField("yyyy");
		uYear.setFont(BODY_FONT);
		cYear.setFont(BODY_FONT);
		eYear.setFont(BODY_FONT);
		uYear.setForeground(EMERALD);
		cYear.setForeground(EMERALD);
		eYear.setForeground(EMERALD);
		uYear.setBackground(DUST);
		cYear.setBackground(DUST);
		eYear.setBackground(DUST);
		uYear.setSelectionColor(TEAL_SELECT);
		cYear.setSelectionColor(TEAL_SELECT);
		eYear.setSelectionColor(TEAL_SELECT);
		uYear.setSelectedTextColor(Color.WHITE);
		cYear.setSelectedTextColor(Color.WHITE);
		eYear.setSelectedTextColor(Color.WHITE);
		sgbc.gridy=1;
		sPane.add(uYear,sgbc);
		sgbc.gridy=2;
		sPane.add(cYear,sgbc);
		sgbc.gridy=3;
		sPane.add(eYear,sgbc);
		
		uMonth.setEnabled(false);
		cMonth.setEnabled(false);
		eMonth.setEnabled(false);
		uDay.setEnabled(false);
		cDay.setEnabled(false);
		eDay.setEnabled(false);
		uYear.setEnabled(false);
		cYear.setEnabled(false);
		eYear.setEnabled(false);
		

		/* add scheduling panel to main */
		gbc.gridx=1;
		gbc.gridy=2;
		add(sPane, gbc);

		// top buttons -- need or not?
		save = new JButton("Save");
		cancel = new JButton("Cancel");

		/* bottom buttons and pane */
		buttonsPane = new JPanel();
		comment = new JButton("COMMENT");
		comment.addActionListener(this);
		comment.setActionCommand("COMMENT");
		history = new JButton("HISTORY");
		history.addActionListener(this);
		history.setActionCommand("HISTORY");
		print = new JButton("PRINT");
		print.addActionListener(this);
		print.setActionCommand("PRINT");
		comment.setAlignmentX(LEFT_ALIGNMENT);
		history.setAlignmentX(CENTER_ALIGNMENT);
		print.setAlignmentX(RIGHT_ALIGNMENT);
		comment.setForeground(Color.WHITE);
		history.setForeground(Color.WHITE);
		print.setForeground(Color.WHITE);
		comment.setBackground(TEAL);
		history.setBackground(TEAL);
		print.setBackground(TEAL);
		comment.setFont(BUTTON_FONT);
		history.setFont(BUTTON_FONT);
		print.setFont(BUTTON_FONT);
		buttonsPane.add(comment);
		buttonsPane.add(history);
		buttonsPane.add(print);
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=3;
		buttonsPane.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPane.setBackground(Color.WHITE);
		add(buttonsPane,gbc);
		gbc.gridwidth=1;

	}

	public class GreenLinePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(TEAL);
			g.fillRect(50, 0, 250, 10);
		}
	}

	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("Urgent")) {
			actionItem.updateActionItem(actionItem.getTitle(),
					Priority.URGENT, actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
		} else if (eventName.contentEquals("Current")) {
			actionItem.updateActionItem(actionItem.getTitle(),
					Priority.CURRENT, actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
		} else if (eventName.contentEquals("Eventual")) {
			actionItem.updateActionItem(actionItem.getTitle(),
					Priority.EVENTUAL, actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
		} else if (eventName.contentEquals("Inactive")) {
			actionItem.updateActionItem(actionItem.getTitle(),
					Priority.INACTIVE, actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
		} else if (eventName.contentEquals("Completed")) {
			actionItem.updateActionItem(actionItem.getTitle(),
					Priority.COMPLETED, actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
		}
		
		else if (event.getSource()==urgentBy) {
			System.out.println("urgent by");
			uMonth.setEnabled(true);
			uDay.setEnabled(urgentBy.isSelected());
			uYear.setEnabled(urgentBy.isSelected());
		} else if (event.getSource()==currentBy) {
			cMonth.setEnabled(currentBy.isSelected());
			cDay.setEnabled(currentBy.isSelected());
			cYear.setEnabled(currentBy.isSelected());
		} else if (event.getSource()==eventualBy) {
			eMonth.setEnabled(eventualBy.isSelected());
			eDay.setEnabled(eventualBy.isSelected());
			eYear.setEnabled(eventualBy.isSelected());
		}
		
		
		
		if (eventName.contentEquals("COMMENT")) {
			setCommentScreen();
		} else if (eventName.contentEquals("HISTORY")) {
			setHistoryScreen();
		} else if (eventName.contentEquals("PRINT")) {
			setPrintScreen();
		} else {
			actionItem.updateActionItem(name.getText(),
					actionItem.getPriority(), actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
			System.out.println("pressed enter, current name: "+actionItem.getTitle());
}
	}

	private void setCommentScreen() {
		frame.setContentPane(new CommentScreen(actionItem, frame));
		((MenuBar) frame.getJMenuBar()).addPrevPanel(this);
		frame.revalidate();
		frame.repaint();
	}

	private void setHistoryScreen() {
		frame.setContentPane(new HistoryScreen(actionItem, frame));
		((MenuBar) frame.getJMenuBar()).addPrevPanel(this);
		frame.revalidate();
		frame.repaint();
	}

	private void setPrintScreen() {
		frame.setContentPane(new printActionItemScreen(actionItem, frame));
		((MenuBar) frame.getJMenuBar()).addPrevPanel(this);
		frame.revalidate();
		frame.repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		SampleActionItem1 item = new SampleActionItem1();
		EditActionItemScreen screen = new 
				EditActionItemScreen(item, frame);
		screen.setPreferredSize(new Dimension(1024, 1366));
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("focus gained, current name: "+
				actionItem.getTitle());}
		
	@Override
	public void focusLost(FocusEvent e) {
		actionItem.updateActionItem(name.getText(),
				actionItem.getPriority(), actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
				actionItem.getEventualByDate(), actionItem.getComment());	
		System.out.println("focus lost, current name: "+actionItem.getTitle());
	}
}
