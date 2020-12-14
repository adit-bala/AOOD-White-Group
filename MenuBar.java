import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import backend.ActionItem;
import backend.FileUtilities;
import backend.FontLoader;
import backend.ToDoList;

import java.awt.Color;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class MenuBar extends JMenuBar implements ActionListener{
	private JMenu file; 
	private JMenuItem createBackup, restoreBackup, printList;
	private JButton completedActionItems, quit, back;
	private JFileChooser fileChooser; 
	private Stack<JPanel> prevPanels = new Stack<JPanel>();
	private JFrame frame;
	private MainScreen main;
	private ArrayList<ActionItem> passCompletedActionItems;
	final static Color BAR_COLOR = Color.decode("#56997F");
	
	MenuBar(JFrame frame, MainScreen main){
		this.frame = frame;
		this.main = main;
		setBackground(BAR_COLOR);
		file = new JMenu("File");
		file.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 12));
		file.setBackground(BAR_COLOR);
		file.setForeground(Color.white);
		
		createBackup = new JMenuItem("Create Backup");
		createBackup.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 10));
		createBackup.setActionCommand("Create");
		createBackup.addActionListener(this);
		restoreBackup = new JMenuItem("Restore Backup...");
		restoreBackup.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 10));
		restoreBackup.setActionCommand("Restore");
		restoreBackup.addActionListener(this);
		printList = new JMenuItem("Print List");
		printList.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 10));
		printList.setActionCommand("Print");
		printList.addActionListener(this);
		file.add(createBackup);
		file.add(restoreBackup);
		file.add(printList);
		
		completedActionItems = new JButton("Completed Action Items");
		completedActionItems.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 12));
		completedActionItems.setBackground(BAR_COLOR);
		completedActionItems.setForeground(Color.white);
		completedActionItems.setOpaque(true);
		completedActionItems.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		completedActionItems.setActionCommand("Completed Action Items");
		completedActionItems.addActionListener(this);
		quit = new JButton("Quit");
		quit.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Bold.ttf", 12));
		quit.setBackground(BAR_COLOR);
		quit.setOpaque(true);
		quit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		quit.setForeground(Color.white);
		quit.setActionCommand("Quit");
		quit.addActionListener(this); 
		
		add(file);
		add(completedActionItems);
		add(quit);
		
		passCompletedActionItems = new ArrayList<ActionItem>();
		
		//frame = new JFrame();
		//MainScreen main = new MainScreen(frame);
		//frame.setJMenuBar(this);
		//frame.setContentPane(main);
		//frame.pack();
		//frame.setVisible(true);
	
	}
	public void changeBar() {
		remove(file);
		remove(completedActionItems);
		remove(quit); 
		back = new JButton("Back");
		add(back);
		back.setActionCommand("Back");
		back.addActionListener(this);
		back.setFont(FontLoader.loadFont("src/res/Chivo/Chivo-Regular.ttf", 12));
		back.setBackground(BAR_COLOR);
		back.setForeground(Color.white);
		back.setOpaque(true);
		back.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	}
	public void resetBar() {
		remove(back);
		add(file);
		add(completedActionItems);
		add(quit);
	}
	public void addPrevPanel(JPanel panel) {
		prevPanels.push(panel);
	}
	public void actionPerformed(ActionEvent event) {
		String eventName = event.getActionCommand();
		if (eventName.equals("Create")) {
			JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("backup.json"));
			fc.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filepath = file.getPath();
                if (!filepath.endsWith(".json")) {
                	filepath += ".json";
                }
                FileUtilities.writeBackup(main.getToDoList(), filepath);
            }
		} else if (eventName.equals("Restore")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("To Do List Backups", "tdl"));
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
            } 
			// SET TO DO LIST
		} else if (eventName.equals("Print")) {
			try {
				FileUtilities.printToDoList(main);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		} else if (eventName.equals("Back")) {
			if (prevPanels.empty()) {
				frame.setContentPane(main);
				resetBar();
				// REFRESH LIST
			} else {
				frame.setContentPane(prevPanels.pop());
			}
			frame.revalidate();
			frame.repaint();
		} else if (eventName.equals("Completed Action Items")) {
			changeBar();
			for(int i=0; i<main.getToDoList().getNumCompleteItems(); i++) {
				passCompletedActionItems.add(main.getToDoList().getCompleteItemAtIndex(i));
			}
			ClosedActionItemsScreen completed = new ClosedActionItemsScreen(passCompletedActionItems);
			frame.setContentPane(completed);
			frame.revalidate();
			frame.repaint();
		} else if (eventName.equals("Quit")) {
			System.exit(0);
		}
	}
	public static void main (String[] args) {
		//MenuBar bar = new MenuBar();
	}
}
