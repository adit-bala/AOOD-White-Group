package backend;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontLoader {
	public static Font loadFont(String path, float size) {
		try {
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(12f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(customFont);
		    return customFont;
		} catch (FontFormatException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
