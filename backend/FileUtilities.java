package backend;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JPanel;

import org.json.*;

import backend.samples.SampleToDoList;

public class FileUtilities {

	public static void writeBackup(ToDoList toDoList, String filePath) {
		JSONObject json = new JSONObject(toDoList);
		try {
			FileWriter file = new FileWriter(filePath);
			file.write(json.toString(4));
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ToDoList restoreFrom(String filePath) {
		ToDoList list = new ToDoList();
		try {
			JSONObject source = new JSONObject(
					new JSONTokener(new FileReader(filePath)));
			JSONArray completeItems = source.getJSONArray("completeItems");
			for (int i = completeItems.length() - 1; i >= 0; i--) {
				JSONObject itemSource = completeItems.getJSONObject(i);
				list.addCompleteActionItem(JSONToActionItem(itemSource));
			}
			JSONArray incompleteItems = source.getJSONArray("incompleteItems");
			for (int i = incompleteItems.length() - 1; i >= 0; i--) {
				JSONObject itemSource = incompleteItems.getJSONObject(i);
				list.addCompleteActionItem(JSONToActionItem(itemSource));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private static ActionItem JSONToActionItem(JSONObject itemSource) {
		ActionItem item = new ActionItem();
		item.setTitle(itemSource.getString("title"));
		item.setPriority(Priority.valueOf(itemSource.getString("priority")));
		item.setComment(itemSource.getString("comment"));
		if (itemSource.has("urgentByDate"))
			item.setUrgentByDate(
					LocalDate.parse(itemSource.getString("urgentByDate")));
		if (itemSource.has("currentByDate"))
			item.setCurrentByDate(
					LocalDate.parse(itemSource.getString("currentByDate")));
		if (itemSource.has("eventualByDate"))
			item.setEventualByDate(
					LocalDate.parse(itemSource.getString("eventualByDate")));
		JSONArray history = itemSource.getJSONArray("history");
		for (int j = 0; j < history.length(); j++) {
			JSONObject eventSource = history.getJSONObject(j);
			int type = eventSource.getInt("type");
			LocalDate dateTime = LocalDate
					.parse(eventSource.getString("dateTime"));
			if (type == HistoryEvent.COMMENT_CHANGE) {
				item.addEventToHistory(new CommentChangeEvent(dateTime,
						eventSource.getInt("commentChangeEventType"),
						eventSource.getString("comment")));
			} else if (type == HistoryEvent.PRIORITY_CHANGE) {
				item.addEventToHistory(new PriorityChangeEvent(dateTime,
						Priority.valueOf(eventSource.getString("oldPriority")),
						Priority.valueOf(
								eventSource.getString("newPriority"))));
			} else {
				item.addEventToHistory(new TitleChangeEvent(dateTime,
						eventSource.getString("oldTitle"),
						eventSource.getString("newTitle")));
			}
		}
		return item;
	}

	public static void printToDoList(JPanel toDoListPanel)
			throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ToDoListPrinter(toDoListPanel));
		boolean ok = job.printDialog();
		if (ok) {
			job.print();
		}
	}

	public static void printActionItem(JPanel editItemPanel,
			JPanel commentPanel, JPanel historyPanel) throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ActionItemPrinter(editItemPanel, commentPanel,
				historyPanel));
		boolean ok = job.printDialog();
		if (ok) {
			job.print();
		}
	}

//	public static void main(String[] args) {
//		ToDoList list = new SampleToDoList();
//		list.moveActionItem(2, 0);
//		writeBackup(list, "test.json");
//	}
}

class ToDoListPrinter implements Printable {

	private Component compToPrint;

	ToDoListPrinter(Component comp) {
		this.compToPrint = comp;
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		if (page > 0)
			return NO_SUCH_PAGE;

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		compToPrint.printAll(g);

		return PAGE_EXISTS;
	}

}

class ActionItemPrinter implements Printable {

	private Component editComp, commentComp, historyComp;

	ActionItemPrinter(Component editComp, Component commentComp,
			Component historyComp) {
		this.editComp = editComp;
		this.commentComp = commentComp;
		this.historyComp = historyComp;
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		Component compToPrint;
		if (page == 0) {
			compToPrint = editComp;
		} else if (page == 1) {
			compToPrint = commentComp;
		} else if (page == 2) {
			compToPrint = historyComp;
		} else {
			return NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		compToPrint.printAll(g);

		return PAGE_EXISTS;
	}

}