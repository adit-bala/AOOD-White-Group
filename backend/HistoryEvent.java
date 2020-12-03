package backend;

import java.time.LocalDate;

public abstract class HistoryEvent {
	public static final int TITLE_CHANGE = 0;
	public static final int PRIORITY_CHANGE = 1;
	public static final int COMMENT_CHANGE = 2;
	public int TYPE;
	
	private LocalDate dateTime;

	HistoryEvent(LocalDate time) {
		dateTime = time;
	}

	public LocalDate getDateTime() {
		return dateTime;
	}

	public abstract String label();

}
