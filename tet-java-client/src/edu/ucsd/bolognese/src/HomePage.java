package edu.ucsd.bolognese.src;

import edu.ucsd.marinara.KeyboardView;
import processing.core.*;

/**
 * Created by karenlo on 11/24/14.
 */
public class HomePage extends PApplet {

    static final int ICONSIZE = 85;
    static final int ICONX = 25;
    static final int ZERO = 0;
    static final int BUTTONWIDTH    = 335;
    static final int BUTTONHEIGHT   = 130;
    static final int KEYBOARDY = 185;
    static final int CONTACTSY = 370;
    static final int MAILY = 555;

    int homeColor, keyboardColor, contactsColor, mailColor;
    PImage homeImg, keyboardImg, contactsImg, mailImg;
    PFont f;

    public void setup() {
        size(TemplatePrefs.WINDOWWIDTH, TemplatePrefs.WINDOWHEIGHT);
        background(TemplatePrefs.DEFAULT_BACKGROUND);

        homeImg = loadImage("home.png");
        keyboardImg = loadImage("keyboard.png");
        contactsImg = loadImage("contacts.png");
        mailImg = loadImage("mail.png");

        f = createFont("Arial", 48, true);
    }

    public void draw() {

        homeColor = TemplatePrefs.LIME;
        keyboardColor = contactsColor = mailColor = TemplatePrefs.TURQUOISE;

        if (overKeyboard(ZERO, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT)) keyboardColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        if (overContacts(ZERO, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT)) contactsColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        if (overMail(ZERO, MAILY, BUTTONWIDTH, BUTTONHEIGHT)) mailColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;

        // buttons
        fill(homeColor);
        noStroke();
        rect(ZERO, ZERO, BUTTONWIDTH, BUTTONHEIGHT);

        fill(keyboardColor);
        noStroke();
        rect(ZERO, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT);

        fill(contactsColor);
        noStroke();
        rect(ZERO, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT);

        fill(mailColor);
        noStroke();
        rect(ZERO, MAILY, BUTTONWIDTH, BUTTONHEIGHT);

        image(homeImg, ICONX, 20, ICONSIZE, ICONSIZE);
        image(keyboardImg, ICONX, 205, ICONSIZE, ICONSIZE);
        image(contactsImg, ICONX, 390, ICONSIZE, ICONSIZE);
        image(mailImg, ICONX, 580, ICONSIZE, 70);

        textFont(f);
        fill(0);

        text("Home", 130, 80);
        text("Keyboard", 115, 265);
        text("Contacts", 115, 450);
        text("Mail", 140, 635);
    }

    /**
     * Actions when mouse is clicked
     */
    public void mousePressed() {
        if(overKeyboard(ZERO, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT)){
            try {
                KeyboardView.main(new String[0]);
                noLoop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set to true if hovering over keyboard button
     */
    public boolean overKeyboard(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    /**
     * Set to true if hovering over contacts button
     */
    public boolean overContacts(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    /**
     * Set to true if hovering over mail button
     */
    public boolean overMail(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    static public void main(String args[]) {
        PApplet.main(new String[] { "edu.ucsd.bolognese.src.ProfileView" });
    }
}
