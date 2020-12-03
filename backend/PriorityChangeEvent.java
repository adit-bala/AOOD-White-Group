package backend;

import java.time.LocalDate;

public class PriorityChangeEvent extends HistoryEvent {
	private Priority oldPriority;
	private Priority newPriority;

	PriorityChangeEvent(LocalDate dateTime, Priority oldPriority,
			Priority newPriority) {
		super(dateTime);
		this.oldPriority = oldPriority;
		this.newPriority = newPriority;
		TYPE = HistoryEvent.PRIORITY_CHANGE;
	}

	public String label() {
		return ("Priority changed from " + oldPriority + " to " + newPriority);
	}

}
