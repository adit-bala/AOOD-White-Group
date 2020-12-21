import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import backend.ActionItem;
import backend.FileUtilities;
import backend.FontLoader;

/*
 * This class shows all events in an Action Item's history
 */
public class PrintActionItemScreen extends JPanel implements ActionListener {
	/*
	 * Instance variables
	 */
	private JFrame frame;
	private PrintActionItemPanel preview;
	private ActionItem item;

	public static final Font TITLE_FONT = FontLoader.loadFont("/res/EBGaramond/static/EBGaramond-ExtraBold.ttf", 80);
	public static final Font HEADER_FONT = FontLoader.loadFont("/res/EBGaramond/static/EBGaramond-ExtraBold.ttf",
			50);
	public static final Font LABEL_FONT = FontLoader.loadFont("/res/Chivo/Chivo-Bold.ttf", 30);

	PrintActionItemScreen(ActionItem item, JFrame frame) {
		this.item = item;
		this.frame = frame;
		this.preview = new PrintActionItemPanel(item);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

		/*
		 * title
		 */
		setLayout(new GridBagLayout());
		JLabel titleLabel = new JLabel("Print Action Item");
		titleLabel.setFont(TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel underline = new RoundedPanel(10, Color.decode("#56997F"), Color.WHITE);
		underline.setMaximumSize(new Dimension(610, 10));
		titlePanel.add(titleLabel);
		titlePanel.add(underline);
		titlePanel.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		add(titlePanel, c);

		/*
		 * name of item
		 */
		JLabel itemLabel = new JLabel("Preview");
		itemLabel.setFont(HEADER_FONT);
		itemLabel.setAlignmentX(LEFT_ALIGNMENT);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10, 0, 0, 0);
		add(itemLabel, c);

		/*
		 * scroll pane
		 */
		JPanel container = new JPanel();
		container.setBackground(new Color(120, 120, 120));
		container.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.weighty = 1;
		c.insets = new Insets(50, 50, 50, 50);
		JPanel paper = new JPanel();
		paper.setLayout(new BorderLayout());
		paper.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		paper.add(preview);
		container.add(paper, c);

		JScrollPane scroll = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10, 0, 20, 0);
		add(scroll, c);

		JButton commitButton = new JButton("PRINT");
		commitButton.addActionListener(this);
		commitButton.setActionCommand("print");
		commitButton.setFont(LABEL_FONT);
		commitButton.setBackground(Color.decode("#56997F"));
		commitButton.setForeground(Color.WHITE);
		commitButton.setFocusable(false);
		commitButton.setBorder(BorderFactory.createEtchedBorder());
		commitButton.setPreferredSize(new Dimension(0, 60));
		commitButton.setMinimumSize(new Dimension(0, 60));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 0.1;
		c.fill = GridBagConstraints.BOTH;
		add(commitButton, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		if (eventName.contentEquals("print")) {
			try {
				if (FileUtilities.printPanel(preview))
					JOptionPane.showMessageDialog(frame, "Now printing " + item.getTitle() + ".", "Print Confirmation",
							JOptionPane.INFORMATION_MESSAGE);
			} catch (PrinterException e1) {
				JOptionPane.showMessageDialog(frame, "Failed to print " + item.getTitle() + ".", "Print Unsuccessful",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}