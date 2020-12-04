package backend;

import java.time.LocalDate;

public class CommentChangeEvent extends HistoryEvent {

	public static int ADD;
	public static int EDIT;
	public static int DELETE;
	private int eventType;
	private String comment;

	CommentChangeEvent(LocalDate dateTime, int cceType, String comment) {
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
		return ("Comment added: " + comment);
	}

}
