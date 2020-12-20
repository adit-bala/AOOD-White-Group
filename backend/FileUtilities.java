package backend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import org.json.*;

public class FileUtilities {

	public static void writeBackup(ToDoList toDoList, String filePath) throws IOException {
		JSONObject json = new JSONObject();
		JSONArray incompletes = new JSONArray();
		for (int i = 0; i < toDoList.getNumIncompleteItems(); i++) {
			incompletes.put(new JSONObject(toDoList.getIncompleteItemAtIndex(i)));
		}
		json.put("incompleteItems", incompletes);
		JSONArray completes = new JSONArray();
		for (int i = 0; i < toDoList.getNumCompleteItems(); i++) {
			completes.put(new JSONObject(toDoList.getCompleteItemAtIndex(i)));
		}
		json.put("completeItems", completes);
		
		FileWriter file = new FileWriter(filePath);
		file.write(json.toString(4));
		file.flush();
		file.close();
	}

	public static ToDoList restoreFrom(String filePath) throws JSONException, FileNotFoundException {
		ToDoList list = new ToDoList();
		JSONObject source = new JSONObject(new JSONTokener(new FileReader(filePath)));
		JSONArray completeItems = source.getJSONArray("completeItems");
		for (int i = completeItems.length() - 1; i >= 0; i--) {
			JSONObject itemSource = completeItems.getJSONObject(i);
			list.addCompleteActionItem(JSONToActionItem(itemSource));
		}
		JSONArray incompleteItems = source.getJSONArray("incompleteItems");
		for (int i = incompleteItems.length() - 1; i >= 0; i--) {
			JSONObject itemSource = incompleteItems.getJSONObject(i);
			list.addActionItem(JSONToActionItem(itemSource));
		}
		return list;
	}

	private static ActionItem JSONToActionItem(JSONObject itemSource) {
		ActionItem item = new ActionItem();
		item.setTitle(itemSource.getString("title"));
		item.setPriority(Priority.valueOf(itemSource.getString("priority")));
		item.setComment(itemSource.getString("comment"));
		if (itemSource.has("urgentByDate"))
			item.setUrgentByDate(LocalDateTime.parse(itemSource.getString("urgentByDate")));
		if (itemSource.has("currentByDate"))
			item.setCurrentByDate(LocalDateTime.parse(itemSource.getString("currentByDate")));
		if (itemSource.has("eventualByDate"))
			item.setEventualByDate(LocalDateTime.parse(itemSource.getString("eventualByDate")));
		if (itemSource.has("completedByDate"))
			item.setCompletedByDate(LocalDateTime.parse(itemSource.getString("completedByDate")));
		JSONArray history = itemSource.getJSONArray("history");
		for (int j = 0; j < history.length(); j++) {
			JSONObject eventSource = history.getJSONObject(j);
			int type = eventSource.getInt("type");
			LocalDateTime dateTime = LocalDateTime.parse(eventSource.getString("dateTime"));
			if (type == HistoryEvent.COMMENT_CHANGE) {
				item.addEventToHistory(new CommentChangeEvent(dateTime, eventSource.getInt("commentChangeEventType"),
						eventSource.getString("comment")));
			} else if (type == HistoryEvent.PRIORITY_CHANGE) {
				item.addEventToHistory(
						new PriorityChangeEvent(dateTime, Priority.valueOf(eventSource.getString("oldPriority")),
								Priority.valueOf(eventSource.getString("newPriority"))));
			} else {
				item.addEventToHistory(new TitleChangeEvent(dateTime, eventSource.getString("oldTitle"),
						eventSource.getString("newTitle")));
			}
		}
		return item;
	}

	public static boolean printPanel(JPanel panel) throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		Printer p = new Printer(panel);
		p.setPageFormat(job.pageDialog(job.defaultPage()));
		job.setPrintable(p);
		job.setPageable(p);
		boolean ok = job.printDialog();
		if (ok) {
			job.print();
		}
		return ok;
	}

//	public static void main(String[] args) {
//		ToDoList list = new SampleToDoList();
//		list.moveActionItem(2, 0);
//		writeBackup(list, "test.json");
//	}
}

class Printer implements Printable, Pageable {

	private Component compToPrint;
	private PageFormat format;
	private int numPages;

	Printer(Component comp) {
		this.compToPrint = comp;
		Dimension totalSpace = this.compToPrint.getSize();
		format = PrinterJob.getPrinterJob().defaultPage();
		numPages = (int) Math.ceil(totalSpace.height / format.getImageableHeight());
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page < 0 || page >= numPages)
			return NO_SUCH_PAGE;

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX() - 40, pf.getImageableY() - page * pf.getImageableHeight() - 20);
		disableDoubleBuffering(compToPrint);
		compToPrint.paint(g2d);
		enableDoubleBuffering(compToPrint);

		return PAGE_EXISTS;
	}
	
    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }

	@Override
	public int getNumberOfPages() {
		return numPages;
	}

	@Override
	public PageFormat getPageFormat(int arg0) throws IndexOutOfBoundsException {
		return format;
	}
	
	public void setPageFormat(PageFormat format) {
		this.format = format;
	}

	@Override
	public Printable getPrintable(int arg0) throws IndexOutOfBoundsException {
		return this;
	}
}