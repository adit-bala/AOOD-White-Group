package backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.json.*;

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

	public static void main(String[] args) {
		ToDoList list = new ToDoList();
		ActionItem item = new ActionItem();
		item.setTitle("Eat cuddy");
		item.setPriority(Priority.URGENT);
		item.setComment("");
		list.addActionItem(item);
		list.updateActionItem(0, "New Title", Priority.EVENTUAL,
				LocalDate.now(), null, null, "LOL");
		writeBackup(list, "test.json");
	}
}
