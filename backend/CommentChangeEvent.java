package backend;

import java.time.LocalDateTime;

public class CommentChangeEvent extends HistoryEvent {

	public static int ADD = 0;
	public static int EDIT = 1;
	public static int DELETE = 2;
	private int eventType;
	private String comment;

	CommentChangeEvent(LocalDateTime dateTime, int cceType, String comment) {
		super(dateTime, HistoryEvent.COMMENT_CHANGE);
		this.eventType = cceType;
		this.comment = comment;
	}

	public int getCommentChangeEventType() {
		return eventType;
	}
	
	public String getComment() {
		return comment;
	}

	public String label() {
		if (eventType == ADD)
			return ("Comment added: " + comment);
		else if (eventType == EDIT)
			return ("Comment changed: " + comment);
		else
			return ("Comment removed: " + comment);
	}

}
