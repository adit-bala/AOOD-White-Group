import java.awt.Color;

import javax.swing.*;

class ActionItemEntry extends JPanel {
	private JLabel taskName;
	private ActionItem item;
	ActionItemEntry(ActionItem item) {
		this.item = item;
		taskName = new JLabel(item.getTitle());
		this.add(taskName);
		this.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 100));
		this.setBackground(new Color(220, 220, 220));
	}
}