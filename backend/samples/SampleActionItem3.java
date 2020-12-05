package backend.samples;

import java.time.LocalDate;

import backend.ActionItem;
import backend.Priority;

public class SampleActionItem3 extends ActionItem {
	public SampleActionItem3() {
		super("Vacuum car", Priority.INACTIVE, LocalDate.now().plusDays(30),
				LocalDate.now().plusDays(15), LocalDate.now().plusDays(1),
				"Aliquam pellentesque, enim accumsan dictum dictum, lorem lacus vehicula justo, non vestibulum diam augue et ligula. Integer rutrum quam et.");
	}
}
