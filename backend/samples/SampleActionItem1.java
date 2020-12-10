package backend.samples;

import java.time.LocalDateTime;

import backend.ActionItem;
import backend.Priority;

public class SampleActionItem1 extends ActionItem {
	public SampleActionItem1() {
		super("Get groceries", Priority.CURRENT, LocalDateTime.now().plusDays(1),
				null, null,
				"Mauris a metus vitae massa scelerisque viverra. Aliquam mollis ante nec rhoncus posuere. Duis at venenatis odio, vitae venenatis lacus.");
	}
}
