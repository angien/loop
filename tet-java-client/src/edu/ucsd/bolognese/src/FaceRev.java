package edu.ucsd.bolognese.src;

import edu.ucsd.marinara.MyMarinaraSketch;
import processing.core.*;

import java.io.IOException;

/**
 * Created by karenlo on 11/13/14.
 */
public class FaceRev extends PApplet {
    static final int WINDOWWIDTH  = 1366;
    static final int WINDOWHEIGHT = 768;
    static final int IMGPOS       = 40;
    static final int IMGWIDTH     = 400;
    static final int IMGHEIGHT    = 400;
    static final int BACKWIDTH    = 440;
    static final int BACKHEIGHT   = 70;
    static final int RECTWIDTH    = 290;
    static final int RECTHEIGHT   = 150;
    static final int ZERO = 0;
    static final int ICONY        = 8;
    static final int ICONSIZE     = 130;
    static final int BACKSIZE     = 65;
    static final int WRITEX       = 500;
    static final int ADDX         = 790;
    static final int DELETEX      = 1080;
    static final int BACKY        = 460;
    int writeColor, addColor, deleteColor, backColor, highlight;
    boolean writeOver = false, addOver = false, deleteOver = false, backOver = false;
    PImage profPic, writeImg, addImg, deleteImg, backImg;
    PFont f;

    public void setup() {
        size(WINDOWWIDTH, WINDOWHEIGHT);
        profPic = loadImage("profile.jpg");
        writeImg = loadImage("write.png");
        addImg = loadImage("add.png");
        deleteImg = loadImage("delete.png");
        backImg = loadImage("back.png");
        writeColor = color(11, 72, 107);
        addColor = color(59, 134, 134);
        deleteColor = color(121, 189, 154);
        backColor = color(115, 99, 87);
        highlight = color(224, 228, 204);
        background(50);
        f = createFont("Helvetica Neue UltraLight", 48, true);
    }

    public void draw() {
        update(mouseX, mouseY);

        if(writeOver) fill(highlight);
        else fill(writeColor);

        noStroke();
        rect(WRITEX, ZERO, RECTWIDTH, RECTHEIGHT);

        if(addOver) fill(highlight);
        else fill(addColor);

        noStroke();
        rect(ADDX, ZERO, RECTWIDTH, RECTHEIGHT);

        if(deleteOver) fill(highlight);
        else fill(deleteColor);

        noStroke();
        rect(DELETEX, ZERO, RECTWIDTH, RECTHEIGHT);

        if(backOver) fill(highlight);
        else fill(backColor);

        noStroke();
        rect(ZERO, BACKY, BACKWIDTH, BACKHEIGHT);

        image(profPic, IMGPOS, IMGPOS, IMGWIDTH, IMGHEIGHT);
        image(writeImg, 580, ICONY, ICONSIZE, ICONSIZE);
        image(addImg, 870, ICONY, ICONSIZE, ICONSIZE);
        image(deleteImg, 1160, ICONY, ICONSIZE, ICONSIZE);
        image(backImg, IMGPOS, BACKY+3, BACKSIZE, BACKSIZE);

        textFont(f);
        fill(255);

        textAlign(CENTER);
        text("Karen Lo", 250, 510);
    }

    public void update(int x, int y){
        if( overWrite(WRITEX, ZERO, RECTWIDTH, RECTHEIGHT) ) {
            writeOver = true;
            addOver = false;
            deleteOver = false;
            backOver = false;
        } else if( overAdd(ADDX, ZERO, RECTWIDTH, RECTHEIGHT) ) {
            writeOver = false;
            addOver = true;
            deleteOver = false;
            backOver = false;
        } else if( overDelete(DELETEX, ZERO, RECTWIDTH, RECTHEIGHT) ) {
            writeOver = false;
            addOver = false;
            deleteOver = true;
            backOver = false;
        } else if( overBack(ZERO, BACKY, BACKWIDTH, BACKHEIGHT) ) {
            writeOver = false;
            addOver = false;
            deleteOver = false;
            backOver = true;
        } else {
            writeOver = addOver =  deleteOver = backOver = false;
        }
    }

    /**
     * Actions when mouse is clicked
     */
    public void mousePressed() {
        if(writeOver){
            try {
                MyMarinaraSketch sketch = new MyMarinaraSketch();
                String[] args = {};
                sketch.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set to true if hovering over write button
     */
    public boolean overWrite(int x, int y, int width, int height) {
        if(mouseX >= x && mouseX <= x+width &&
           mouseY >= y && mouseY <= y+height)
            return true;
        else return false;
    }

    /**
     * Set to true if hovering over add button
     */
    public boolean overAdd(int x, int y, int width, int height) {
        if(mouseX >= x && mouseX <= x+width &&
                mouseY >= y && mouseY <= y+height)
            return true;
        else return false;
    }

    /**
     * Set to true if hovering over delete button
     */
    public boolean overDelete(int x, int y, int width, int height) {
        if(mouseX >= x && mouseX <= x+width &&
                mouseY >= y && mouseY <= y+height)
            return true;
        else return false;
    }

    /**
     * Set to true if hovering over back button
     */
    public boolean overBack(int x, int y, int width, int height) {
        if(mouseX >= x && mouseX <= x+width &&
                mouseY >= y && mouseY <= y+height)
            return true;
        else return false;
    }

    static public void main(String args[]) {
        PApplet.main(new String[] { "edu.ucsd.bolognese.src.FaceRev" });
    }
}
