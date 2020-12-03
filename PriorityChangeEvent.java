import java.time.LocalDate;

public class PriorityChangeEvent extends Event{
	public static final int type = Event.Priority_Change;
	private Priority oldPriority;
	private Priority newPriority;
	
	PriorityChangeEvent(LocalDate dateTime, Priority oldPriority, Priority newPriority){
		super(dateTime);
	}
	
	public String label() {
		return ("Priority changed from " + oldPriority + " to " + newPriority);
	}


}
