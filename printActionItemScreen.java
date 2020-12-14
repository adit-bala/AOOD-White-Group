import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.time.LocalDate;

public class printActionItemScreen {
	JFrame frame;
    JPanel contentPane;
    JLabel label;
	public void displayActionItem(ActionItem item) {
		frame = new JFrame("Action Item Display Screen");
		String title = item.getTitle();
		String comment = item.getComment();
		LocalDate urgent = item.getUrgentByDate();
		LocalDate current = item.getCurrentByDate();
		LocalDate eventual = item.getEventualByDate();
		
		contentPane = new JPanel();
		label = new JLabel("<html>Title: " + title + "<br/>urgentByDate: " + urgent + "<br/>currentByDate: " + current + "<br/>eventualByDate: " + eventual + "<br/>Comment: " + comment + "<br/>History: " + "</html>");
		contentPane.add(label);
		frame.setContentPane(contentPane);
    	frame.pack();
    	frame.setVisible(true);

		
	}
}
