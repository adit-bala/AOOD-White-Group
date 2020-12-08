package backend;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;

public class ActionItem implements Serializable {

	private String title;
	private Priority priority;
	private LocalDate urgentByDate;
	private LocalDate currentByDate;
	private LocalDate eventualByDate;
	private LocalDate completedByDate;
	private String comment;
	private ArrayList<HistoryEvent> history = new ArrayList<HistoryEvent>();

	public ActionItem() {
	}

	// Convenience method so I can make sample action items easier
	protected ActionItem(String title, Priority priority,
			LocalDate urgentByDate, LocalDate currentByDate,
			LocalDate eventualByDate, String comment) {
		this.title = title;
		this.priority = priority;
		this.urgentByDate = urgentByDate;
		this.currentByDate = currentByDate;
		this.eventualByDate = eventualByDate;
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public Priority getPriority() {
		return priority;
	}

	public LocalDate getUrgentByDate() {
		return urgentByDate;
	}

	public LocalDate getCurrentByDate() {
		return currentByDate;
	}

	public LocalDate getEventualByDate() {
		return eventualByDate;
	}

	public LocalDate getActiveByDate() {
		// does not account for same date
		LocalDate returned;
		if (urgentByDate.compareTo(currentByDate) < 0) {
			returned = urgentByDate;
		} else {
			returned = currentByDate;
		}
		if (returned.compareTo(eventualByDate) > 0) {
			returned = eventualByDate;
		}
		return returned;
	}
	public LocalDate getCompletedByDate() {
		return completedByDate;
	}

	public String getComment() {
		return comment;
	}

	public ArrayList getHistory() {
		return history;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public void setPriority(Priority newPriority) {
		priority = newPriority;
	}

	public void setUrgentByDate(LocalDate newUrgentByDate) {
		urgentByDate = newUrgentByDate;
	}

	public void setCurrentByDate(LocalDate newCurrentByDate) {
		currentByDate = newCurrentByDate;
	}

	public void setEventualByDate(LocalDate newEventualByDate) {
		eventualByDate = newEventualByDate;
	}

	public void setComment(String newComment) {
		comment = newComment;
	}
	
	public void setCompletedByDate(LocalDate completedByDate) {
		this.completedByDate = completedByDate;
	}

	public void addEventToHistory(HistoryEvent newEvent) {
		history.add(newEvent);
	}

	public void updatePriority() {
		LocalDate current = LocalDate.now();
		if (current.compareTo(urgentByDate) < 0) {
			priority = Priority.URGENT;
		} else if (current.compareTo(currentByDate) < 0) {
			priority = Priority.CURRENT;
		} else if (current.compareTo(eventualByDate) < 0) {
			priority = Priority.EVENTUAL;
		}
	}

	public ActionItem copy() {
		ActionItem copy = new ActionItem(title, priority, urgentByDate,
				currentByDate, eventualByDate, comment);
		for (int i = 0; i < history.size(); i++) {
			copy.addEventToHistory(history.get(i));
		}
		return copy;
	}

}
