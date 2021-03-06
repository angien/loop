package edu.ucsd.bolognese.src;

import java.awt.*;

/**
 * Contains the color scheme used within the application.
 */
public class TemplatePrefs {
  public static final int DEFAULT_BACKGROUND = 50;
  public static final int DEFAULT_WRITE_COLOR = new Color(11, 72, 107).getRGB();
  public static final int TURQUOISE = new Color(87, 186, 225).getRGB();
  public static final int LIGHTGREEN = new Color(116, 205, 178).getRGB();
  public static final int LIME = new Color(121, 189, 154).getRGB();
  public static final int DEFAULT_BACK_COLOR = new Color(115, 99, 87).getRGB();
  public static final int DEFAULT_BUTTON_SELECTED = new Color(229, 255, 151).getRGB();

  // dynamic window size
  static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int WINDOWWIDTH  = (int)screenSize.getWidth();
  public static final int WINDOWHEIGHT = (int)screenSize.getHeight();

  // Keyboard Colors
  public static final int QUADRANT_SELECTED = TURQUOISE;
  public static final int VALID_INPUT_COLOR = new Color(0, 255, 0).getRGB();
  public static final int INVALID_INPUT_COLOR = new Color(255, 0, 0).getRGB();
  public static final int DEFAULT_TEXT = 255;
  public static final int SELECTED_TEXT = DEFAULT_WRITE_COLOR;


}
