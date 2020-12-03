import java.time.LocalDate;

public class TitleChangeEvent extends Event{
	
	public static final int type = Event.Title_Change;
	private String oldTitle;
	private String newTitle;
	
	TitleChangeEvent(LocalDate dateTime, String OldTitle, String NewTitle){
		super(dateTime);
		oldTitle = OldTitle;
		newTitle = NewTitle;
	}
	
	public String label() {
		return("Title changed from \"" + oldTitle + "\" to \"" + newTitle + "\"");
	}



}
