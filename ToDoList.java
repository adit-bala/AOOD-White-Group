import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.SwingUtilities;

/*
	Mock implementation for testing purposes.
	Delete this file when the actual class is pushed to main.
*/

class ToDoList {
	public int getNumActionItems() {
		return 10;
	}
	ActionItem getIncompleteActionItemAtIndex(int index) {
		if (index % 2 == 0)
			return getInactiveTestItem();
		else
			return getActiveTestItem();
	}
	private ActionItem getInactiveTestItem() {
		ActionItem test = new ActionItem();
		test.setTitle("Inactive ActionItem title");
		test.setPriority(Priority.URGENT);
		test.setEventualByDate(LocalDate.now().plusDays(2));
		
		return test;
	}
	private ActionItem getActiveTestItem() {
		ActionItem test = new ActionItem();
		test.setTitle("Active ActionItem title");
		test.setPriority(Priority.URGENT);
		test.setEventualByDate(LocalDate.now().minusDays(2));
		return test;
	}
}