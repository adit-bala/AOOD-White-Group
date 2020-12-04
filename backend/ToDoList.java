package backend;

import java.time.*;
import java.util.*;

public class ToDoList {
	private List<ActionItem> incompleteItems = new ArrayList<ActionItem>();
	private List<ActionItem> completeItems = new ArrayList<ActionItem>();

	public List<ActionItem> getIncompleteItems() {
		List<ActionItem> incompleteItems = new ArrayList<ActionItem>();
		for (int i = 0; i < this.incompleteItems.size(); i++) {
			incompleteItems.add(this.incompleteItems.get(i).copy());
		}
		return incompleteItems;
	}

	public List<ActionItem> getCompleteItems() {
		List<ActionItem> completeItems = new ArrayList<ActionItem>();
		for (int i = 0; i < this.completeItems.size(); i++) {
			completeItems.add(this.completeItems.get(i).copy());
		}
		return completeItems;
	}

	public void moveActionItem(int oldIndex, int newIndex) {
		ActionItem temp = incompleteItems.get(oldIndex);
		incompleteItems.remove(temp);
		incompleteItems.add(newIndex,temp);
		if (oldIndex<newIndex) {
			if (temp.getPriority() != incompleteItems.get(newIndex - 1).getPriority())
				temp.setPriority(incompleteItems.get(newIndex - 1).getPriority());
		} else {
			if (temp.getPriority() != incompleteItems.get(newIndex + 1).getPriority())
				temp.setPriority(incompleteItems.get(newIndex + 1).getPriority());
		}
	}

	public void completeActionItem(int index) {
		completeItems.add(incompleteItems.get(index));
		incompleteItems.remove(index);
	}

	public void updateActionItem(int index, String title, Priority priority,
			LocalDate urgentDate, LocalDate currentDate, LocalDate eventualDate,
			String comment) {
		ActionItem item = incompleteItems.get(index);
		if (!title.equals(item.getTitle())) {
			item.addEventToHistory(new TitleChangeEvent(LocalDate.now(),
					item.getTitle(), title));
			item.setTitle(title);
		}
		if (priority != item.getPriority()) {
			item.addEventToHistory(new PriorityChangeEvent(LocalDate.now(),
					item.getPriority(), priority));
			item.setPriority(priority);
		}
		item.setUrgentByDate(urgentDate);
		item.setCurrentByDate(currentDate);
		item.setEventualByDate(eventualDate);
		if (!comment.equals(item.getComment())) {
			int type = CommentChangeEvent.EDIT;
			if (comment.equals(""))
				type = CommentChangeEvent.DELETE;
			else if (item.getComment().equals(""))
				type = CommentChangeEvent.ADD;
			item.addEventToHistory(
					new CommentChangeEvent(LocalDate.now(), type, comment));
			item.setComment(comment);
		}
	}

	public void deleteActionItem(int index) {
		incompleteItems.remove(index);
	}

	public void addActionItem(ActionItem item) {
		incompleteItems.add(0, item);
	}
	
	
	// this is for restoring from a backup
	public void addCompleteActionItem(ActionItem item) {
		completeItems.add(0, item);
	}
}
