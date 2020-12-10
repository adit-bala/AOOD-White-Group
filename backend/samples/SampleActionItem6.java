package backend.samples;

import java.time.LocalDateTime;

import backend.ActionItem;
import backend.Priority;

public class SampleActionItem6 extends ActionItem {
	public SampleActionItem6() {
		super("Do homework", Priority.COMPLETED,
				null, null,
				null,
				"Nunc a tristique nulla, nec porta urna. Maecenas nec tortor lobortis neque lacinia sodales vel iaculis elit. In tristique molestie.");
	}
	
	public LocalDateTime getCompletedByDate() {
		return LocalDateTime.now();
	}	
	
}
