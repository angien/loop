package edu.ucsd.marinara;

import processing.core.*;

import java.awt.*;

public class MyMarinaraSketch extends PApplet {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();

    private int CANVAS_WIDTH = (int) (width / 2); //2160/2;
    private int CANVAS_HEIGHT = (int) (height / 2); //1440/2;
    private float CANVAS_X1 = CANVAS_WIDTH / 2;
    private float CANVAS_Y1 = CANVAS_HEIGHT / 2;
    private float CANVAS_X2 = CANVAS_WIDTH / 4;
    private float CANVAS_Y2 = CANVAS_HEIGHT / 4;
    private float CANVAS_MID_X = CANVAS_WIDTH / 2;
    private float CANVAS_MID_Y = CANVAS_HEIGHT / 2;

    private static int prev_q = 0;
    private static int prev_direction = 0;
    private static int branch_count = 0;
    private static int[] crossed_q = new int[6];
    private static boolean didStartFromMid = false;
    private static boolean isValidFlow = false;
    private static int flow_counter = 0;
    private static int pressed_start_q = -1;
    private static boolean did_start_q0 = false;
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


    // check if current mouse position is inside the box
    // start point = x1, y2 ... end point = x2, y2
    public boolean isInBox(float box_x1, float box_y1, float box_x2, float box_y2,
                           int mouse_x, int mouse_y) {
        if (mouse_x > box_x1 && mouse_x < box_x2 &&
                mouse_y > box_y1 && mouse_y < box_y2) {
            return true;
        }
        return false;
    }

    public static void resetAllValues() {
        prev_q = 0;
        prev_direction = 0;
        branch_count = 0;
        didStartFromMid = false;
        isValidFlow = false;
        flow_counter = 0;
        pressed_start_q = -1;
        crossed_q = new int[6];
    }

    public char getQChar(int start_q, int cross_direction, int cross_count) {
        char result = '^';
        System.out.print("getting char: " + Integer.toString(start_q) + " " +
            Integer.toString(cross_direction) + " " +
            Integer.toString(cross_count));
        switch (start_q * cross_direction) {
            case 1:
                result = q1_pos_char[cross_count - 1];
                break;
            case -1:
                result = q1_neg_char[cross_count - 1];
                break;
            case 2:
                result = q2_pos_char[cross_count - 1];
                break;
            case -2:
                result = q2_neg_char[cross_count - 1];
                break;
            case 3:
                result = q3_pos_char[cross_count - 1];
                break;
            case -3:
                result = q3_neg_char[cross_count - 1];
                break;
            case 4:
                result = q4_pos_char[cross_count - 1];
                break;
            case -4:
                result = q4_neg_char[cross_count - 1];
                break;
            default:
                break;
        }


        return result;
    }

    public void drawDefaultWindow() {
        stroke(255);

        // line (start x, start y, end x, end y)
        line(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        line(CANVAS_WIDTH, 0, 0, CANVAS_HEIGHT);

        rectMode(CENTER);
        rect(CANVAS_X1, CANVAS_Y1, CANVAS_X2, CANVAS_Y2);
    }

    public static void main(String args[]) {
        PApplet.main(new String[]{"edu.ucsd.marinara.MyMarinaraSketch"});
    }

    public void setup() {
        System.out.println("set up counter");
        size(CANVAS_WIDTH, CANVAS_HEIGHT);
        background(0);
    }


    // calculate which quadrant the mouse is in
    public int getCurrQ(int mouse_x, int mouse_y) {
        double y1 = (double) CANVAS_HEIGHT / CANVAS_WIDTH * mouseX;
        double y2 = (double) -CANVAS_HEIGHT / CANVAS_WIDTH * mouseX + CANVAS_HEIGHT;

        if (mouse_y >= y1 && mouse_y <= y2 && mouse_x <= CANVAS_MID_X - CANVAS_WIDTH / 8) {
            //System.out.println("Q1");
            return 1;
        } else if (mouse_y <= y1 && mouse_y <= y2 && mouse_y <= CANVAS_MID_Y - CANVAS_HEIGHT / 8) {
            //System.out.println("Q2");
            return 2;
        } else if (mouse_y <= y1 && mouse_y >= y2 && mouse_x >= CANVAS_MID_X + CANVAS_WIDTH / 8) {
            //System.out.println("Q3");
            return 3;
        } else if (mouse_y >= y1 && mouse_y >= y2 && mouse_y >= CANVAS_MID_Y + CANVAS_HEIGHT / 8) {
            //System.out.println("Q4");
            return 4;
        } else {
            //System.out.println("CENTER");
            return 0;
        }
    }

    // sorry...too lazy to refactor this...
    // 0 is center, 1 is right cross, -1 is left cross
    public int getCurrDirection(int prev_q, int curr_q) // refactor this...only works cause Java is PBV
    {
        int result = curr_q - prev_q;
        if ((prev_q == 4 && curr_q == 1) || (prev_q == 1 && curr_q == 4)) // corner case
        {
            if (prev_q < curr_q) // 4 going to 1
            {
                result = 1;
            } else // 1 going to 4
            {
                result = -1;
            }
        }

        else {
            if (prev_q < curr_q) // right cross
            {
                result = 1;
            } else // left cross
            {
                result = -1;
            }
        }

        return result;
    }

    public void draw() {
        drawDefaultWindow();

        if (mousePressed) {
                // if mouse enters/is in quadrant
                line(mouseX, mouseY, pmouseX, pmouseY);

                 // wen moving quadrants, mouse changes before pmouse
                // mouse is start, pmouse is end
                int new_q = getCurrQ(pmouseX, pmouseY);
                int new_direction = getCurrDirection(prev_q, new_q);

                if (branch_count < 6)
                {
                    if(branch_count == 0)
                    {
                        crossed_q[branch_count] = new_q;
                        branch_count++;
                        prev_q = new_q;
                        System.out.println("started at quadrant: " + Integer.toString(new_q));

                    }
                    if (new_q != prev_q) // did change quadrant, only crossed one
                    {
                        System.out.println("crossed branch from: " + Integer.toString(prev_q) + " to " + Integer.toString(new_q));
                        prev_q = new_q;
                        prev_direction = new_direction;
                        crossed_q[branch_count] = new_q;
                        branch_count++;
                    }
                }
        } // end if(mousePressed
        else // mouse released
        {
            if(branch_count > 2 && branch_count < 7 && getCurrQ(mouseX, mouseY) == 0) // make sure it did exit the center before
            {
                System.out.println("mouse released, the starting point is: " + Integer.toString(crossed_q[0]));
                System.out.println("the size is: " + Integer.toString(branch_count));
                if(crossed_q[0] == 0 && crossed_q[branch_count - 1] == 0) {

                    // crossed_q[1] should be when 0 moves to something else
                    // - 3 because : start point, start point's next point, and  end point dont count
                    char test = getQChar(crossed_q[1], crossed_q[2] - crossed_q[1], branch_count - 3); // FIX middle parameter
                    System.out.println("the char is: " + test);
                }

                else
                {
                    System.out.println("somethign went wrong ");
                }
            }

            resetAllValues();
            background(0);
            drawDefaultWindow();
        }


    } // end draw

} // end class
