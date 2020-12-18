import backend.ActionItem;
import backend.FileUtilities;
import backend.FontLoader;
import backend.Priority;
import backend.samples.SampleActionItem1;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.time.LocalDateTime;

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
	private int um=0,ud=0,uy=0,cm=0,cd=0,cy=0,em=0,ed=0,ey=0;

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
		if (item.getPriority()==Priority.URGENT)
			urgent.setSelected(true);
		else if (item.getPriority()==Priority.CURRENT)
			current.setSelected(true);
		else if (item.getPriority()==Priority.EVENTUAL)
			eventual.setSelected(true);
		else if (item.getPriority()==Priority.INACTIVE)
			inactive.setSelected(true);
		else if (item.getPriority()==Priority.COMPLETED)
			complete.setSelected(true);

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
		urgentBy.addActionListener(this);
		currentBy.addActionListener(this);
		eventualBy.addActionListener(this);
		sgbc.gridy=1;
		sPane.add(urgentBy,sgbc);
		sgbc.gridy=2;
		sPane.add(currentBy,sgbc);
		sgbc.gridy=3;
		sPane.add(eventualBy,sgbc);
			// months
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
		uMonth.addActionListener(this);
		cMonth.addActionListener(this);
		eMonth.addActionListener(this);
		sgbc.gridy=1;
		sPane.add(uMonth,sgbc);
		sgbc.gridy=2;
		sPane.add(cMonth,sgbc);
		sgbc.gridy=3;
		sPane.add(eMonth,sgbc);
		
			// days
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
		uDay.addActionListener(this);
		cDay.addActionListener(this);
		eDay.addActionListener(this);
		sgbc.gridy=1;
		sPane.add(uDay,sgbc);
		sgbc.gridy=2;
		sPane.add(cDay,sgbc);
		sgbc.gridy=3;
		sPane.add(eDay,sgbc);
			// years
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
		uYear.addActionListener(this);
		cYear.addActionListener(this);
		eYear.addActionListener(this);
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
		// set existing values
		if (!(item.getUrgentByDate()==null)) {
			uMonth.setEnabled(true);
			uDay.setEnabled(true);
			uYear.setEnabled(true);
			urgentBy.setSelected(true);
			uMonth.setSelectedIndex(item.getUrgentByDate().getMonthValue());
			uDay.setSelectedIndex(item.getUrgentByDate().getDayOfMonth());
			uYear.setText(Integer.toString(item.getUrgentByDate().getYear()));
		} else if (!(item.getCurrentByDate()==null)) {
			cMonth.setEnabled(true);
			cDay.setEnabled(true);
			cYear.setEnabled(true);
			currentBy.setSelected(true);
			cMonth.setSelectedIndex(item.getCurrentByDate().getMonthValue());
			cDay.setSelectedIndex(item.getCurrentByDate().getDayOfMonth());
			cYear.setText(Integer.toString(item.getCurrentByDate().getYear()));		
		} else if (!(item.getEventualByDate()==null)) {
			eMonth.setEnabled(true);
			eDay.setEnabled(true);
			eYear.setEnabled(true);
			eventualBy.setSelected(true);
			eMonth.setSelectedIndex(item.getEventualByDate().getMonthValue());
			eDay.setSelectedIndex(item.getEventualByDate().getDayOfMonth());
			eYear.setText(Integer.toString(item.getEventualByDate().getYear()));		
		}

		/* add scheduling panel to main */
		gbc.gridx=1;
		gbc.gridy=2;
		add(sPane, gbc);

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

		/* priority radio buttons */
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
		} else if (eventName.contentEquals("Complete")) {
			actionItem.updateActionItem(actionItem.getTitle(),
					Priority.COMPLETED, actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());
		}

		/* scheduling checkboxes */
		else if (event.getSource()==urgentBy) {
			uMonth.setEnabled(urgentBy.isSelected());
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

		/* scheduling drop downs */
		
		LocalDateTime newDate=null;
		if (event.getSource()==uMonth||event.getSource()==cMonth||event.getSource()==eMonth
				||event.getSource()==uDay||event.getSource()==cDay||event.getSource()==eDay) {
			JComboBox cb = (JComboBox)event.getSource();
			String choice = (String)cb.getSelectedItem();
			
			if (event.getSource()==uMonth) {
				um=getMonth(choice);
				if (um!=0&&ud!=0&&uy!=0) {
					newDate=LocalDateTime.of(uy,um,ud,0,0);
					updateUBD(newDate);
				}
				uDay.requestFocusInWindow();
			} else if (event.getSource()==cMonth) {
				cm=getMonth(choice);
				if (cm!=0&&cd!=0&&cy!=0) {
					newDate=LocalDateTime.of(cy,cm,cd,0,0);
					updateCBD(newDate);
				}
				cDay.requestFocusInWindow();
			} else if (event.getSource()==eMonth) {
				em=getMonth(choice);
				if (em!=0&&ed!=0&&ey!=0) {
					newDate=LocalDateTime.of(ey,em,ed,0,0);
					updateEBD(newDate);
				}
				eDay.requestFocusInWindow();
			} else if (event.getSource()==uDay) {
				ud=getDay(choice);
				if (um!=0&&ud!=0&&uy!=0) {
					newDate=LocalDateTime.of(uy,um,ud,0,0);
					updateUBD(newDate);
				}
				uYear.requestFocusInWindow();
			} else if (event.getSource()==cDay) {
				cd=getDay(choice);
				if (cm!=0&&cd!=0&&cy!=0) {
					newDate=LocalDateTime.of(cy,cm,cd,0,0);
					updateCBD(newDate);
				}
				cYear.requestFocusInWindow();
			} else if (event.getSource()==eDay) {
				ed=getDay(choice);
				if (em!=0&&ed!=0&&ey!=0) {
					newDate=LocalDateTime.of(ey,em,ed,0,0);
					updateEBD(newDate);
				}
				eYear.requestFocusInWindow();
			}
		} else if (event.getSource()==uYear) {
			uy=getYear(uYear.getText());
			if (um!=0&&ud!=0&&uy!=0) {
				newDate=LocalDateTime.of(uy,um,ud,0,0);
				updateUBD(newDate);
			}
		} else if (event.getSource()==cYear) {
			cy=getYear(cYear.getText());
			if (cm!=0&&cd!=0&&cy!=0) {
				newDate=LocalDateTime.of(cy,cm,cd,0,0);
				updateCBD(newDate);
			}
		} else if (event.getSource()==eYear) {
			ey=getYear(eYear.getText());
			if (em!=0&&ed!=0&&ey!=0) {
				newDate=LocalDateTime.of(ey,em,ed,0,0);
				updateEBD(newDate);
			}
		}

		/* c,h,p buttons and name */
		else if (eventName.contentEquals("COMMENT")) {
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
		frame.setContentPane(new PrintActionItemScreen(actionItem, frame));
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

	private int getMonth(String month) {
		if (month.equals("Jan"))
			return 1;
		else if (month.equals("Feb"))
			return 2;
		else if (month.equals("Mar"))
			return 3;
		else if (month.equals("Apr"))
			return 4;
		else if (month.equals("May"))
			return 5;
		else if (month.equals("Jun"))
			return 6;
		else if (month.equals("Jul"))
			return 7;
		else if (month.equals("Aug"))
			return 8;
		else if (month.equals("Sep"))
			return 9;
		else if (month.equals("Oct"))
			return 10;
		else if (month.equals("Nov"))
			return 11;
		else if (month.equals("Dec"))
			return 12;
		else
			return 0;
	}

	private int getDay(String day) {
		return Integer.parseInt(day);
	}

	private int getYear(String year) {
		return Integer.parseInt(year);
	}

	private void updateUBD(LocalDateTime date) {
		actionItem.updateActionItem(actionItem.getTitle(),
				actionItem.getPriority(), date, actionItem.getCurrentByDate(),
				actionItem.getEventualByDate(), actionItem.getComment());
	}

	private void updateCBD(LocalDateTime date) {
		actionItem.updateActionItem(actionItem.getTitle(),
				actionItem.getPriority(), actionItem.getUrgentByDate(), date,
				actionItem.getEventualByDate(), actionItem.getComment());
	}

	private void updateEBD(LocalDateTime date) {
		actionItem.updateActionItem(actionItem.getTitle(),
				actionItem.getPriority(), actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
				date, actionItem.getComment());
	}

}
