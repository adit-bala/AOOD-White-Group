import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Dimension;

class MainScreen extends JPanel {
	private JLabel pageTitle;
	private JScrollPane scrollPane;
	private JPanel itemPanel;
	private List<DefaultListModel<ActionItemEntry>> itemLists;
	private ActionItemEntry[] items;
	private ToDoList userList;
	private MouseListener mouseListener = new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	JList<ActionItemEntry> list = (JList<ActionItemEntry>) e.getSource();
	    	int index = list.locationToIndex(e.getPoint());
	    	if (index >= 0) {
	    		ActionItemEntry o = (ActionItemEntry) list.getModel().getElementAt(index);
		    	if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("Right clicked on item " + o.getActionItem() + "!");
					// TODO: open popup
				} else if (e.getClickCount() == 2) {
					System.out.println("Double clicked on item " + o.getActionItem() + "!");
		    		// TODO: open edit action item window
		    	}
		    }
	    }
	    public void mousePressed(MouseEvent e) {
	    	
	    }
	    public void mouseReleased(MouseEvent e) {
	    	
	    }
	};
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
		makeItemList();
		renderAllItemLists();
		scrollPane = new JScrollPane(itemPanel);
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(200, 500));
		this.add(scrollPane);
	}
	private void makeItemList() {
		items = new ActionItemEntry[userList.getNumActionItems()];
		for (int i=0;i<userList.getNumActionItems();i++) {
			items[i] = makeEntry(userList.getIncompleteActionItemAtIndex(i));
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
            private int index;
            private boolean beforeIndex = false; //Start with `false` therefore if it is removed from or added to the list it still works
            private boolean last = false;
            
            @Override
            public int getSourceActions(JComponent comp) {
                return MOVE;
            }

            @Override
            public Transferable createTransferable(JComponent comp) {
                index = list.getSelectedIndex(); 
                if (index == actionItemEntries.size() - 1)
                	last = true;
                else
                	last = false;
                return actionItemEntries.get(index); // new Transferable
            }

            @Override
            public void exportDone(JComponent comp, Transferable trans, int action) {
            	// refresh all priorities here (if greater than previous...)
                if (action == MOVE) {
                    if(beforeIndex && !last) {
                        actionItemEntries.remove(index + 1);
                    }
                    else {
                        actionItemEntries.remove(index);
                    }
                    
                }
            }

            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(ActionItemEntry.actionFlavor); // change to action item flavor
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                try {
                    ActionItemEntry s = (ActionItemEntry) support.getTransferable().getTransferData(ActionItemEntry.actionFlavor); // get transferred action item
                    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
                    actionItemEntries.add(dl.getIndex(), s); // action item
                    beforeIndex = dl.getIndex() < index ? true : false;
                    //updatePriorities();
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
		JLabel label = new JLabel(d.toString());
		label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		panel.add(label);
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
						itemPanel.add(makeDateLabel(currDate));
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