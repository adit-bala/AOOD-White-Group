package backend;

import java.time.LocalDate;

import org.json.JSONPropertyName;

public abstract class HistoryEvent {
	public static final int TITLE_CHANGE = 0;
	public static final int PRIORITY_CHANGE = 1;
	public static final int COMMENT_CHANGE = 2;
	
	private int type;
	
	private LocalDate dateTime;

	HistoryEvent(LocalDate time, int type) {
		this.type = type;
		dateTime = time;
	}
	
	public int getType() {
		return type;
	}

	public LocalDate getDateTime() {
		return dateTime;
	}

	@JSONPropertyName("label")
	public abstract String label();

}
