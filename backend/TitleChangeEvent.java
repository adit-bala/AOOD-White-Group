package backend;

import java.time.LocalDateTime;

public class TitleChangeEvent extends HistoryEvent{
	
	private String oldTitle;
	private String newTitle;
	
	TitleChangeEvent(LocalDateTime dateTime, String oldTitle, String newTitle){
		super(dateTime, HistoryEvent.TITLE_CHANGE);
		this.oldTitle = oldTitle;
		this.newTitle = newTitle;
	}
	
	public String getOldTitle() {
		return oldTitle;
	}
	
	public String getNewTitle() {
		return newTitle;
	}
	
	public String label() {
		return("Title changed from \"" + oldTitle + "\" to \"" + newTitle + "\"");
	}

}
