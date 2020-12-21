import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import backend.ActionItem;
import backend.FontLoader;
import backend.Priority;
import backend.ToDoList;


/*
 * This class shows all events in an Action Item's history
 */
public class ClosedActionItemsScreen extends JPanel implements MouseListener, ActionListener {
	/*
	 * Instance variables
	 */
	private HashSet<LocalDate> dates = new HashSet<LocalDate>();
	private JPanel itemPanel;
	private ToDoList userList;
	private JFrame frame;
	private ActionItemEntry selectedEntry;
	// private JLabel dates;

	ClosedActionItemsScreen(JFrame frame, ToDoList userList) {
		this.userList = userList;
		this.frame = frame;
		ArrayList<ActionItem> test = new ArrayList<ActionItem>();
		for (int i = 0; i < userList.getNumCompleteItems(); i++) {
			test.add(userList.getCompleteItemAtIndex(i));
		}
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.WHITE);
		JLabel titleLabel = new JLabel("Closed Action Items");
		titleLabel.setFont(FontLoader.loadFont("/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80));
		JPanel underline = new RoundedPanel(10, Color.decode("#56997F"), Color.WHITE);
		underline.setMaximumSize(new Dimension(610, 10));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		titlePanel.setAlignmentX(LEFT_ALIGNMENT);
		this.add(titlePanel);

		itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		itemPanel.setBackground(Color.WHITE);
		for (ActionItem item : test) {
			dates.add(item.getCompletedByDate().toLocalDate());
		}
		for (LocalDate date : dates) {
			itemPanel.add(Box.createVerticalStrut(15));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM uuuu");
			JLabel newDate = new JLabel(date.format(formatter));
			newDate.setFont(FontLoader.loadFont("/res/EBGaramond/static/EBGaramond-Bold.ttf", 30));
			newDate.setAlignmentX(LEFT_ALIGNMENT);
			itemPanel.add(newDate);
			itemPanel.add(Box.createVerticalStrut(5));
			for (ActionItem item : test) {
				if (item.getCompletedByDate().toLocalDate().equals(date)) {
					ActionItemEntry entry = new ActionItemEntry(item);
					entry.addMouseListener(this);
					entry.setAlignmentX(LEFT_ALIGNMENT);
					itemPanel.add(entry);
				}
			}
		}
		itemPanel.add(Box.createVerticalStrut(15));
		itemPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		JScrollPane scrollPane = new JScrollPane(itemPanel);
		scrollPane.setBorder(null);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		this.add(scrollPane);
	}

	public void mouseClicked(MouseEvent e) {
		this.selectedEntry = (ActionItemEntry) e.getSource();
		if (SwingUtilities.isRightMouseButton(e)) {
			// System.out.println("Right clicked on item " + o.getActionItem() + "!");
			setPopup(e.getX(), e.getY());
		} else if (e.getClickCount() == 2) {
			// System.out.println("Double clicked on item " + o.getActionItem() + "!");
			setEditActionItemScreen(this.selectedEntry.getActionItem());
		}
	}

	private void setPopup(int x, int y) {
		JPopupMenu menu = new JPopupMenu("Menu");
		JMenuItem Complete = new JMenuItem("Mark as complete");
		JMenuItem Edit = new JMenuItem("Edit item");
		JMenuItem Delete = new JMenuItem("Delete item");
		Complete.setEnabled(false);
		Edit.addActionListener(this);
		Edit.setActionCommand("Edit");
		Delete.addActionListener(this);
		Delete.setActionCommand("Delete");
		menu.add(Complete);
		menu.add(Edit);
		menu.add(Delete);
		menu.show(selectedEntry, x, y);
	}

	private void setEditActionItemScreen(ActionItem item) {
		frame.setContentPane(new EditActionItemScreen(item, frame));
		((MenuBar) frame.getJMenuBar()).addPrevPanel(this);
		frame.revalidate();
		frame.repaint();
	}

	public void refreshEvents() {
		if (selectedEntry.getActionItem().getPriority() != Priority.COMPLETED) {
			userList.undoCompleteActionItem(selectedEntry.getActionItem());
			itemPanel.remove(selectedEntry);
			itemPanel.revalidate();
			itemPanel.repaint();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Edit") {
			setEditActionItemScreen(selectedEntry.getActionItem());
		} else if (e.getActionCommand() == "Delete") {
			int choice = JOptionPane.showConfirmDialog(selectedEntry,
					"Are you sure you want to delete the item '" + selectedEntry.getActionItem().getTitle() + "' ?",
					"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (choice == JOptionPane.YES_OPTION) {
				userList.deleteActionItem(selectedEntry.getActionItem());
				itemPanel.remove(selectedEntry);
				itemPanel.revalidate();
				itemPanel.repaint();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
