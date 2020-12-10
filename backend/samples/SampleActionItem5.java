package backend.samples;

import java.time.LocalDateTime;

import backend.ActionItem;
import backend.Priority;

public class SampleActionItem5 extends ActionItem {
	public SampleActionItem5() {
		super("Change fridge filter", Priority.INACTIVE,
				LocalDateTime.now().plusDays(55), LocalDateTime.now().plusDays(50),
				LocalDateTime.now().plusDays(20),
				"Morbi aliquet pretium tortor, nec bibendum justo ultricies ac. Phasellus laoreet ante sit amet bibendum dictum. In quis tempus nisl.");
	}
}
