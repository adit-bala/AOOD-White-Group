package backend;

import java.time.*;
import java.util.*;

public class ToDoList {
	private List<ActionItem> incompleteItems = new ArrayList<ActionItem>();
	private List<ActionItem> completeItems = new ArrayList<ActionItem>();

	/*
	 * public List<ActionItem> getIncompleteItems() { List<ActionItem>
	 * incompleteItems = new ArrayList<ActionItem>(); for (int i = 0; i <
	 * this.incompleteItems.size(); i++) {
	 * incompleteItems.add(this.incompleteItems.get(i).copy()); } return
	 * incompleteItems; }
	 * 
	 * (public List<ActionItem> getCompleteItems() { List<ActionItem> completeItems
	 * = new ArrayList<ActionItem>(); for (int i = 0; i < this.completeItems.size();
	 * i++) { completeItems.add(this.completeItems.get(i).copy()); } return
	 * completeItems; }
	 */
	public ActionItem getIncompleteItemAtIndex(int index) {
		return incompleteItems.get(index);
	}

	public int getNumIncompleteItems() {
		return incompleteItems.size();
	}

	public ActionItem getCompleteItemAtIndex(int index) {
		return completeItems.get(index);
	}

	public int getNumCompleteItems() {
		return completeItems.size();
	}

	public void moveActionItem(int oldIndex, int newIndex) {
		ActionItem temp = incompleteItems.get(oldIndex);
		incompleteItems.remove(temp);
		incompleteItems.add(newIndex, temp);
		if (oldIndex < newIndex) {
			if (temp.getPriority() != incompleteItems.get(newIndex - 1).getPriority())
				temp.setPriority(incompleteItems.get(newIndex - 1).getPriority());
		} else {
			if (temp.getPriority() != incompleteItems.get(newIndex + 1).getPriority())
				temp.setPriority(incompleteItems.get(newIndex + 1).getPriority());
		}
	}

	public void completeActionItem(int index) {
		incompleteItems.get(index).setCompletedByDate(LocalDateTime.now());
		completeItems.add(incompleteItems.get(index));
		incompleteItems.remove(index);

	}

	public void deleteActionItem(int index) {
		incompleteItems.remove(index);
	}

	public void addActionItem(ActionItem item) {
		item.setPriority(Priority.URGENT);
		incompleteItems.add(0, item);
	}

	// this is for restoring from a backup
	public void addCompleteActionItem(ActionItem item) {
		completeItems.add(item);
	}
}
