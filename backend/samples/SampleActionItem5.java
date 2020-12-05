package backend.samples;

import java.time.LocalDate;

import backend.ActionItem;
import backend.Priority;

public class SampleActionItem5 extends ActionItem {
	public SampleActionItem5() {
		super("Change fridge filter", Priority.INACTIVE,
				LocalDate.now().plusDays(55), LocalDate.now().plusDays(50),
				LocalDate.now().plusDays(20),
				"Morbi aliquet pretium tortor, nec bibendum justo ultricies ac. Phasellus laoreet ante sit amet bibendum dictum. In quis tempus nisl.");
	}
}
