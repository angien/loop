package edu.ucsd.marinara;

import edu.ucsd.bolognese.src.ColorPrefs;
import processing.core.*;

import java.awt.*;

/*
 * TODO: Some text box other than console to give to ProfileView
 * TODO: bottom in itself should be a new
 */

public class KeyboardView extends PApplet {

    // Get current screen size, take out later
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();

    private int CANVAS_WIDTH = (int) (width / 2); //2160/2;
    private int CANVAS_HEIGHT = (int) (height / 2); //1440/2;
    private float CANVAS_MID_X = CANVAS_WIDTH / 2;
    private float CANVAS_MID_Y = CANVAS_HEIGHT / 2;

    // color of the line that was drawn
    private int lineColor = ColorPrefs.VALID_INPUT_COLOR;

    // Inner rectangle variables
    private float RECT_X1 = CANVAS_WIDTH / 2;
    private float RECT_Y1 = CANVAS_HEIGHT / 2;
    private float RECT_X2 = CANVAS_WIDTH / 4;
    private float RECT_Y2 = CANVAS_HEIGHT / 4;

    private static int prev_q = 0;
    private static int branch_count = 0;
    private static int[] crossed_q = new int[7];

    // Quadrant identifiers
    private static final int QUADRANT_CENTER = 0;
    private static final int QUADRANT_ONE = 1;
    private static final int QUADRANT_TWO = 2;
    private static final int QUADRANT_THREE = 3;
    private static final int QUADRANT_FOUR = 4;

    // q1_pos_char is quadrant 1's "right" branch, where index 0 is the char closest to the center
    // can refactor this into 1 array per quadrant...not sure if needed to refactor
    private static final char[] q1_pos_char = new char[]{'t', 'c', 'z', '.'}; // i think its .
    private static final char[] q1_neg_char = new char[]{'i', 'h', 'j', ','}; // i think its ,
    private static final char[] q2_pos_char = new char[]{'y', 'b', 'p', 'q'};
    private static final char[] q2_neg_char = new char[]{'s', 'd', 'g', '\''}; // i think its '
    private static final char[] q3_pos_char = new char[]{'n', 'm', 'f', '#'}; // accented a
    private static final char[] q3_neg_char = new char[]{'a', 'r', 'x', '?'};
    private static final char[] q4_pos_char = new char[]{'e', 'l', 'k', '#'}; // accented e
    private static final char[] q4_neg_char = new char[]{'o', 'u', 'v', 'w'};

    public static void main(String args[]) {
        PApplet.main(new String[]{"edu.ucsd.marinara.KeyboardView"});
    }

    // Processing function for setting up UI
    public void setup() {
        size(CANVAS_WIDTH, CANVAS_HEIGHT);
        background(ColorPrefs.DEFAULT_BACKGROUND);
        drawDefaultWindow();
    }

    // Method for drawing UI
    public void drawDefaultWindow() {
        stroke(255);
        fill(255);
        // line (start x, start y, end x, end y)
        line(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        line(CANVAS_WIDTH, 0, 0, CANVAS_HEIGHT);

        // center input box
        rectMode(CENTER);
        rect(RECT_X1, RECT_Y1, RECT_X2, RECT_Y2);

        drawKeyboardLetters();
    }

  /**
   * Draws on the the letters for the keyboard within their respective quadrants.
   */
    private void drawKeyboardLetters() {
      // keyboard letters and placement
      int textSize = 32;
      int leftXPos = CANVAS_WIDTH / 10;
      int rightXPos = (int) (CANVAS_WIDTH * .8);
      fill(255);
      textSize(textSize);
      textAlign(leftXPos);
      text(new String(q1_pos_char), leftXPos, getQuadrantTextYPosition(leftXPos, true) + 50);
      text(new String(q1_neg_char), leftXPos, getQuadrantTextYPosition(leftXPos, false) - 35);
      text(new String(q2_neg_char), leftXPos, getQuadrantTextYPosition(leftXPos, true));
      text(new String(q4_pos_char), leftXPos, getQuadrantTextYPosition(leftXPos, false) + 30);

      textAlign(rightXPos);
      text(new String(q2_pos_char), rightXPos, getQuadrantTextYPosition(rightXPos, false) - 55);
      text(new String(q3_pos_char), rightXPos, getQuadrantTextYPosition(rightXPos, true));
      text(new String(q3_neg_char), rightXPos, getQuadrantTextYPosition(rightXPos, false) + 30);
      text(new String(q4_neg_char), rightXPos, getQuadrantTextYPosition(rightXPos, true) + 75);
    }

    private int getQuadrantTextYPosition(int xPosition, boolean posChar) {
        float lineSlope = (float) CANVAS_HEIGHT / CANVAS_WIDTH;
        if(posChar) {
            return (int) (lineSlope * xPosition);
        }
        return  CANVAS_HEIGHT - (int) (lineSlope * xPosition);
    }

    public void resetAllValues() {
        lineColor = ColorPrefs.VALID_INPUT_COLOR;
        prev_q = 0;
        branch_count = 0;
        crossed_q = new int[7];
    }

    // Based off params, gets the char
    // start_q: starting quadrant number
    // loop_direction: direction of the loop
    // q_count: number of quadrants entered
    public char getQChar(int start_q, int loop_direction, int q_count) {
        char result = '\n'; // new line

        switch (start_q * loop_direction) {
            case 1:
                result = q1_pos_char[q_count];
                break;
            case -1:
                result = q1_neg_char[q_count];
                break;
            case 2:
                result = q2_pos_char[q_count];
                break;
            case -2:
                result = q2_neg_char[q_count];
                break;
            case 3:
                result = q3_pos_char[q_count];
                break;
            case -3:
                result = q3_neg_char[q_count];
                break;
            case 4:
                result = q4_pos_char[q_count];
                break;
            case -4:
                result = q4_neg_char[q_count];
                break;
            default: // if its a 0
                if (start_q == 2)
                    result = ' ';
                break;
        }

        return result;
    }

  /**
   * Calculates which quadrant the mouse is in given the mouse position
   * @param mouse_x x-position of the mouse
   * @param mouse_y y-position of the mouse
   * @return the quadrant the mouse is currently in(i.e. QUADRANT_ONE, QUADRANT_TWO, etc.)
   */
    public int getCurrQuadrant(int mouse_x, int mouse_y) {
        double y1 = (double) CANVAS_HEIGHT / CANVAS_WIDTH * mouseX;
        double y2 = (double) -CANVAS_HEIGHT / CANVAS_WIDTH * mouseX + CANVAS_HEIGHT;

        if (mouse_y >= y1 && mouse_y <= y2 && mouse_x <= CANVAS_MID_X - CANVAS_WIDTH / 8) {
            //System.out.println("Q1");
            return QUADRANT_ONE;
        } else if (mouse_y <= y1 && mouse_y <= y2 && mouse_y <= CANVAS_MID_Y - CANVAS_HEIGHT / 8) {
            //System.out.println("Q2");
            return QUADRANT_TWO;
        } else if (mouse_y <= y1 && mouse_y >= y2 && mouse_x >= CANVAS_MID_X + CANVAS_WIDTH / 8) {
            //System.out.println("Q3");
            return QUADRANT_THREE;
        } else if (mouse_y >= y1 && mouse_y >= y2 && mouse_y >= CANVAS_MID_Y + CANVAS_HEIGHT / 8) {
            //System.out.println("Q4");
            return QUADRANT_FOUR;
        } else {
            //System.out.println("CENTER");
            return QUADRANT_CENTER;
        }
    }

    // gets the current direction
    // 0 is back on itself, 1 is right cross, -1 is left cross
    public int getDirection(int first_q, int second_q) // refactor this...only works cause Java is PBV
    {
        if (first_q == 4 && second_q == 1) // corner case
            return 1;
        if (first_q == 1 && second_q == 4)
            return -1;
        if (second_q == 0) {
            return 0;
        }
        return second_q - first_q;
    }

    public void draw() {
        background(ColorPrefs.DEFAULT_BACKGROUND);
        drawDefaultWindow();

        if (mousePressed) {
            // when moving quadrants, mouse changes before pmouse
            // mouse is start, pmouse is end
            int new_q = getCurrQuadrant(pmouseX, pmouseY);
            highlightQuadrant(new_q);

            // if mouse enters/is in quadrant
            strokeWeight(3);
            stroke(lineColor); // TODO: FIX THIS!!!!!!!!!
            line(mouseX, mouseY, pmouseX, pmouseY);

            if (branch_count < 7)
            {
                // started
                if(branch_count == 0)
                {
                    if (new_q == 0)
                        lineColor = ColorPrefs.VALID_INPUT_COLOR;
                    else {
                        lineColor = ColorPrefs.INVALID_INPUT_COLOR;
                    }

                    crossed_q[branch_count] = new_q;
                    branch_count++;
                    prev_q = new_q;
                    //System.out.println("started at quadrant: " + Integer.toString(new_q));

                }
                if (new_q != prev_q) // changed quadrant area
                {
                    //System.out.println("crossed branch from: " + Integer.toString(prev_q) + " to " + Integer.toString(new_q));
                    prev_q = new_q;
                    crossed_q[branch_count] = new_q;
                    branch_count++;
                }
            }

            if(branch_count > 2 && branch_count < 7 && getCurrQuadrant(mouseX, mouseY) == 0) // check that loop is valid
            {
                //System.out.println("mouse released, the starting point is: " + Integer.toString(crossed_q[0]));
                //System.out.println("the size is: " + Integer.toString(branch_count));
                if(crossed_q[0] == 0) {

                    // crossed_q[1] should be when 0 moves to something else
                    // - 3 because : start point, start point's next point, and  end point dont count
                    //System.out.println("branch_count:" + branch_count);
                    //System.out.println("getQChar params: " + crossed_q[1] + " " + getDirection(crossed_q[1], crossed_q[2]) + " " + (branch_count-3));
                    char text = getQChar(crossed_q[1], getDirection(crossed_q[1], crossed_q[2]), branch_count - 3);
                    System.out.print(text);
                    resetAllValues();
                }

            }
        }
        else // mouse released
        {
            resetAllValues();
        }
    } // end draw

  /**
   * Colors in the area of a given quadrant. If the center quadrant is given, the canvas
   * remains unchanged.
   * @param currentQuadrant the quadrant which will be highlighted
   */
    private void highlightQuadrant(int currentQuadrant) {
      float x1, x2, x3, x4;
      float y1, y2, y3, y4;

      x1 = x2 = x3 = x4 = y1 = y2 = y3 = y4 = 0;
      switch(currentQuadrant) {
        case QUADRANT_ONE:
          x2 = CANVAS_MID_X - CANVAS_WIDTH / 8;
          y2 = CANVAS_MID_Y - CANVAS_HEIGHT / 8;
          x3 = CANVAS_MID_X - CANVAS_WIDTH / 8;
          y3 = CANVAS_MID_Y + CANVAS_HEIGHT / 8;
          y4 = CANVAS_HEIGHT;
          break;
        case QUADRANT_TWO:
          x2 = CANVAS_WIDTH;
          x3 = CANVAS_MID_X + CANVAS_WIDTH / 8;
          y3 = CANVAS_MID_Y - CANVAS_HEIGHT / 8;
          x4 = CANVAS_MID_X - CANVAS_WIDTH / 8;
          y4 = CANVAS_MID_Y - CANVAS_HEIGHT / 8;
          break;
        case QUADRANT_THREE:
          x1 = CANVAS_WIDTH;
          x2 = CANVAS_WIDTH;
          y2 = CANVAS_HEIGHT;
          x3 = CANVAS_MID_X + CANVAS_WIDTH / 8;
          y3 = CANVAS_MID_Y + CANVAS_HEIGHT / 8;
          x4 = CANVAS_MID_X + CANVAS_WIDTH / 8;
          y4 = CANVAS_MID_Y - CANVAS_HEIGHT / 8;
          break;
        case QUADRANT_FOUR:
          y1 = CANVAS_HEIGHT;
          x2 = CANVAS_MID_X - CANVAS_WIDTH / 8;
          y2 = CANVAS_MID_Y + CANVAS_HEIGHT / 8;
          x3 = CANVAS_MID_X + CANVAS_WIDTH / 8;
          y3 = CANVAS_MID_Y + CANVAS_HEIGHT / 8;
          x4 = CANVAS_WIDTH;
          y4 = CANVAS_HEIGHT;
          break;
        default:
          return;
      }

      fill(ColorPrefs.QUADRANT_SELECTED);
      quad(x1, y1, x2, y2, x3, y3, x4, y4);

      // Redraw the keyboard letters on top of the highlighted quadrant
      drawKeyboardLetters();
    }
} // end class
