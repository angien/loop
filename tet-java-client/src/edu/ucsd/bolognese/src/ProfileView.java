package edu.ucsd.bolognese.src;

import edu.ucsd.marinara.KeyboardView;
import processing.core.*;

public class ProfileView extends PApplet {
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

    boolean isSetup = false;
    int writeColor, addColor, deleteColor, backColor, highlight;
    static PImage writeImg, addImg, deleteImg, backImg;
    PFont f;
    PImage profPic;
    String profName;

    public ProfileView(PImage img, String name){
        profPic = img;
        profName = name;
    }

    // CHANGE this to default case instead of karen's profile!!!
    public void setup() {
        size(WINDOWWIDTH, WINDOWHEIGHT);
        writeImg = loadImage("write.png");
        addImg = loadImage("add.png");
        deleteImg = loadImage("delete.png");
        backImg = loadImage("back.png");
        writeColor = TemplatePrefs.DEFAULT_WRITE_COLOR;
        addColor = TemplatePrefs.TURQUOISE;
        deleteColor = TemplatePrefs.LIME;
        backColor = TemplatePrefs.DEFAULT_BACK_COLOR;
        highlight = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        background(TemplatePrefs.DEFAULT_BACKGROUND);
        f = createFont("Arial", 48, true);

        //noLoop(); // prevent thread from starting everything else
    }

    public void draw() {
        writeColor = TemplatePrefs.DEFAULT_WRITE_COLOR;
        addColor = TemplatePrefs.TURQUOISE;
        deleteColor = TemplatePrefs.LIME;
        backColor = TemplatePrefs.DEFAULT_BACK_COLOR;

        if (overWrite(WRITEX, ZERO, RECTWIDTH, RECTHEIGHT)) writeColor = highlight;
        if (overAdd(ADDX, ZERO, RECTWIDTH, RECTHEIGHT)) addColor = highlight;
        if (overDelete(DELETEX, ZERO, RECTWIDTH, RECTHEIGHT)) deleteColor = highlight;
        if (overBack(ZERO, BACKY, BACKWIDTH, BACKHEIGHT)) backColor = highlight;

        // buttons
        fill(writeColor);
        noStroke();
        rect(WRITEX, ZERO, RECTWIDTH, RECTHEIGHT);
        fill(addColor);
        noStroke();
        rect(ADDX, ZERO, RECTWIDTH, RECTHEIGHT);
        fill(deleteColor);
        noStroke();
        rect(DELETEX, ZERO, RECTWIDTH, RECTHEIGHT);
        fill(backColor);
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
        text(profName, 250, 510);

        noLoop();
    }

    /**
     * Actions when mouse is clicked
     */
    public void mousePressed() {
        if (!isSetup) {
            isSetup = true;
            setup();
        }

        if(overWrite(WRITEX, ZERO, RECTWIDTH, RECTHEIGHT)){
            try {
                KeyboardView.main(new String[0]);
                noLoop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set to true if hovering over write button
     */
    public boolean overWrite(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    /**
     * Set to true if hovering over add button
     */
    public boolean overAdd(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    /**
     * Set to true if hovering over delete button
     */
    public boolean overDelete(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    /**
     * Set to true if hovering over back button
     */
    public boolean overBack(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

}
