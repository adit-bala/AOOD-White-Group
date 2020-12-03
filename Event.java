import java.time.LocalDate;

public abstract class Event {
	public static int Title_Change;
	public static int Priority_Change;
	public static int Comment_Change;
	private LocalDate dateTime;
	
	Event (LocalDate time){
		dateTime = time;
	}
	
	public LocalDate getDateTime() {
		return dateTime;
	}
	
	public abstract String label();
		


}
