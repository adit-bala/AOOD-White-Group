import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.*;

class Window {
	private JFrame frame;
	private MenuBar menuBar;
	private JPanel currentScreenPanel;
	private JPanel contentPane;
	boolean isMainScreen;
	boolean isEditActionItemScreen;
	boolean isCommentScreen;
	boolean isHistoryScreen;
	boolean isPrintScreen;
	boolean isClosedActionItemScreen;
	Window() {
		frame = new JFrame("To Do List");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		frame.setContentPane(contentPane);
		currentScreenPanel = new MainScreen();
		menuBar = new MenuBar(currentScreenPanel);
		contentPane.add(menuBar);
		contentPane.add(currentScreenPanel);
		frame.pack();
		frame.setVisible(true);
	}
	private static void runGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		Window window = new Window();
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
}