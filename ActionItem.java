import java.util.ArrayList;
import java.time.LocalDate;

public class ActionItem {
	
	private String title;
	private Priority priority;
	private LocalDate urgentByDate;
	private LocalDate currentByDate;
	private LocalDate eventualByDate;
	private String comment;
	private ArrayList <Event> history;
	
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
		//does not account for same date
		LocalDate returned;
		System.out.println(currentByDate);
		if (urgentByDate.compareTo(currentByDate) < 0) {
			returned = urgentByDate;
		}else {
			returned = currentByDate;
		}
		if(returned.compareTo(eventualByDate) > 0) {
			returned = eventualByDate;
		}
		return returned;
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
	
	public void addEventToHistory(Event newEvent) {
		history.add(newEvent);
	}
	
	public void updatePriority() {
		LocalDate current = LocalDate.now();
		if(current.compareTo(urgentByDate) < 0) {
			priority = Priority.Urgent;
		}
		else if(current.compareTo(currentByDate) < 0) {
			priority = Priority.Current;
		}
		else if(current.compareTo(eventualByDate) < 0) {
			priority = Priority.Eventual;
		}
	}
	
	public ActionItem copy() {
		ActionItem copy = new ActionItem();
		copy.setTitle(title);
		copy.setPriority(priority);
		copy.setUrgentByDate(urgentByDate);
		copy.setCurrentByDate(currentByDate);
		copy.setEventualByDate(eventualByDate);
		copy.setComment(comment);
		for(int i = 0; i < history.size(); i++) {
			copy.addEventToHistory(history.get(i));
		}
		return copy;
	}


}
