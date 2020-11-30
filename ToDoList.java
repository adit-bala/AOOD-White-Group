import java.time.*;
import java.util.*;

public class ToDoList {
	private List<ActionItem> incompleteItems;
	private List<ActionItem> completeItems;

	public List<ActionItem> getIncompleteItems() {
		List<ActionItem> incompleteItems = new List<ActionItem>();
		for (int i=0; i<this.incompleteItems.size(); i++) {
			incompleteItems.add(this.incompleteItems.get(i).copy());
		}
		return incompleteItems;
	}

	public List<ActionItem> getCompleteItems() {
		List<ActionItem> completeItems = new List<ActionItem>();
		for (int i=0; i<this.completeItems.size(); i++) {
			completeItems.add(this.completeItems.get(i).copy());
		}
		return completeItems;
	}

	public void moveActionItem(int oldIndex, int newIndex) {
		ActionItem temp = incompleteItems.get(oldIndex);
		incompleteItems.add(temp, newIndex);
		if (incompleteItems.getPriority(newIndex) != incompleteItems.getPriority(newIndex - 1))
			incompleteItems.getPriority(newIndex).setPriority(incompleteItems.getPriority(newIndex - 1));
	}

	public void completeActionItem(int index) {
		completeItems.add(incompleteItems.get(index));
		incompleteItems.remove(index);
	}

	public void updateActionItem(int index, String title, Priority priority, LocalDate urgentDate,
			LocalDate currentDate, LocalDate eventualDate, String comment) {
		ActionItem item = incompleteItems.get(index);
		//incomplete
	}

	public void deleteActionItem(int index) {
		incompleteItems.remove(index);
	}

	public void addActionItem(ActionItem item) {
		incompleteItems.add(item, 0);
	}
}
