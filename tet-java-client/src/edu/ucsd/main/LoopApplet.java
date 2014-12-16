package edu.ucsd.main;

import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;
import processing.core.PApplet;
import processing.event.MouseEvent;
import edu.ucsd.marinara.KeyboardView;

import java.util.ArrayList;

/**
 * Class used to override the mouse values set within PApplet.
 */
public class LoopApplet extends PApplet implements IGazeListener {

    private static int NUM_COOR_AVG = 5;
    private static int CLICK_RANGE = 50;
    private static int NUM_MS_PER_CLICK = 2000; // How long to wait until the mouse is pressed

    public int prevAvgMouseX;
    public int prevAvgMouseY;
    private int startingClickX;
    private int startingClickY;
    private int startGazeTime = 0;

    // To store last NUM_COOR_AVG coordinates
    private ArrayList<GazeData> prev_mouse_events = new ArrayList<GazeData>(NUM_COOR_AVG);

    /**
     * Takes the average of the last NUM_COOR_AVG coordinates, and assigns them to the mouseX/mouseY
     * values being used throughout the rest of the app.
     * @param gazeData - The most recent mouse event. Always included in the average.
     */
    private synchronized void set_mouse_coordinates(GazeData gazeData) {
        if (prev_mouse_events.size() == NUM_COOR_AVG) {
            prev_mouse_events.remove(NUM_COOR_AVG - 1);
        }

        prev_mouse_events.add(0, gazeData);

        double sumMouseX = 0;
        double sumMouseY = 0;

        for(GazeData g : prev_mouse_events) {
            sumMouseX += g.smoothedCoordinates.x - getXPositionOffset();
            sumMouseY += g.smoothedCoordinates.y - getYPositionOffset();
        }

        pmouseX = prevAvgMouseX;
        pmouseY = prevAvgMouseY;
        mouseX =  (int) sumMouseX / NUM_COOR_AVG;
        mouseY = (int) sumMouseY / NUM_COOR_AVG;
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

    protected int getXPositionOffset() {
        return 0;
    }

    protected int getYPositionOffset() {
        return 0;
    }

    @Override
    public void onGazeUpdate(GazeData gazeData) {

        /**
         * NOTE: Comment these two out if you would like to turn off 2-second hover clicks
         */
        set_mouse_coordinates(gazeData);
        clickIfNeeded();
    }
}
