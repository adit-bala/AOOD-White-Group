package backend;

import java.time.LocalDate;

public class CommentChangeEvent extends HistoryEvent {

	public static int ADD;
	public static int EDIT;
	public static int DELETE;
	private int eventType;
	private String comment;

	CommentChangeEvent(LocalDate dateTime, int type, String comment) {
		super(dateTime);
		this.eventType = type;
		this.comment = comment;
		TYPE = HistoryEvent.COMMENT_CHANGE;
	}

	public int getType() {
		return eventType;
	}

	public String label() {
		return ("Comment added: " + comment);
	}

}
