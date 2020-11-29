import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

class MainScreen extends JPanel {
	private JLabel pageTitle;
	private List<JLabel> dates;
	private JScrollPane scrollPane;
	private JPanel itemList;
	private ToDoList userList;
	MainScreen() {
		userList = new ToDoList(); // where does this come from ?
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		this.setBackground(Color.white);
		pageTitle = new JLabel("TO-DO");
		pageTitle.setFont(new Font("Garamond", Font.BOLD, 100));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.setBackground(Color.white);
		JPanel underline = new JPanel();
		underline.setBackground(new Color(0, 200, 0));
		underline.setMaximumSize(new Dimension(800, 10));
		titlePanel.add(pageTitle);
		titlePanel.add(underline);
		this.add(titlePanel);
		itemList = new JPanel();
		itemList.setLayout(new BoxLayout(itemList, BoxLayout.Y_AXIS));
		for (int i=0;i<userList.getNumActionItems();i++) {
			JPanel margin = new JPanel();
			margin.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
			margin.setBackground(Color.white);
			itemList.add(margin);
			itemList.add(makeEntry(userList.getIncompleteActionItemAtIndex(i)));
		}
		scrollPane = new JScrollPane(itemList);
		scrollPane.setBorder(null);
		this.add(scrollPane);
		
	}
	private ActionItemEntry makeEntry(ActionItem item) {
		ActionItemEntry entry = new ActionItemEntry(item);
		entry.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Right clicked on item!");
					// TODO: open popup
				} else if (e.getClickCount() == 2) {
					System.out.println("Double clicked on item!");
		    		// TODO: open edit action item window
		    	}
		    }
		    public void mousePressed(MouseEvent e) {
		    	
		    }
		    public void mouseReleased(MouseEvent e) {
		    	
		    }
		});
		return entry;
	}
	public void moveItem(ActionItem item) {
		
	}
	public void priorityChange() {
		
	}
	/*
	public List<ActionItem> orderByDate {
		
	}
	*/
}