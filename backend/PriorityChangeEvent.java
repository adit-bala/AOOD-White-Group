package backend;

import java.time.LocalDateTime;

public class PriorityChangeEvent extends HistoryEvent {
	private Priority oldPriority;
	private Priority newPriority;

	PriorityChangeEvent(LocalDateTime dateTime, Priority oldPriority,
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
