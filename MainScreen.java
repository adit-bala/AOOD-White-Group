import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

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

class MainScreen extends JPanel implements ActionListener{
	private JFrame frame;
	private JLabel pageTitle;
	private JScrollPane scrollPane;
	private JPanel itemPanel;
	private List<DefaultListModel<ActionItemEntry>> itemLists;
	private ArrayList<ActionItemEntry> items;
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
	    	list.setSelectedIndex(index);
	    	if (index >= 0) {
	    		ActionItemEntry o = (ActionItemEntry) list.getModel().getElementAt(index);
		    	if (SwingUtilities.isRightMouseButton(e)) {
					//System.out.println("Right clicked on item " + o.getActionItem() + "!");
					setPopup(list, e.getX(), e.getY(), o);
				} else if (e.getClickCount() == 2) {
					//System.out.println("Double clicked on item " + o.getActionItem() + "!");
		    		setActionItemScreen(items.get(o.getIndex()).getActionItem());
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
		this.setBackground(Color.white);
		pageTitle = new JLabel("TO-DO");
		pageTitle.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		titlePanel.setBackground(Color.white);
		JPanel underline = new RoundedPanel(10, Color.decode("#56997F"), Color.WHITE);
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(pageTitle);
		titlePanel.add(underline);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
		titlePanel.setAlignmentX(LEFT_ALIGNMENT);
		this.add(titlePanel);
		userList.updateListOrder();
		initItemList();
		renderAllItemLists();
		scrollPane = new JScrollPane(itemPanel);
		scrollPane.setBorder(null);
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		this.setPreferredSize(new Dimension(1024, 1366)); //height too big for most laptop screens so won't be proportional
		this.add(scrollPane);
		NewActionItem = new JTextField("New Action Item...", 100);
		NewActionItem.setToolTipText("Please enter the name of a new action item");
		NewActionItem.setHorizontalAlignment(JTextField.CENTER);
		NewActionItem.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Regular.ttf", 14));
		NewActionItem.setBackground(Color.white);
		NewActionItem.setForeground(Color.gray.brighter());
		NewActionItem.setColumns(30);
		NewActionItem.setBorder(BorderFactory.createCompoundBorder(
                new CustomBorder(), 
                new EmptyBorder(new Insets(15, 25, 15, 25))));
		NewActionItem.setMaximumSize(new Dimension(9999, 100));
		NewActionItem.addActionListener(this);
		NewActionItem.setAlignmentX(LEFT_ALIGNMENT);
		this.add(NewActionItem);
	}
	private void setActionItemScreen(ActionItem item) {
		((MenuBar)frame.getJMenuBar()).changeBar();
		frame.setContentPane(new EditActionItemScreen(item, frame));
		frame.revalidate();
		frame.repaint();
	}
	private void setPopup(JList<ActionItemEntry> list, int x, int y, final ActionItemEntry itemEntry) {
		JPopupMenu menu = new JPopupMenu("Menu");
		JMenuItem Complete = new JMenuItem("Mark as complete");
		JMenuItem Edit = new JMenuItem("Edit item");
		JMenuItem Delete = new JMenuItem("Delete item");
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Complete") {
					userList.completeActionItem(items.get(itemEntry.getIndex()).getActionItem());
					items.remove(items.get(itemEntry.getIndex()));
					rerenderItemLists();
				} else if (e.getActionCommand() == "Edit") {
					setActionItemScreen(items.get(itemEntry.getIndex()).getActionItem());
				} else if (e.getActionCommand() == "Delete") {
					int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the item '" + itemEntry.getActionItem().getTitle() + "' ?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (choice == JOptionPane.YES_OPTION) {
						userList.deleteActionItem(items.get(itemEntry.getIndex()).getActionItem());
						items.remove(items.get(itemEntry.getIndex()));
						rerenderItemLists();
					}
				}
			}
		};
		Complete.addActionListener(listener);
		Complete.setActionCommand("Complete");
		Edit.addActionListener(listener);
		Edit.setActionCommand("Edit");
		Delete.addActionListener(listener);
		Delete.setActionCommand("Delete");
		menu.add(Complete);
		menu.add(Edit);
		menu.add(Delete);
        menu.show(list, x, y);
	}
	private void setHistoryScreen(ActionItem item) {
		((MenuBar)frame.getJMenuBar()).changeBar();
		frame.setContentPane(new HistoryScreen(item, frame));
		frame.revalidate();
		frame.repaint();
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
			inactive[i].setEventualByDate(LocalDateTime.now().plusDays(2));
			inactive2[i] = new ActionItem();
			inactive2[i].setPriority(Priority.INACTIVE);
			inactive2[i].setTitle("Other Inactive Action Item");
			inactive2[i].setEventualByDate(LocalDateTime.now().plusDays(4));
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
	 
	private void initItemList() { // only call first time
		items = new ArrayList<ActionItemEntry>();
		for (int i=0;i<userList.getNumIncompleteItems();i++) {
			items.add(makeEntry(userList.getIncompleteItemAtIndex(i)));
		}
		/*
		Collections.sort(items, new Comparator<ActionItemEntry>() {
			@Override
		    public int compare(ActionItemEntry o1, ActionItemEntry o2) {
				ActionItem item1 = o1.getActionItem();
				ActionItem item2 = o2.getActionItem();
				if (item1.getPriority() == Priority.INACTIVE && item2.getPriority() == Priority.INACTIVE)
					return item1.getEventualByDate().compareTo(item2.getEventualByDate());
		        return item1.getPriority().compareTo(item2.getPriority());
		    }
		});
		*/
	}
	private void rerenderItemLists() {
		scrollPane.getViewport().remove(itemPanel);
		userList.updateListOrder();
		initItemList();
		renderAllItemLists();
		scrollPane.getViewport().add(itemPanel);
		frame.revalidate();
		frame.repaint();
	}
	private void renderItemList(List<ActionItemEntry> listItems) {
		final DefaultListModel<ActionItemEntry> actionItemEntries = new DefaultListModel<ActionItemEntry>();
		for (ActionItemEntry entry : listItems)
			actionItemEntries.addElement(entry);
		itemLists.add(actionItemEntries);
		final JList<ActionItemEntry> list = new JList<ActionItemEntry>(actionItemEntries) {
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
	            		//System.out.println("Successful");
	            		rerenderItemLists();
	            		/*
	            		actionItemEntries.remove(actionItemEntries.indexOf(transferItem));
	            		if (actionItemEntries.size() == 0) {
	            			if (Arrays.asList(itemPanel.getComponents()).indexOf(list.getParent()) != 0) {
		            			itemPanel.remove(Arrays.asList(itemPanel.getComponents()).indexOf(list.getParent())-1);
		            			itemPanel.remove(list.getParent());
	            			}
	            		}
	            		*/
	            	}
	            }

	            @Override
	            public boolean canImport(TransferHandler.TransferSupport support) {
	                return support.isDataFlavorSupported(ActionItemEntry.actionFlavor);
	            }

	            @Override
	            public boolean importData(TransferHandler.TransferSupport support) {
	                try {
	                    ActionItemEntry itemEntry = (ActionItemEntry) support.getTransferable().getTransferData(ActionItemEntry.actionFlavor);
	                    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	                    //items.remove(itemEntry.getIndex());
	                    int prevIndex = itemEntry.getIndex();
	                    int newIndex = 0;
	                    //updateAllIndexes();
	                    int dropIndex = dl.getIndex();
	                    //System.out.println(dropIndex);
	                    boolean newList = !actionItemEntries.contains(transferItem);
	                    //actionItemEntries.add(dropIndex, itemEntry);
	                    if (dropIndex < actionItemEntries.size()) {
	                    	//items.add(actionItemEntries.get(dropIndex+1).getIndex(), itemEntry);
	                    	newIndex = actionItemEntries.get(dropIndex).getIndex();
	                    	if (newIndex > prevIndex) newIndex--;
	                    }
	                    else if (dropIndex > 0) {
	                    	//items.add(Math.min(items.size(), actionItemEntries.get(dropIndex-1).getIndex()+1), itemEntry);
	                    	newIndex = Math.min(items.size()-1, actionItemEntries.get(dropIndex - 1).getIndex());
	                    	if (newIndex < prevIndex) newIndex++;
	                    }	
	                    //updateAllIndexes();
	                    //System.out.print("Old index " + prevIndex);
	                    //System.out.println(" to new index " + newIndex);
	                    //System.out.println(actionItemEntries.get(dropIndex - 1).getActionItem().getPriority());
	                    if (dropIndex == actionItemEntries.size() && dropIndex > 0 && actionItemEntries.get(dropIndex - 1).getActionItem().getPriority() != Priority.INACTIVE) {
	                    	updateActionItemPriority(itemEntry, actionItemEntries.get(dropIndex - 1).getActionItem().getPriority());
	                    	//userList.moveActionItem(prevIndex, newIndex);
	                    }
	                    if (prevIndex != newIndex)
	                    	userList.moveActionItem(prevIndex, newIndex);
	                    if (newList) {
	                    	//System.out.println("new list");
	                    	if (dropIndex < actionItemEntries.size()) {
                    			updateActionItemPriority(itemEntry, actionItemEntries.get(dropIndex).getActionItem().getPriority());
                    		} else if (dropIndex > 0) {
                    			Priority prevPriority = actionItemEntries.get(dropIndex - 1).getActionItem().getPriority();
                    			if (prevPriority == Priority.INACTIVE)
                    				updateActionItemPriority(itemEntry, Priority.INACTIVE);
                    			else
                    				updateActionItemPriority(itemEntry, Priority.EVENTUAL);
                    		} else {
                    			updateActionItemPriority(itemEntry, Priority.EVENTUAL);
                    		}
	                    }
	                    ActionItem item = items.get(itemEntry.getIndex()).getActionItem();
	                    if (item.getPriority() == Priority.INACTIVE) {
	                    	if (dropIndex > 0) {
	                    		item.setEventualByDate(actionItemEntries.get(dropIndex - 1).getActionItem().getEventualByDate());
	                    	} else if (dropIndex < actionItemEntries.size()) {
	                    		item.setEventualByDate(actionItemEntries.get(dropIndex).getActionItem().getEventualByDate());
	                    	}
	                    	itemEntry.getActionItem().setEventualByDate(item.getEventualByDate());
	                    }
	                    //printData(item);
	                    return true;
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                return false;
	            }
	        });
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			p.add(list);
			p.setAlignmentX(LEFT_ALIGNMENT);
			p.setMaximumSize(new Dimension(p.getMaximumSize().width, p.getPreferredSize().height));
			itemPanel.add(p);
			itemPanel.add(Box.createVerticalStrut(20));
		}
	public void setToDoList(ToDoList list) {
		this.userList = list;
		this.rerenderItemLists();
	}
	public ToDoList getToDoList() {
		return this.userList;
	}
	private void updateAllIndexes() {
		for (int i=0;i<items.size();i++) {
			items.get(i).setIndex(i);
			if (i > 0)
				items.get(i).setPrev(items.get(i-1));
			else
				items.get(i).setPrev(null);
			if (i < items.size()-1)
				items.get(i).setNext(items.get(i+1));
			else
				items.get(i).setNext(null);
		}
	}
	private void updateActionItemPriority(ActionItemEntry itemEntry, Priority p) {
		ActionItem item = items.get(itemEntry.getIndex()).getActionItem();
		item.updateActionItem(item.getTitle(), p, item.getUrgentByDate(), item.getCurrentByDate(), item.getEventualByDate(), item.getComment());
	}

	private JPanel makeDateLabel(LocalDateTime d) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM uuuu");
		String formattedString = d.format(formatter);
		JLabel label = new JLabel(formattedString);
		label.setFont(HEADING_FONT);
		panel.add(label);
		panel.setAlignmentX(LEFT_ALIGNMENT);
		panel.setMaximumSize(panel.getPreferredSize());
		return panel;
	}
	private void renderAllItemLists() { // assumes list already sorted properly
		int start = 0;
		itemLists = new ArrayList<DefaultListModel<ActionItemEntry>>();
		itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		itemPanel.setBackground(Color.WHITE);
		itemPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		itemPanel.add(Box.createVerticalStrut(15));
		updateAllIndexes();
		if (items.size() > 0 && items.get(0).getActionItem().getPriority() == Priority.INACTIVE) {
			renderItemList(items.subList(0, 0));
		}
		for (int i=0;i<items.size();i++) {
			Priority currPriority = items.get(i).getActionItem().getPriority();
			LocalDateTime currDate = items.get(i).getActionItem().getEventualByDate();
			if (i > 0) {
				Priority prevPriority = items.get(i-1).getActionItem().getPriority();
				LocalDateTime prevDate = items.get(i-1).getActionItem().getEventualByDate();
				if (currPriority == Priority.INACTIVE) {
					if (prevPriority != Priority.INACTIVE) {
						renderItemList(items.subList(start, i));
						start = i;
					} else if (!prevDate.toString().equals(currDate.toString())) {
						itemPanel.add(makeDateLabel(prevDate));
						itemPanel.add(Box.createVerticalStrut(5));
						renderItemList(items.subList(start, i));
						start = i;
					}
				}
			}
		}
		if (items.size() > 0) {
			if (items.get(start).getActionItem().getPriority() == Priority.INACTIVE) {
				itemPanel.add(makeDateLabel(items.get(start).getActionItem().getEventualByDate()));
				itemPanel.add(Box.createVerticalStrut(5));
			}
			renderItemList(items.subList(start, items.size()));
		}
		itemPanel.add(Box.createGlue());
	}
	
	private void printData(ActionItem item) {
		System.out.println("ACTION ITEM");
		System.out.println("      Title: " + item.getTitle());
		System.out.println("      Priority: " + item.getPriority());
		System.out.println("      Urgent By: " + item.getUrgentByDate());
		System.out.println("      Current By: " + item.getCurrentByDate());
		System.out.println("      Eventual By: " + item.getEventualByDate());
		System.out.println("      Completed By: " + item.getCompletedByDate());
		System.out.println("      Active By: " + item.getActiveByDate());
		System.out.println("      Comment: " + item.getComment());
		System.out.println("      History: " + item.getHistory());
	}

	private ActionItemEntry makeEntry(ActionItem item) {
		ActionItemEntry entry = new ActionItemEntry(item);
		return entry;
	}
	
	public void actionPerformed(ActionEvent event) {
		ActionItem test = new ActionItem();
		test.setTitle(NewActionItem.getText());
		test.setPriority(Priority.URGENT);
		//test.setUrgentByDate(LocalDateTime.now());
		//test.setEventualByDate(LocalDateTime.now());
		//test.setCurrentByDate(LocalDateTime.now());
		//test.setCompletedByDate(LocalDateTime.now());
		test.setComment("comment");
		userList.addActionItem(test);
		scrollPane.getViewport().remove(itemPanel);
		boolean added = false;
		for (int i=0;i<items.size();i++) {
			if (!added && items.get(i).getActionItem().getPriority().compareTo(test.getPriority()) >= 0) {
				items.add(i, makeEntry(test));
				added = true;
			}
		}
		if (!added)
			items.add(makeEntry(test));
		renderAllItemLists();
		scrollPane.getViewport().add(itemPanel);
		frame.revalidate();
	}
	/*
	public List<ActionItem> orderByDate {

	}
	*/
	
}