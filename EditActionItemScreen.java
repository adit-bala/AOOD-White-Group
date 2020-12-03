
// import javax.swing.JPanel;
import javax.swing.*;

import backend.ActionItem;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;

/* Displays ways for users to manipulate action item data.

 */
public class EditActionItemScreen extends JPanel {
	private JLabel pageTitle;
	private JTextField name, uYear, cYear, eYear;
	private JComboBox uMonth, cMonth, eMonth, uDay, cDay, eDay;
	private JRadioButton urgent, current, eventual, inactive, complete;
	private JCheckBox urgentBy, currentBy, eventualBy;
	JButton save, cancel, comment, history, print;
	private ActionItem actionItem = new ActionItem();

	// added JLabels for "Priority" and "Scheduling" headers
	private JLabel pHead, sHead;

	EditActionItemScreen(ActionItem item) {
		actionItem = item;
		// setup: gridbag layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// label setup
		pageTitle = new JLabel("Edit Action Item");
		name = new JTextField(actionItem.getTitle());
		pHead = new JLabel("Priority");
		sHead = new JLabel("Scheduling");
		uYear = new JTextField("yyyy");
		cYear = new JTextField("yyyy");
		eYear = new JTextField("yyyy");
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		// use <>? look into
		uMonth = new JComboBox(months);
		cMonth = new JComboBox(months);
		eMonth = new JComboBox(months);
		Integer[] days = new Integer[31];
		// string or integer?
		int i = 1;
		for (Integer a : days) {
			a = i;
			i++;
		}
		uDay = new JComboBox(days);
		cDay = new JComboBox(days);
		eDay = new JComboBox(days);
		urgent = new JRadioButton("Urgent");
		current = new JRadioButton("Current");
		eventual = new JRadioButton("Eventual");
		inactive = new JRadioButton("Inactive");
		complete = new JRadioButton("Complete");
		urgentBy = new JCheckBox("Urgent by: ");
		currentBy = new JCheckBox("Current by: ");
		eventualBy = new JCheckBox("Eventual by: ");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		comment = new JButton("COMMENT");
		history = new JButton("HISTORY");
		print = new JButton("PRINT");

		this.add(pageTitle);

	}

	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.contentEquals("Save")) {
			System.out.println("Save");
			// update everything
		} else if (eventName.contentEquals("Cancel")) {
			// close window and don't save
		} else if (eventName.contentEquals("COMMENT")) {
			System.out.println("comment");
			// open comment screen
		} else if (eventName.contentEquals("HISTORY")) {
			System.out.println("History");
			// open history screen
		} else if (eventName.contentEquals("PRINT")) {
			System.out.println("print");
			// open print screen
		}
	}

	public static void main(String[] args) {
		EditActionItemScreen screen = new EditActionItemScreen();
		JFrame frame = new JFrame();
		frame.setContentPane(screen);
		frame.pack();
		frame.setVisible(true);
	}
}
