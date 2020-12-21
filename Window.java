import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import org.json.JSONException;

import backend.FileUtilities;
import backend.ToDoList;

class Window {
	private JFrame frame;
	private MenuBar menuBar;
	private MainScreen main;

	Window() {
		frame = new JFrame("To Do List");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main = new MainScreen(frame);
		File saveFile = new File("save.tdl");
		if (saveFile.exists() && saveFile.canRead()) {
			ToDoList savedList;
			try {
				savedList = FileUtilities.restoreFrom("save.tdl");
				main.setToDoList(savedList);
			} catch (JSONException | FileNotFoundException e) {}
		}
		menuBar = new MenuBar(frame, main);
		frame.setJMenuBar(menuBar);
		frame.setContentPane(main);
		frame.pack();
		frame.setVisible(true);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					FileUtilities.writeBackup(main.getToDoList(), "save.tdl");
				} catch (IOException e) {}
			}
		});
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}
}