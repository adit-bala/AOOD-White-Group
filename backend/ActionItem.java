package backend;

import java.util.ArrayList;

import org.json.JSONPropertyIgnore;

import java.io.Serializable;
import java.time.LocalDate;
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

	@JSONPropertyIgnore
	public LocalDateTime getActiveByDate() {
		if (urgentByDate == null) {
			if (currentByDate == null)
				return eventualByDate;
			else if (eventualByDate == null)
				return currentByDate;
			else
				return eventualByDate.compareTo(currentByDate) < 0 ? eventualByDate : currentByDate;
		} else {
			if (currentByDate == null) {
				if (eventualByDate == null)
					return urgentByDate;
				else
					return eventualByDate.compareTo(urgentByDate) < 0 ? eventualByDate : urgentByDate;
			} else {
				if (eventualByDate == null)
					return currentByDate.compareTo(urgentByDate) < 0 ? currentByDate : urgentByDate;
				else {
					LocalDateTime returned = eventualByDate.compareTo(currentByDate) < 0 ? eventualByDate : currentByDate;
					return returned.compareTo(urgentByDate) < 0 ? returned : urgentByDate;
				}
			}
		}
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
		LocalDate current = LocalDate.now();
		if (urgentByDate != null && current.compareTo(urgentByDate.toLocalDate()) >= 0) {
			priority = Priority.URGENT;
			urgentByDate = null;
			currentByDate = null;
			eventualByDate = null;
		} else if (currentByDate != null && current.compareTo(currentByDate.toLocalDate()) >= 0) {
			priority = Priority.CURRENT;
			currentByDate = null;
			eventualByDate = null;
		} else if (eventualByDate != null && current.compareTo(eventualByDate.toLocalDate()) >= 0) {
			priority = Priority.EVENTUAL;
			eventualByDate = null;
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
			if (this.comment.equals("")) {
				type = CommentChangeEvent.ADD;
				addEventToHistory(new CommentChangeEvent(LocalDateTime.now(), type, comment));
			} else if (comment.equals("")) {
				type = CommentChangeEvent.DELETE;
				addEventToHistory(new CommentChangeEvent(LocalDateTime.now(), type, this.comment));
			} else {
				addEventToHistory(new CommentChangeEvent(LocalDateTime.now(), type, comment));
			}
			setComment(comment);
		}
	}
}
