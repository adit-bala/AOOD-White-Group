package backend;

import java.io.Serializable;
import java.time.LocalDateTime;

//import org.json.JSONPropertyName;

public abstract class HistoryEvent implements Serializable {
	public static final int TITLE_CHANGE = 0;
	public static final int PRIORITY_CHANGE = 1;
	public static final int COMMENT_CHANGE = 2;
	
	private int type;
	
	private LocalDateTime dateTime;

	HistoryEvent(LocalDateTime time, int type) {
		this.type = type;
		dateTime = time;
	}
	
	public int getType() {
		return type;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	//@JSONPropertyName("label")
	public abstract String label();

}
