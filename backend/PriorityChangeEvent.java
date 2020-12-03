package backend;

import java.time.LocalDate;

public class PriorityChangeEvent extends HistoryEvent {
	private Priority oldPriority;
	private Priority newPriority;

	PriorityChangeEvent(LocalDate dateTime, Priority oldPriority,
			Priority newPriority) {
		super(dateTime, HistoryEvent.PRIORITY_CHANGE);
		this.oldPriority = oldPriority;
		this.newPriority = newPriority;
	}
	
	public Priority getOldPriority() {
		return oldPriority;
	}
	
	public Priority getNewPriority() {
		return newPriority;
	}
	
	public String label() {
		return ("Priority changed from " + oldPriority + " to " + newPriority);
	}

}
