import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.CompoundBorder;

import backend.ActionItem;
import backend.Priority;

class ActionItemEntry extends JLabel
		implements ListCellRenderer<Object>, Transferable {
	// private ActionItem item;
	private JPanel panel;
	private ActionItem item;
	protected static DataFlavor actionFlavor = new DataFlavor(
			ActionItemEntry.class, "Action Item Entry");
	protected static DataFlavor[] supportedFlavors = { actionFlavor };

	ActionItemEntry(ActionItem item) {
		setOpaque(true);
		this.item = item;
		this.panel = new JPanel();
	}

	public ActionItem getActionItem() {
		return this.item;
	}

	public String toString() {
		return item.getTitle();
	}

	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		panel.removeAll();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5), BorderFactory.createLineBorder(new Color(230, 230, 230), 15, true)));
		panel.setBackground(Color.white);
		this.setText(value.toString());	
		Priority p = ((ActionItemEntry) value).getActionItem().getPriority();
		if (p == Priority.URGENT)
			this.setFont(MainScreen.BOLD_FONT);
		else if (p == Priority.INACTIVE)
			this.setFont(MainScreen.ITALIC_FONT);
		else if (p == Priority.EVENTUAL)
			this.setFont(MainScreen.LIGHT_ITALIC_FONT);
		else
			this.setFont(MainScreen.NORMAL_FONT);
		this.setForeground(Color.decode("#134D37"));
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 300));
		this.setBackground(new Color(230, 230, 230));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(this);
		return panel;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		// TODO Auto-generated method stub
		return supportedFlavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		// TODO Auto-generated method stub
		return flavor.equals(actionFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		// TODO Auto-generated method stub
		if (flavor.equals(actionFlavor))
			return this;
		else
			throw new UnsupportedFlavorException(flavor);
	}

}