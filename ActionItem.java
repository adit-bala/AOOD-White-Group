import java.time.LocalDate;

/*
	Mock implementation for testing purposes.
	Delete this file when the actual class is pushed to main.
*/

enum Priority {
	URGENT, CURRENT, EVENTUAL, INACTIVE, COMPLETED
}

class ActionItem {
	private String title;
	private LocalDate eventualByDate;
	private Priority priority;
	public String getTitle() {
		return this.title;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public void setEventualByDate(LocalDate eventualByDate) {
		this.eventualByDate = eventualByDate;
	}
}