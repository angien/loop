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

    public int prevAvgMouseX;
    public int prevAvgMouseY;
    private int startingClickX;
    private int startingClickY;
    private int startGazeTime = 0;
    private ArrayList<MouseEvent> prev_mouse_events = new ArrayList<MouseEvent>(NUM_COOR_AVG);

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        super.handleMouseEvent(event);

        set_mouse_coordinates(event);
        clickIfNeeded();
    }

    private void set_mouse_coordinates(MouseEvent event) {
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

    protected void clickIfNeeded() {
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

        if (!xInRange || !yInRange) {
            startGazeTime = millis();
            System.out.print("Mouse is not in range " + prevAvgMouseX + " " + prevAvgMouseY + "\n");
            System.out.print("Starting mouse clicks " + startingClickX + " " + startingClickY + "\n");
            startingClickX = prevAvgMouseX;
            startingClickY = prevAvgMouseY;
            return;
        }

        if (millis() - startGazeTime >= 2000) {
            System.out.print("Mouse is getting clicked at " + mouseX + " " + mouseY + "\n");
            //mousePressed();
            startGazeTime = millis();
            startingClickX = prevAvgMouseX;
            startingClickY = prevAvgMouseY;
        }
    }

    protected void resetTimedClick() {
        startGazeTime = millis();
        startingClickX = 0;
        startingClickY = 0;
    }
}
