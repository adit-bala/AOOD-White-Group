import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

//import ClosedActionItemsScreen.CustomBorder;
import backend.ActionItem;
import backend.Priority;
import backend.ToDoList;
import backend.samples.SampleToDoList;
import backend.FontLoader;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Dimension;

class MainScreen extends JPanel {
	private JFrame frame;
	private JLabel pageTitle;
	private JScrollPane scrollPane;
	private JPanel itemPanel;
	private List<DefaultListModel<ActionItemEntry>> itemLists;
	private ActionItemEntry[] items;
	private ToDoList userList;
	private JTextField NewActionItem;
	public static final Color THEME_MEDIUM = Color.decode("#56997F");
	public static final Font TITLE_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 100);
	public static final Font HEADING_FONT = FontLoader.loadFont("src/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 30);
	public static final Font BOLD_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 15);
	public static final Font NORMAL_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Regular.ttf", 15);
	public static final Font ITALIC_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-Italic.ttf", 15);
	public static final Font LIGHT_ITALIC_FONT = FontLoader.loadFont("src/res/Chivo/Chivo-LightItalic.ttf", 15);
	private MouseListener mouseListener = new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	JList<ActionItemEntry> list = (JList<ActionItemEntry>) e.getSource();
	    	int index = list.locationToIndex(e.getPoint());
	    	if (index >= 0) {
	    		ActionItemEntry o = (ActionItemEntry) list.getModel().getElementAt(index);
		    	if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Right clicked on item " + o.getActionItem() + "!");
					setHistoryScreen(o.getActionItem());
				} else if (e.getClickCount() == 2) {
					System.out.println("Double clicked on item " + o.getActionItem() + "!");
		    		setActionItemScreen(o.getActionItem());
		    	}
		    }
	    }
	    public void mousePressed(MouseEvent e) {
	    	
	    }
	    public void mouseReleased(MouseEvent e) {
	    	
	    }
	};
	MainScreen(JFrame frame) {
		this.frame = frame;
		userList = new SampleToDoList(); // where does this come from ?
		//TEST(); // adds example action items
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		this.setBackground(Color.white);
		pageTitle = new JLabel("TO-DO");
		pageTitle.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.setBackground(Color.white);
		JPanel underline = new JPanel();
		underline.setBorder(new LineBorder(THEME_MEDIUM, 5, true));
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(pageTitle);
		titlePanel.add(underline);
		this.add(titlePanel);
		makeItemList();
		renderAllItemLists();
		scrollPane = new JScrollPane(itemPanel);
		scrollPane.setBorder(null);
		// this.setPreferredSize(new Dimension(1024, 1366)); height too big for most laptop screens so won't be proportional
		this.add(scrollPane);
		NewActionItem = new JTextField("New Action Item...", 100);
		NewActionItem.setToolTipText("Please enter the name of a new action item");
		NewActionItem.setHorizontalAlignment(JTextField.CENTER);
		NewActionItem.setFont(new Font("Chivo Regular", Font.PLAIN, 14));
		NewActionItem.setBackground(Color.white);
		NewActionItem.setForeground(Color.gray.brighter());
		NewActionItem.setColumns(30);
		NewActionItem.setBorder(BorderFactory.createCompoundBorder(
                new CustomBorder(), 
                new EmptyBorder(new Insets(15, 25, 15, 25))));
		NewActionItem.setMaximumSize(new Dimension(9999, 100));
		this.add(NewActionItem);
	}
	private void setActionItemScreen(ActionItem item) {
		frame.setContentPane(new EditActionItemScreen(item));
		frame.revalidate();
	}
	private void setHistoryScreen(ActionItem item) {
		frame.setContentPane(new HistoryScreen(item));
		frame.revalidate();
	}
	private void TEST() {
		ActionItem[] urgent = new ActionItem[5];
		ActionItem[] current = new ActionItem[5];
		ActionItem[] inactive = new ActionItem[5];
		ActionItem[] inactive2 = new ActionItem[5];
		for (int i=0;i<5;i++) {
			urgent[i] = new ActionItem();
			urgent[i].setPriority(Priority.URGENT);
			urgent[i].setTitle("Active Action Item");
			current[i] = new ActionItem();
			current[i].setPriority(Priority.CURRENT);
			current[i].setTitle("Current Action Item");
			inactive[i] = new ActionItem();
			inactive[i].setPriority(Priority.INACTIVE);
			inactive[i].setTitle("Inactive Action Item");
			inactive[i].setEventualByDate(LocalDate.now().plusDays(2));
			inactive2[i] = new ActionItem();
			inactive2[i].setPriority(Priority.INACTIVE);
			inactive2[i].setTitle("Other Inactive Action Item");
			inactive2[i].setEventualByDate(LocalDate.now().plusDays(4));
			userList.addActionItem(urgent[i]);
			userList.addActionItem(current[i]);
			userList.addActionItem(inactive[i]);
			userList.addActionItem(inactive2[i]);
		}
	}
	
	 @SuppressWarnings("serial")
	    class CustomBorder extends AbstractBorder{
	        @Override
	        public void paintBorder(Component c, Graphics g, int x, int y,
	                int width, int height) {
	            // TODO Auto-generated method stubs
	            super.paintBorder(c, g, x, y, width, height);
	            Graphics2D g2d = (Graphics2D)g;
	            g2d.setStroke(new BasicStroke(12));
	            g2d.setColor(THEME_MEDIUM);
	            g2d.drawRoundRect(x, y, width - 1, height - 1, 25, 25);
	        }   
	    }
	 
	private void makeItemList() {
		// changed this a little because the methods are different by Ms. Gerb request -Tyler
		items = new ActionItemEntry[userList.getIncompleteItems().size()];
		for (int i=0;i<userList.getIncompleteItems().size();i++) {
			items[i] = makeEntry(userList.getIncompleteItems().get(i));
		}
		Arrays.sort(items, new Comparator<ActionItemEntry>() {
			@Override
		    public int compare(ActionItemEntry o1, ActionItemEntry o2) {
				ActionItem item1 = o1.getActionItem();
				ActionItem item2 = o2.getActionItem();
				if (item1.getPriority() == Priority.INACTIVE && item2.getPriority() == Priority.INACTIVE)
					return item1.getEventualByDate().compareTo(item2.getEventualByDate());
		        return item1.getPriority().compareTo(item2.getPriority());
		    }
		});
	}
	private void renderItemList(ActionItemEntry[] items) {
		DefaultListModel<ActionItemEntry> actionItemEntries = new DefaultListModel<ActionItemEntry>();
		for (ActionItemEntry entry : items)
			actionItemEntries.addElement(entry);
		itemLists.add(actionItemEntries);
		JList<ActionItemEntry> list = new JList<ActionItemEntry>(actionItemEntries) {
	        @Override
	        public int locationToIndex(Point location) {
	            int index = super.locationToIndex(location);
	            if (index != -1 && !getCellBounds(index, index).contains(location)) {
	                return -1;
	            }
	            else {
	                return index;
	            }
	        }
	        @Override
	        public Rectangle getCellBounds(int index1, int index2) {
	        	Rectangle orig = super.getCellBounds(index1, index2);
	        	Rectangle withoutBorder = new Rectangle(orig.x + 10, orig.y + 10, orig.width - 20, orig.height - 20);
	        	return withoutBorder;
	        }
		};
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new ActionItemEntry(new ActionItem()));
		list.addMouseListener(this.mouseListener);
		list.setFixedCellHeight(70);
		//itemLists.add(list);
		list.setDragEnabled(true);
		list.setDropMode(DropMode.INSERT);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setTransferHandler(new TransferHandler() {
	           private Transferable transferItem;
	            
	            @Override
	            public int getSourceActions(JComponent comp) {
	                return MOVE;
	            }

	            @Override
	            public Transferable createTransferable(JComponent comp) {
	                int index = list.getSelectedIndex(); 
	                transferItem = actionItemEntries.get(index);
	                return transferItem;
	            }

	            @Override
	            public void exportDone(JComponent comp, Transferable trans, int action) {
	            	if (action == MOVE) {
	            		actionItemEntries.remove(actionItemEntries.indexOf(transferItem));
	            	}
	            }

	            @Override
	            public boolean canImport(TransferHandler.TransferSupport support) {
	                return support.isDataFlavorSupported(ActionItemEntry.actionFlavor);
	            }

	            @Override
	            public boolean importData(TransferHandler.TransferSupport support) {
	                try {
	                    ActionItemEntry item = (ActionItemEntry) support.getTransferable().getTransferData(ActionItemEntry.actionFlavor);
	                    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	                    int index = dl.getIndex();
	                    actionItemEntries.add(index, item);
	                    Priority itemPriority = item.getActionItem().getPriority();
	                    if (index > 0) {
	                    	ActionItemEntry prev = actionItemEntries.get(index-1);
	                    	Priority prevPriority = prev.getActionItem().getPriority();
	                    	LocalDate prevEventualByDate = prev.getActionItem().getEventualByDate();
	                    	if (prevPriority.compareTo(itemPriority) > 0) {
	                    		item.getActionItem().setPriority(prevPriority);
	                    		if (prevPriority == Priority.INACTIVE)
	                    			item.getActionItem().setEventualByDate(prevEventualByDate);
	                    	}
	                    } else {
	                    	if (itemPriority != Priority.INACTIVE) {
	                    		if (index < actionItemEntries.size()-1 &&  actionItemEntries.get(index+1).getActionItem().getPriority() == Priority.INACTIVE) {
	                    			item.getActionItem().setPriority(actionItemEntries.get(index+1).getActionItem().getPriority());
	                    			item.getActionItem().setEventualByDate(actionItemEntries.get(index+1).getActionItem().getEventualByDate());
	                    		}
	                    	}
	                    }
	                    if (index < actionItemEntries.size() - 1) {
	                    	ActionItemEntry next = actionItemEntries.get(index+1);
	                    	Priority nextPriority = next.getActionItem().getPriority();
	                    	LocalDate nextEventualByDate = next.getActionItem().getEventualByDate();
	                    	if (nextPriority.compareTo(itemPriority) < 0)
	                    		item.getActionItem().setPriority(nextPriority);
	                    	if (item.getActionItem().getPriority() == Priority.INACTIVE && nextPriority == Priority.INACTIVE)
	                    		item.getActionItem().setEventualByDate(nextEventualByDate);
	                    	
	                    } else {
	                    	if (itemPriority == Priority.INACTIVE) {
	                    		if (index > 0 &&  actionItemEntries.get(index-1).getActionItem().getPriority() != Priority.INACTIVE)
	                    			item.getActionItem().setPriority(actionItemEntries.get(index-1).getActionItem().getPriority());
	                    		else if (index == 0)
	                    			item.getActionItem().setPriority(Priority.EVENTUAL);
	                    	}
	                    }
	                    return true;
	                } catch (UnsupportedFlavorException | IOException e) {
	                    e.printStackTrace();
	                }

	                return false;
	            }
	        });
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			p.add(list);
			itemPanel.add(p);
		}
	private JPanel makeDateLabel(LocalDate d) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		String day = d.getDayOfWeek().toString();
		day = day.substring(0, 1) + day.substring(1).toLowerCase();
		String formattedString = day + ", " + d.format(formatter);
		JLabel label = new JLabel(formattedString);
		label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		label.setFont(HEADING_FONT);
		panel.add(label);
		panel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		return panel;
	}
	private void renderAllItemLists() { // assumes list already sorted properly
		int start = 0;
		itemLists = new ArrayList<DefaultListModel<ActionItemEntry>>();
		itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		itemPanel.setBackground(Color.WHITE);
		for (int i=0;i<items.length;i++) {
			Priority currPriority = items[i].getActionItem().getPriority();
			LocalDate currDate = items[i].getActionItem().getEventualByDate();
			if (i > 0) {
				Priority prevPriority = items[i-1].getActionItem().getPriority();
				LocalDate prevDate = items[i-1].getActionItem().getEventualByDate();
				if (currPriority == Priority.INACTIVE) {
					if (prevPriority != Priority.INACTIVE) {
						renderItemList(Arrays.copyOfRange(items, start, i));
						start = i;
					} else if (!prevDate.toString().equals(currDate.toString())) {
						itemPanel.add(makeDateLabel(prevDate));
						renderItemList(Arrays.copyOfRange(items, start, i));
						start = i;
					}
				}
			}
		}
		if (items[start].getActionItem().getPriority() == Priority.INACTIVE)
			itemPanel.add(makeDateLabel(items[start].getActionItem().getEventualByDate()));
		renderItemList(Arrays.copyOfRange(items, start, items.length));
	}

	private ActionItemEntry makeEntry(ActionItem item) {
		ActionItemEntry entry = new ActionItemEntry(item);
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