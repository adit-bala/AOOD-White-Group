import java.time.LocalDate;

public class PriorityChangeEvent extends Event{
	public static final int type = Event.Priority_Change;
	private Priority oldPriority;
	private Priority newPriority;
	
	PriorityChangeEvent(LocalDate dateTime, Priority OldPriority, Priority NewPriority){
		super(dateTime);
		oldPriority = OldPriority;
		newPriority = NewPriority;
	}
	
	public String label() {
		return ("Priority changed from " + oldPriority + " to " + newPriority);
	}


}
