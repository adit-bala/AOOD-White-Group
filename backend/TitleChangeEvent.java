package backend;

import java.time.LocalDate;

public class TitleChangeEvent extends HistoryEvent{
	
	private String oldTitle;
	private String newTitle;
	
	TitleChangeEvent(LocalDate dateTime, String oldTitle, String newTitle){
		super(dateTime);
		this.oldTitle = oldTitle;
		this.newTitle = newTitle;
		TYPE = HistoryEvent.TITLE_CHANGE;
	}
	
	public String label() {
		return("Title changed from \"" + oldTitle + "\" to \"" + newTitle + "\"");
	}

}
