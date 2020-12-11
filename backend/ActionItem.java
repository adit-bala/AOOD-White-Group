package backend;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ActionItem implements Serializable {

	private String title;
	private Priority priority;
	private LocalDateTime urgentByDate;
	private LocalDateTime currentByDate;
	private LocalDateTime eventualByDate;
	private LocalDateTime completedByDate;
	private String comment;
	private ArrayList<HistoryEvent> history = new ArrayList<HistoryEvent>();

	public ActionItem() {
	}

	// Convenience method so I can make sample action items easier
	protected ActionItem(String title, Priority priority, LocalDateTime urgentByDate, LocalDateTime currentByDate,
			LocalDateTime eventualByDate, String comment) {
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

	public LocalDateTime getUrgentByDate() {
		return urgentByDate;
	}

	public LocalDateTime getCurrentByDate() {
		return currentByDate;
	}

	public LocalDateTime getEventualByDate() {
		return eventualByDate;
	}

	public LocalDateTime getActiveByDate() {
		// does not account for same date
		LocalDateTime returned;
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

	public LocalDateTime getCompletedByDate() {
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

	public void setUrgentByDate(LocalDateTime newUrgentByDate) {
		urgentByDate = newUrgentByDate;
	}

	public void setCurrentByDate(LocalDateTime newCurrentByDate) {
		currentByDate = newCurrentByDate;
	}

	public void setEventualByDate(LocalDateTime newEventualByDate) {
		eventualByDate = newEventualByDate;
	}

	public void setComment(String newComment) {
		comment = newComment;
	}

	public void setCompletedByDate(LocalDateTime completedByDate) {
		this.completedByDate = completedByDate;
	}

	public void addEventToHistory(HistoryEvent newEvent) {
		history.add(newEvent);
	}

	public void updatePriority() {
		LocalDateTime current = LocalDateTime.now();
		if (current.compareTo(urgentByDate) < 0) {
			priority = Priority.URGENT;
		} else if (current.compareTo(currentByDate) < 0) {
			priority = Priority.CURRENT;
		} else if (current.compareTo(eventualByDate) < 0) {
			priority = Priority.EVENTUAL;
		}
	}

	public void updateActionItem(String title, Priority priority, LocalDateTime urgentDate, LocalDateTime currentDate,
			LocalDateTime eventualDate, String comment) {
		if (!title.equals(this.title)) {
			addEventToHistory(new TitleChangeEvent(LocalDateTime.now(), this.title, title));
			setTitle(title);
		}
		if (priority != this.priority) {
			addEventToHistory(new PriorityChangeEvent(LocalDateTime.now(), this.priority, priority));
			setPriority(priority);
		}
		setUrgentByDate(urgentDate);
		setCurrentByDate(currentDate);
		setEventualByDate(eventualDate);
		if (!comment.equals(this.comment)) {
			int type = CommentChangeEvent.EDIT;
			if (comment.equals(""))
				type = CommentChangeEvent.DELETE;
			else if (this.comment.equals(""))
				type = CommentChangeEvent.ADD;
			addEventToHistory(new CommentChangeEvent(LocalDateTime.now(), type, comment));
			setComment(comment);
		}
	}
}
