package edu.ucsd.bolognese.src;

import edu.ucsd.main.MainViewController;
import edu.ucsd.marinara.KeyboardView;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.awt.*;

/**
 * Created by justinhung on 11/20/14.
 */
public class TypingView extends PApplet {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static double width = screenSize.getWidth();
    static double height = screenSize.getHeight();

    static final int WINDOWWIDTH  = (int)width; //1366;
    static final int WINDOWHEIGHT = (int)height; //768;
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

    KeyboardView keyboard;

    boolean isSetup = false;

    int writeColor, addColor, deleteColor, backColor, highlight;
    PImage profPic, writeImg, addImg, deleteImg, backImg;
    PFont f;


    public TypingView () {
        setLayout(new BorderLayout());
        keyboard = new KeyboardView();
        add(keyboard, BorderLayout.EAST);
        keyboard.init();
    }

    public void setup() {
        size(WINDOWWIDTH, WINDOWHEIGHT);
        profPic = loadImage("profile.jpg");
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
        f = createFont("Helvetica Neue UltraLight", 48, true);
    }

    public void draw() {

        writeColor = TemplatePrefs.DEFAULT_WRITE_COLOR;
        addColor = TemplatePrefs.TURQUOISE;
        deleteColor = TemplatePrefs.LIME;
        backColor = TemplatePrefs.DEFAULT_BACK_COLOR;
        textSize(32);
        text(keyboard.getCurrentMessage(), 100, 100);

        if (overWrite(WRITEX, ZERO, RECTWIDTH, RECTHEIGHT)) writeColor = highlight;
        if (overAdd(ADDX, ZERO, RECTWIDTH, RECTHEIGHT)) addColor = highlight;
        if (overDelete(DELETEX, ZERO, RECTWIDTH, RECTHEIGHT)) deleteColor = highlight;
        if (overBack(ZERO, BACKY, BACKWIDTH, BACKHEIGHT)) backColor = highlight;

        // buttons
        fill(backColor);
        noStroke();
        rect(ZERO, BACKY, BACKWIDTH, BACKHEIGHT);

        image(backImg, IMGPOS, BACKY+3, BACKSIZE, BACKSIZE);

        textFont(f);
        fill(255);

        textAlign(CENTER);
    }

    public void mousePressed() {
        if (overBack(ZERO, BACKY, BACKWIDTH, BACKHEIGHT)) {
            MainViewController.removeTopEmbed("back");
            System.out.println("back pressed");
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

    static public void main(String args[]) {
        PApplet.main(new String[] { "edu.ucsd.bolognese.src.TypingView" });
    }
}
