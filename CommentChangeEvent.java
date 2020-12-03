import java.time.LocalDate;

public class CommentChangeEvent extends Event{
	
	public static int ADD;
	public static int EDIT;
	public static int DELETE;
	public static final int type = Event.Comment_Change;
	private int eventType;
	private String comment;
	
	CommentChangeEvent(LocalDate dateTime, int Type, String Comment){
		super(dateTime);
		eventType = Type;
		comment = Comment;
	}
	
	public int getType() {
		return eventType;
	}
	
	public String label() {
		return("Comment added: " + comment);
	}


}
