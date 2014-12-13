package edu.ucsd.main;

import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.ArrayList;

/**
 * Class used to override the mouse values set within PApplet.
 */
public class LoopApplet extends PApplet {

    public int prevAvgMouseX;
    public int prevAvgMouseY;

    private static int NUM_COOR_AVG = 20;
    private ArrayList<MouseEvent> prev_mouse_events = new ArrayList<MouseEvent>(NUM_COOR_AVG);

    @Override
    protected void handleMouseEvent(MouseEvent event) {
        super.handleMouseEvent(event);

        set_mouse_coordinates(event);
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
}
