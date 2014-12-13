package edu.ucsd.bolognese.src;

import edu.ucsd.main.MainViewController;
import edu.ucsd.marinara.KeyboardView;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import edu.ucsd.main.LoopApplet;

import javax.swing.*;
import java.awt.*;

/**
 * Created by justinhung on 11/20/14.
 */
public class TypingView extends LoopApplet {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static double width = screenSize.getWidth();
    static double height = screenSize.getHeight();

    static final int WINDOWWIDTH  = (int)width; //1366;
    static final int WINDOWHEIGHT = (int)height; //768;
    static final int IMGPOS       = 40;
    static final int BACKWIDTH    = 150;
    static final int BACKHEIGHT   = 70;
    static final int ZERO         = 0;
    static final int BACKSIZE     = 65;
    static final int BACKY        = WINDOWHEIGHT*4/5;

    KeyboardView keyboard;
    JLayeredPane main_pane = new JLayeredPane();

    boolean isSetup = false;
    String message = null;

    int backColor, highlight;
    PImage profPic, backImg;
    PFont f;

    public TypingView (KeyboardView keyboard) {
        this.keyboard = keyboard;
    }

    public void setup() {
        size(WINDOWWIDTH, WINDOWHEIGHT);
        profPic = loadImage("profile.jpg");
        backImg = loadImage("back.png");
        backColor = TemplatePrefs.DEFAULT_BACK_COLOR;
        highlight = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        background(TemplatePrefs.DEFAULT_BACKGROUND);
        f = createFont("Helvetica Neue UltraLight", 48, true);
    }

    public void draw() {
        background(TemplatePrefs.DEFAULT_BACKGROUND);
        backColor = TemplatePrefs.DEFAULT_BACK_COLOR;

        message = keyboard.getCurrentMessage();

        //current message
        textSize(32);
        fill(TemplatePrefs.DEFAULT_TEXT);
        textFont(f);
        text(message, (int) (width/5), WINDOWHEIGHT*5/6);


        if (overBack(ZERO, BACKY, BACKWIDTH, BACKHEIGHT)) backColor = highlight;

        //TEXT TO Speak
        if (keyboard.getDone()) {
              FreeTTS freeTTS = new FreeTTS(keyboard.getPrevMessage());
              freeTTS.speak();
              keyboard.setDone(false);
        }

        // buttons
        fill(backColor);
        noStroke();
        rect(ZERO, BACKY, BACKWIDTH, BACKHEIGHT);
        image(backImg, IMGPOS, BACKY+3, BACKSIZE, BACKSIZE);

    }

    public void mousePressed() {
        if (overBack(ZERO, BACKY, BACKWIDTH, BACKHEIGHT)) {
            MainViewController.removeTopEmbed("keyboard");
            System.out.println("back pressed");
        }
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
