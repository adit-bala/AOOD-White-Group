import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONException;

import backend.ActionItem;
import backend.FileUtilities;
import backend.FontLoader;
import backend.ToDoList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class MenuBar extends JMenuBar implements ActionListener{
	private JMenu file; 
	private JMenuItem createBackup, restoreBackup, printList;
	private JButton completedActionItems, quit, back;
	private JLabel[] separators = new JLabel[2];
	private Stack<JPanel> prevPanels = new Stack<JPanel>();
	private JFrame frame;
	private MainScreen main;
	final static Color BAR_COLOR = Color.decode("#56997F");
	
	MenuBar(JFrame frame, MainScreen main){
		this.frame = frame;
		this.main = main;
		setBackground(BAR_COLOR);
		file = new JMenu("File");
		file.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 20));
		file.setBackground(BAR_COLOR);
		file.setForeground(Color.white);
		
		createBackup = new JMenuItem("Create Backup");
		createBackup.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 18));
		createBackup.setActionCommand("Create");
		createBackup.addActionListener(this);
		restoreBackup = new JMenuItem("Restore Backup");
		restoreBackup.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 18));
		restoreBackup.setActionCommand("Restore");
		restoreBackup.addActionListener(this);
		printList = new JMenuItem("Print List");
		printList.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 18));
		printList.setActionCommand("Print");
		printList.addActionListener(this);
		file.add(createBackup);
		file.add(restoreBackup);
		file.add(printList);
		
		completedActionItems = new JButton("Completed Action Items");
		completedActionItems.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 20));
		completedActionItems.setBackground(BAR_COLOR);
		completedActionItems.setForeground(Color.white);
		completedActionItems.setOpaque(true);
		completedActionItems.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		completedActionItems.setActionCommand("Completed Action Items");
		completedActionItems.addActionListener(this);
		quit = new JButton("Quit");
		quit.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 20));
		quit.setBackground(BAR_COLOR);
		quit.setOpaque(true);
		quit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		quit.setForeground(Color.white);
		quit.setActionCommand("Quit");
		quit.addActionListener(this); 
		
		for (int i = 0; i < 2; i++) {
			JLabel separator = new JLabel(" | ");
			separator.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 20));
			separator.setForeground(Color.WHITE);
			separators[i] = separator;
		}
		add(Box.createHorizontalStrut(5));
		add(file);
		add(separators[0]);
		add(completedActionItems);
		add(separators[1]);
		add(quit);
		
		//frame = new JFrame();
		//MainScreen main = new MainScreen(frame);
		//frame.setJMenuBar(this);
		//frame.setContentPane(main);
		//frame.pack();
		//frame.setVisible(true);
	
	}
	public void changeBar() {
		remove(file);
		remove(separators[0]);
		remove(completedActionItems);
		remove(separators[1]);
		remove(quit); 
		back = new JButton("Back");
		add(back);
		back.setActionCommand("Back");
		back.addActionListener(this);
		back.setFont(FontLoader.loadFont("/res/Chivo/Chivo-Regular.ttf", 18));
		back.setBackground(BAR_COLOR);
		back.setForeground(Color.white);
		back.setOpaque(true);
		back.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	}
	public void resetBar() {
		remove(back);
		add(file);
		add(separators[0]);
		add(completedActionItems);
		add(separators[1]);
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
			fc.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
            int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filepath = file.getPath();
                if (!filepath.endsWith(".json")) {
                	filepath += ".json";
                }
                try {
					FileUtilities.writeBackup(main.getToDoList(), filepath);
					JOptionPane.showMessageDialog(frame, "Successfully created backup at " + filepath + ".", "Backup Successful", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(frame, "Failed to write file at " + filepath + ". Please check if the application has permission to write to this location.", "Backup Failed", JOptionPane.ERROR_MESSAGE);
				}
            }
		} else if (eventName.equals("Restore")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
    			ToDoList restoredList;
				try {
					restoredList = FileUtilities.restoreFrom(file.getPath());
					main.setToDoList(restoredList);
	    			JOptionPane.showMessageDialog(frame, "Successfully restored backup from " + file.getPath() + ".", "Restore Successful", JOptionPane.INFORMATION_MESSAGE);
				} catch (JSONException e) {
					JOptionPane.showMessageDialog(frame, "The file at " + file.getPath() + " is not a valid backup.", "Restore Failed", JOptionPane.ERROR_MESSAGE);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(frame, "Failed to read file at " + file.getPath() + ". Please check if the application has permission to read from this location.", "Restore Failed", JOptionPane.ERROR_MESSAGE);
				}
            } 
		} else if (eventName.equals("Print")) {
			Dimension temp = frame.getSize();
			try {
				frame.setSize(500, 10000);
				frame.repaint();
				frame.revalidate();
				if (FileUtilities.printPanel(main))
					JOptionPane.showMessageDialog(frame, "Now printing your to-do list.", "Print Confirmation", JOptionPane.INFORMATION_MESSAGE);
			} catch (PrinterException e) {
				JOptionPane.showMessageDialog(frame, "Failed to print your to-do list.", "Print Unsuccessful", JOptionPane.ERROR_MESSAGE);
			}
			frame.setSize(temp);
		} else if (eventName.equals("Back")) {
			if (prevPanels.empty()) {
				frame.setContentPane(main);
				main.rerenderItemLists();
				resetBar();
			} else {
				JPanel last = prevPanels.pop();
				frame.setContentPane(last);
				if (last instanceof HistoryScreen) {
					((HistoryScreen) last).refreshEvents();
				} else if (last instanceof ClosedActionItemsScreen) {
					((ClosedActionItemsScreen) last).refreshEvents();
				}
			}
			frame.revalidate();
			frame.repaint();
		} else if (eventName.equals("Completed Action Items")) {
			changeBar();
			ClosedActionItemsScreen completed = new ClosedActionItemsScreen(frame, main.getToDoList());
			frame.setContentPane(completed);
			frame.revalidate();
			frame.repaint();
		} else if (eventName.equals("Quit")) {
			System.exit(0);
		}
	}
}
