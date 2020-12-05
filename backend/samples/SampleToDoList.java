package backend.samples;

import backend.ToDoList;

public class SampleToDoList extends ToDoList {
	public SampleToDoList() {
		addActionItem(new SampleActionItem5());
		addActionItem(new SampleActionItem3());
		addActionItem(new SampleActionItem2());
		addActionItem(new SampleActionItem1());
		addActionItem(new SampleActionItem4());

		addCompleteActionItem(new SampleActionItem6());
	}
}
