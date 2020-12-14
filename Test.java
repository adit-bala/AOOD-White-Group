import java.time.LocalDate;

public class Test {
	public static void main(String[] args) {
		/*
		LocalDate date = LocalDate.now();  
	    LocalDate yesterday = date.minusDays(10);  
	    LocalDate tomorrow = date.plusDays(1);
		ActionItem noob = new ActionItem();
		noob.setUrgentByDate(date);
		noob.setCurrentByDate(yesterday);
		noob.setEventualByDate(tomorrow);
		noob.getActiveByDate();
		*/
		/*
		TitleChangeEvent test = new TitleChangeEvent(LocalDate.now(), "haha", "hahahha");
		System.out.println(test.label());
		*/
		/*
		PriorityChangeEvent test2 = new PriorityChangeEvent(LocalDate.now(), Priority.Urgent, Priority.Eventual);
		System.out.println(test2.label());
		*/
		
		/*
		
		CommentChangeEvent test3 = new CommentChangeEvent(LocalDate.now(), 1, "haha");
		System.out.println(test3.label());
		*/
		
		LocalDate date = LocalDate.now();
		ActionItem test = new ActionItem();
		test.setComment("reeeeeeeeeee");
		test.setTitle("Sample Title");
		test.setUrgentByDate(date);
		test.setCurrentByDate(date.plusDays(1));
		test.setEventualByDate(date.plusDays(2));
		printActionItemScreen screen = new printActionItemScreen();
		screen.displayActionItem(test);
	}
}
