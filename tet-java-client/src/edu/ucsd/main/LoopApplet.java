package edu.ucsd.main;

import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Class used to override the mouse values set within PApplet.
 */
public class LoopApplet extends PApplet {

    private static int NUM_COOR_AVG = 20;
    private static int CLICK_RANGE = 50;
    private static int NUM_MS_PER_CLICK = 2000; // How long to wait until the mouse is pressed

    public int prevAvgMouseX;
    public int prevAvgMouseY;
    private int startingClickX;
    private int startingClickY;
    private int startGazeTime = 0;

    // To store last NUM_COOR_AVG coordinates
    private ArrayList<MouseEvent> prev_mouse_events = new ArrayList<MouseEvent>(NUM_COOR_AVG);

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        super.handleMouseEvent(event);

        set_mouse_coordinates(event);
        clickIfNeeded();
    }

    /**
     * Takes the average of the last NUM_COOR_AVG coordinates, and assigns them to the mouseX/mouseY
     * values being used throughout the rest of the app.
     * @param event - The most recent mouse event. Always included in the average.
     */
    private synchronized void set_mouse_coordinates(MouseEvent event) {
        if (prev_mouse_events.size() == NUM_COOR_AVG) {
            prev_mouse_events.remove(NUM_COOR_AVG - 1);
        }

        prev_mouse_events.add(0, event);

        int sumMouseX = 0;
        int sumMouseY = 0;

        for(MouseEvent e : prev_mouse_events) {
            sumMouseX += e.getX();
            sumMouseY += e.getY();
        }

        pmouseX = prevAvgMouseX;
        pmouseY = prevAvgMouseY;
        mouseX = sumMouseX / prev_mouse_events.size();
        mouseY = sumMouseY / prev_mouse_events.size();
        prevAvgMouseX = mouseX;
        prevAvgMouseY = mouseY;
    }

    /**
     * Checks if the mouse has been within the same area for over 2 seconds. If so, the mouse will be pressed.
     */
    protected synchronized void clickIfNeeded() {
        if (startGazeTime == 0) {
            startGazeTime = millis();
            startingClickX = prevAvgMouseX;
            startingClickY = prevAvgMouseY;
            return;
        }

        boolean xInRange = (prevAvgMouseX <= startingClickX + CLICK_RANGE) &&
                (prevAvgMouseX >= startingClickX - CLICK_RANGE);
        boolean yInRange = (prevAvgMouseY <= startingClickY + CLICK_RANGE) &&
                (prevAvgMouseY >= startingClickY - CLICK_RANGE);

        // If neither is in range, reset the start time
        if (!xInRange || !yInRange) {
            startGazeTime = millis();
            startingClickX = prevAvgMouseX;
            startingClickY = prevAvgMouseY;
            return;
        }

        if (millis() - startGazeTime >= NUM_MS_PER_CLICK) {
            System.out.print("Mouse is getting clicked at " + mouseX + " " + mouseY + "\n");
            mousePressed();
            startGazeTime = millis();
            startingClickX = prevAvgMouseX;
            startingClickY = prevAvgMouseY;
        }
    }

    /**
     * Resets timer for mouse presses
     */
    protected void resetTimedClick() {
        startGazeTime = millis();
        startingClickX = 0;
        startingClickY = 0;
    }
}
