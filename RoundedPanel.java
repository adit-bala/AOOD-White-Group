import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

class RoundedPanel extends JPanel {
		private Color backgroundColor;
		private Color foregroundColor;
		private int cornerRadius = 15;

		public RoundedPanel(int radius) {
			super();
			cornerRadius = radius;
			setBackground(getBackground());
		}

		public RoundedPanel(int radius, Color fgColor, Color bgColor) {
			super();
			cornerRadius = radius;
			backgroundColor = bgColor;
			foregroundColor = fgColor;
			setBackground(bgColor);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Dimension arcs = new Dimension(cornerRadius, cornerRadius);
			int width = getWidth();
			int height = getHeight();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (foregroundColor != null) {
				graphics.setColor(foregroundColor);
			} else {
				graphics.setColor(getForeground());
			}
			graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width,
					arcs.height); // paint foreground
			graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width,
					arcs.height); // paint border
		}
	}
