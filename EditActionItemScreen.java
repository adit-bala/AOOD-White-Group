import backend.ActionItem;
import backend.FileUtilities;
import backend.FontLoader;
import backend.Priority;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.swing.border.LineBorder;

/* Displays ways for users to manipulate action item data.

 */
public class EditActionItemScreen extends JPanel implements ActionListener, FocusListener {
	private JFrame frame;

	private JLabel pageTitle;
	private JTextField name, uYear, cYear, eYear;
	private String uYearText, cYearText, eYearText;
	private JComboBox<String> uMonth, cMonth, eMonth, uDay, cDay, eDay;
	private JRadioButton urgent, current, eventual, inactive,
	complete;
	private JCheckBox urgentBy, currentBy, eventualBy;
	JButton save, cancel, comment, history, print;
	private ActionItem actionItem;
	private int um=0,ud=0,uy=0,cm=0,cd=0,cy=0,em=0,ed=0,ey=0;

	// added JLabels for "Priority" and "Scheduling" headers
	private JLabel nHead, pHead, sHead;
	JPanel titlePane;
	JPanel namePane;
	JPanel pPane;
	JPanel sPane;

	public static final Font TITLE_FONT = FontLoader.loadFont
			("src/res/EBGaramond/static/EBGaramond-ExtraBold"
					+ ".ttf", 80);
	public static final Font HEAD_FONT = FontLoader.loadFont
			("src/res/EBGaramond/static/EBGaramond-ExtraBold"
					+ ".ttf", 50);
	public static final Font BODY_FONT = FontLoader.loadFont
			("src/res/Chivo/Chivo-Regular.ttf", 36);
	public static final Font BUTTON_FONT = FontLoader.loadFont
			("src/res/Chivo/Chivo-Bold.ttf", 30);

	public static final Color EMERALD = new Color(19,77,55);
	public static final Color TEAL = new Color(86,153,127);
	public static final Color DUST = new Color(235,235,235);
	public static final Color TEAL_SELECT = new Color(86,153,127,200);

	EditActionItemScreen(ActionItem item, JFrame frame) {
		this.frame = frame;
		actionItem = item;

		/* main panel setup */
		setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
		setLayout(new GridBagLayout());
		setBackground(Color.white);
		
		JLabel titleLabel = new JLabel("Edit Action Item");
		titleLabel.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.setBackground(Color.WHITE);
		JPanel underline = new JPanel();
		underline.setBorder(new LineBorder(Color.decode("#56997F"), 5, true));
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.insets = new Insets(0, 10, 0, 0);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(titlePanel, gbc);

		/* action item name */
		namePane = new JPanel();
		namePane.setBackground(Color.WHITE);
		namePane.setLayout(new BoxLayout(namePane, BoxLayout.Y_AXIS));
		nHead = new JLabel("Name");
		nHead.setFont(HEAD_FONT);
		nHead.setAlignmentX(LEFT_ALIGNMENT);
		namePane.add(nHead);
		name = new JTextField(actionItem.getTitle());
		name.setMaximumSize(new Dimension(500, 9999));
		name.setFont(BODY_FONT);
		name.setBackground(DUST);
		name.setForeground(EMERALD);
		name.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		name.setSelectionColor(TEAL_SELECT);
		name.setSelectedTextColor(Color.white);
		name.addActionListener(this);
		name.addFocusListener(this);
		name.setAlignmentX(LEFT_ALIGNMENT);
		namePane.add(Box.createVerticalStrut(5));
		namePane.add(name);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 10, 15, 0);
		add(namePane, gbc);

		/* priority panel and header */
		pPane = new JPanel();
		pPane.setLayout(new BoxLayout(pPane,BoxLayout.Y_AXIS));
		pPane.setBackground(Color.WHITE);
		pHead = new JLabel("Priority");
		pHead.setFont(HEAD_FONT);
		pHead.setAlignmentX(LEFT_ALIGNMENT);
		pPane.add(pHead);

		/* priority radio buttons */
		ButtonGroup group = new ButtonGroup();
		urgent = new JRadioButton("Urgent", new CustomRadioButton());
		urgent.setSelectedIcon(new CustomSelectedRadioButton());
		urgent.setIconTextGap(12);
		current = new JRadioButton("Current", new CustomRadioButton());
		current.setSelectedIcon(new CustomSelectedRadioButton());
		current.setIconTextGap(12);
		eventual = new JRadioButton("Eventual", new CustomRadioButton());
		eventual.setSelectedIcon(new CustomSelectedRadioButton());
		eventual.setIconTextGap(12);
		inactive = new JRadioButton("Inactive", new CustomRadioButton());
		inactive.setSelectedIcon(new CustomSelectedRadioButton());
		inactive.setIconTextGap(12);
		complete = new JRadioButton("Complete", new CustomRadioButton());
		complete.setSelectedIcon(new CustomSelectedRadioButton());
		complete.setIconTextGap(12);
		urgent.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 36));
		current.setFont(BODY_FONT);
		eventual.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-LightItalic.ttf", 36));
		inactive.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Italic.ttf", 36));
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
		pPane.add(Box.createVerticalStrut(10));
		urgent.setAlignmentX(LEFT_ALIGNMENT);
		pPane.add(urgent);
		pPane.add(Box.createVerticalStrut(10));
		current.setAlignmentX(LEFT_ALIGNMENT);
		pPane.add(current);
		pPane.add(Box.createVerticalStrut(10));
		eventual.setAlignmentX(LEFT_ALIGNMENT);
		pPane.add(eventual);
		pPane.add(Box.createVerticalStrut(10));
		inactive.setAlignmentX(LEFT_ALIGNMENT);
		pPane.add(inactive);
		pPane.add(Box.createVerticalStrut(10));
		complete.setAlignmentX(LEFT_ALIGNMENT);
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
		gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(pPane, gbc);

		/* scheduling panel and header */
		sPane = new JPanel();
		GridBagConstraints sgbc = new GridBagConstraints();
		sgbc.anchor=GridBagConstraints.FIRST_LINE_START;
		sPane.setLayout(new GridBagLayout());
		sPane.setBackground(Color.WHITE);
		sgbc.gridx=0;
		sgbc.gridy=0;
		sgbc.gridwidth = 4;
		sgbc.insets = new Insets(0, 0, 10, 0);
		sHead = new JLabel("Scheduling");
		sHead.setFont(HEAD_FONT);
		sPane.add(sHead,sgbc);
		sgbc.gridwidth = 1;
		
		/* scheduling checkboxes and comboboxes */
		urgentBy = new JCheckBox("Urgent by: ", new CustomCheckBox());
		urgentBy.setSelectedIcon(new CustomSelectedCheckBox());
		urgentBy.setIconTextGap(12);
		currentBy = new JCheckBox("Current by: ", new CustomCheckBox());
		currentBy.setSelectedIcon(new CustomSelectedCheckBox());
		currentBy.setIconTextGap(12);
		eventualBy = new JCheckBox("Eventual by: ", new CustomCheckBox());
		eventualBy.setSelectedIcon(new CustomSelectedCheckBox());
		eventualBy.setIconTextGap(12);
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
		sgbc.insets = new Insets(0, 0, 10, 5);
		sgbc.gridx=1;
		uMonth = new JComboBox<>();
		uMonth.setModel(customComboBoxModel("mm"));
		addMonths(uMonth);
		cMonth = new JComboBox<>();
		cMonth.setModel(customComboBoxModel("mm"));
		addMonths(cMonth);
		eMonth = new JComboBox<>();
		eMonth.setModel(customComboBoxModel("mm"));
		addMonths(eMonth);
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
		uDay = new JComboBox<>();
		uDay.setModel(customComboBoxModel("dd"));
		addDays(uDay);
		cDay = new JComboBox<>();
		cDay.setModel(customComboBoxModel("dd"));
		addDays(cDay);
		eDay = new JComboBox<>();
		eDay.setModel(customComboBoxModel("dd"));
		addDays(eDay);
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
		uYear.setMinimumSize(new Dimension(100, 53));
		uYear.setPreferredSize(new Dimension(100, 53));
		uYear.addFocusListener(this);
		cYear = new JTextField("yyyy");
		cYear.setMinimumSize(new Dimension(100, 53));
		cYear.setPreferredSize(new Dimension(100, 53));
		cYear.addFocusListener(this);
		eYear = new JTextField("yyyy");
		eYear.setMinimumSize(new Dimension(100, 53));
		eYear.setPreferredSize(new Dimension(100, 53));
		eYear.addFocusListener(this);
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
		uYear.addFocusListener(new FocusListener() {  
		    public void focusGained(FocusEvent e) {  
		    	uYear.setText(uYearText);  
		    }  
		    public void focusLost(FocusEvent e) { 
	        	uYearText = uYear.getText();
		        if (uYearText.length() == 0) {  
		        	uYear.setText("yyyy");  
		        }
		    }  
		});
		cYear.addFocusListener(new FocusListener() {  
		    public void focusGained(FocusEvent e) {  
		    	cYear.setText(cYearText);  
		    }  
		    public void focusLost(FocusEvent e) { 
	        	cYearText = cYear.getText();
		        if (cYearText.length() == 0) {  
		        	cYear.setText("yyyy");  
		        }
		    }  
		});
		eYear.addFocusListener(new FocusListener() {  
		    public void focusGained(FocusEvent e) {  
		    	eYear.setText(eYearText);  
		    }  
		    public void focusLost(FocusEvent e) { 
	        	eYearText = eYear.getText();
		        if (eYearText.length() == 0) {  
		        	eYear.setText("yyyy");  
		        }
		    }  
		});
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
			uy = item.getUrgentByDate().getYear();
			uYearText = uy + "";
		}
		if (!(item.getCurrentByDate()==null)) {
			cMonth.setEnabled(true);
			cDay.setEnabled(true);
			cYear.setEnabled(true);
			currentBy.setSelected(true);
			cMonth.setSelectedIndex(item.getCurrentByDate().getMonthValue());
			cDay.setSelectedIndex(item.getCurrentByDate().getDayOfMonth());
			cYear.setText(Integer.toString(item.getCurrentByDate().getYear()));	
			cy = item.getCurrentByDate().getYear();
			cYearText = cy + "";
		}
		if (!(item.getEventualByDate()==null)) {
			eMonth.setEnabled(true);
			eDay.setEnabled(true);
			eYear.setEnabled(true);
			eventualBy.setSelected(true);
			eMonth.setSelectedIndex(item.getEventualByDate().getMonthValue());
			eDay.setSelectedIndex(item.getEventualByDate().getDayOfMonth());
			eYear.setText(Integer.toString(item.getEventualByDate().getYear()));	
			ey = item.getEventualByDate().getYear();
			eYearText = ey + "";
		}

		/* add scheduling panel to main */
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx=1;
		gbc.gridy=2;
		gbc.gridwidth = 2;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 10, 0, 10);
		add(sPane, gbc);

		/* bottom buttons and pane */
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
		comment.setPreferredSize(new Dimension(0, 60));
		comment.setMinimumSize(new Dimension(0, 60));
		history.setPreferredSize(new Dimension(0, 60));
		history.setMinimumSize(new Dimension(0, 60));
		print.setPreferredSize(new Dimension(0, 60));
		print.setMinimumSize(new Dimension(0, 60));
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(20, 10, 0, 10);
		gbc.weightx = 1;
		gbc.weighty = 0.1;
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(comment,gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		add(history, gbc);
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(print, gbc);
	}
	
	private DefaultComboBoxModel<String> customComboBoxModel(final String unselectable) {
		return new DefaultComboBoxModel<String>() {
			boolean selectionAllowed = true;
		    public void setSelectedItem(Object object) {
		    	if (!unselectable.equals(object)) {
		    		super.setSelectedItem(object);
		    	} else if (selectionAllowed) {
		    		selectionAllowed = false;
		    		super.setSelectedItem(object);
		    	}
		    }
	    };
	}
	
	private void addMonths(JComboBox c) {
		String[] months = {"mm","Jan", "Feb", "Mar", "Apr", "May", 
			"Jun","Jul","Aug", "Sep", "Oct", "Nov", "Dec"};
		for (String month : months) {
			c.addItem(month);
		}
	}
	
	private void addDays(JComboBox c) {
		String[] days = new String[32];
		days[0]="dd";
		for (int i=1;i<32;i++) {
			days[i]=Integer.toString(i);
		}
		for (String day : days) {
			c.addItem(day);
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
			if (urgentBy.isSelected()) {
				if (um!=0&&ud!=0&&uy!=0) {
					updateUBD(LocalDateTime.of(uy,um,ud,0,0));
				}
			} else {
				updateUBD(null);
			}
		} else if (event.getSource()==currentBy) {
			cMonth.setEnabled(currentBy.isSelected());
			cDay.setEnabled(currentBy.isSelected());
			cYear.setEnabled(currentBy.isSelected());
			if (currentBy.isSelected()) {
				if (cm!=0&&cd!=0&&cy!=0) {
					updateUBD(LocalDateTime.of(cy,cm,cd,0,0));
				}
			} else {
				updateCBD(null);
			}
		} else if (event.getSource()==eventualBy) {
			eMonth.setEnabled(eventualBy.isSelected());
			eDay.setEnabled(eventualBy.isSelected());
			eYear.setEnabled(eventualBy.isSelected());
			if (eventualBy.isSelected()) {
				if (em!=0&&ed!=0&&ey!=0) {
					updateUBD(LocalDateTime.of(ey,em,ed,0,0));
				}
			} else {
				updateEBD(null);
			}
		} else {

			/* scheduling drop downs */
			int dateChange = -1;
			LocalDateTime newDate=null;
			if (event.getSource()==uMonth||event.getSource()==cMonth||event.getSource()==eMonth
					||event.getSource()==uDay||event.getSource()==cDay||event.getSource()==eDay) {
				JComboBox cb = (JComboBox)event.getSource();
				String choice = (String)cb.getSelectedItem();
				if (event.getSource()==uMonth) {
					um=getMonth(choice);
					dateChange = 0;
					uDay.requestFocusInWindow();
				} else if (event.getSource()==cMonth) {
					cm=getMonth(choice);
					dateChange = 1;
					cDay.requestFocusInWindow();
				} else if (event.getSource()==eMonth) {
					em=getMonth(choice);
					dateChange = 2;
					eDay.requestFocusInWindow();
				} else if (event.getSource()==uDay) {
					ud=getDay(choice);
					dateChange = 0;
					uYear.requestFocusInWindow();
				} else if (event.getSource()==cDay) {
					cd=getDay(choice);
					dateChange = 1;
					cYear.requestFocusInWindow();
				} else if (event.getSource()==eDay) {
					ed=getDay(choice);
					dateChange = 2;
					eYear.requestFocusInWindow();
				}
			} else if (event.getSource()==uYear) {
				uy=getYear(uYear.getText());
				dateChange = 0;
			} else if (event.getSource()==cYear) {
				cy=getYear(cYear.getText());
				dateChange = 1;
			} else if (event.getSource()==eYear) {
				ey=getYear(eYear.getText());
				dateChange = 2;
			}
			if (dateChange == 0) {
				if (um!=0&&ud!=0&&uy!=0) {
					newDate=LocalDateTime.of(uy,um,ud,0,0);
					updateUBD(newDate);
				}
			} else if (dateChange == 1) {
				if (cm!=0&&cd!=0&&cy!=0) {
					newDate=LocalDateTime.of(cy,cm,cd,0,0);
					updateCBD(newDate);
				}
			} else if (dateChange == 2) {
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
//				System.out.println("pressed enter, current name: "+actionItem.getTitle());
			}
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

	@Override
	public void focusGained(FocusEvent e) {
//		System.out.println("focus gained, current name: "+
//				actionItem.getTitle());
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource()==uYear) {
			uy=getYear(uYear.getText());
			if (um!=0&&ud!=0&&uy!=0) {
				updateUBD(LocalDateTime.of(uy,um,ud,0,0));
			}
		} else if (e.getSource()==cYear) {
			cy=getYear(cYear.getText());
			if (cm!=0&&cd!=0&&cy!=0) {
				updateCBD(LocalDateTime.of(cy,cm,cd,0,0));
			}
		} else if (e.getSource()==eYear) {
			ey=getYear(eYear.getText());
			if (em!=0&&ed!=0&&ey!=0) {
				updateEBD(LocalDateTime.of(ey,em,ed,0,0));
			}
		} else {
			actionItem.updateActionItem(name.getText(),
					actionItem.getPriority(), actionItem.getUrgentByDate(), actionItem.getCurrentByDate(),
					actionItem.getEventualByDate(), actionItem.getComment());	
		}
//		System.out.println("focus lost, current name: "+actionItem.getTitle());
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
		try {
			return Integer.parseInt(day);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private int getYear(String year) {
		try {
			return Integer.parseInt(year);
		} catch (NumberFormatException e) {
			return 0;
		}
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
	
	class CustomRadioButton implements Icon {
		  private int w, h;
		
		  public CustomRadioButton() {
		    this.w = 30;
		    this.h = 30;
		  }
		
		  @Override
		  public void paintIcon(Component c, Graphics g, int x, int y) {
		    g.setColor(DUST);
		    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.fillOval(x, y, w, h);
		  }
		
		  @Override
		  public int getIconWidth() {
		    return w;
		  }
		
		  @Override
		  public int getIconHeight() {
		    return h;
		  }
	}
	
	class CustomSelectedRadioButton implements Icon {
		  private int w, h;
		
		  public CustomSelectedRadioButton() {
		    this.w = 30;
		    this.h = 30;
		  }
		
		  @Override
		  public void paintIcon(Component c, Graphics g, int x, int y) {
		    g.setColor(DUST);
		    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.fillOval(x, y, w, h);
		    g.setColor(TEAL);
		    g.fillOval(x + 6, y + 6, w - 12, h - 12);
		  }
		
		  @Override
		  public int getIconWidth() {
		    return w;
		  }
		
		  @Override
		  public int getIconHeight() {
		    return h;
		  }
	}
	
	class CustomCheckBox implements Icon {
		  private int w, h;
		
		  public CustomCheckBox() {
		    this.w = 30;
		    this.h = 30;
		  }
		
		  @Override
		  public void paintIcon(Component c, Graphics g, int x, int y) {
		    g.setColor(DUST);
		    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.fillRect(x, y, w, h);
		  }
		
		  @Override
		  public int getIconWidth() {
		    return w;
		  }
		
		  @Override
		  public int getIconHeight() {
		    return h;
		  }
	}
	
	class CustomSelectedCheckBox implements Icon {
		  private int w, h;
		
		  public CustomSelectedCheckBox() {
		    this.w = 30;
		    this.h = 30;
		  }
		
		  @Override
		  public void paintIcon(Component c, Graphics g, int x, int y) {
		    g.setColor(DUST);
		    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g.fillRect(x, y, w, h);
		    g.setColor(TEAL);
		    g.fillRect(x + 6, y + 6, w - 12, h - 12);
		  }
		
		  @Override
		  public int getIconWidth() {
		    return w;
		  }
		
		  @Override
		  public int getIconHeight() {
		    return h;
		  }
	}
}