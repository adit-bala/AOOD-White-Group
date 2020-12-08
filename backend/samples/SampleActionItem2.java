package backend.samples;

import java.time.LocalDate;

import backend.ActionItem;
import backend.Priority;

public class SampleActionItem2 extends ActionItem {
	public SampleActionItem2() {
		super("Buy Christmas gifts", Priority.EVENTUAL,
				LocalDate.now().plusDays(15), LocalDate.now().plusDays(10),
				null,
				"In id arcu quis ipsum suscipit molestie non ac lectus. Vestibulum in nibh consectetur, interdum dui ultrices, venenatis nisi. Donec.");
	}
}
