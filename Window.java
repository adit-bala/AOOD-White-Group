import javax.swing.*;

class Window {
	private JFrame frame;
	private MenuBar menuBar;
	private MainScreen main;
	Window() {
		frame = new JFrame("To Do List");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main = new MainScreen(frame);
		menuBar = new MenuBar(frame);
		frame.setJMenuBar(menuBar);
		frame.setContentPane(main);
		frame.pack();
		frame.setVisible(true);
	}
	private static void runGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		new Window();
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
}