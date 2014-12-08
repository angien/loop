package edu.ucsd.bolognese.src;

import com.sun.corba.se.spi.ior.IORTemplateList;
import edu.ucsd.main.MainViewController;
import edu.ucsd.marinara.KeyboardView;
import processing.core.*;

/**
 * Created by karenlo on 11/24/14.
 */
public class HomePage extends PApplet {
    static int mid_x = (int)(TemplatePrefs.WINDOWWIDTH / 2);
    static int mid_mid_x = (int)(mid_x / 2);
    static int mid_y = (int)(TemplatePrefs.WINDOWHEIGHT / 2) - 20; // FIX THIS!!! 20 is temporary
    static final int MARGINX  = (int)(mid_x * 0.1);

    static final int BUTTONWIDTH = (int)((mid_x - (MARGINX * 3)) / 2);
    static final int BUTTONHEIGHT = (int)(TemplatePrefs.WINDOWHEIGHT * 0.2);

    static final int HOMEPAGEY = (int)(mid_y - (BUTTONHEIGHT / 2));
    static final int KEYBOARDY = HOMEPAGEY;
    static final int CONTACTSY = KEYBOARDY;
    static final int MAILY = CONTACTSY;

    static final int HOMEPAGEX = MARGINX;
    static final int KEYBOARDX = HOMEPAGEX + BUTTONWIDTH + MARGINX;
    static final int MAILX = TemplatePrefs.WINDOWWIDTH - MARGINX - BUTTONWIDTH;
    static final int CONTACTSX = MAILX - MARGINX - BUTTONWIDTH;

    static final int ICONSIZE = (int)(BUTTONWIDTH / 4);
    static final int ICON_OFFSET_X = (BUTTONWIDTH / 2) - (ICONSIZE / 2) ;
    static final int ICONY = (int)(mid_y + (BUTTONHEIGHT / 2) - (0.2 * BUTTONHEIGHT));
    //static final int ICON_OFFSET_Y
    static final int ZERO = 0;

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

        f = createFont("Arial", 40, true);

        noLoop(); // prevent thread from starving everything else
    }

    public void draw() {

        homeColor = TemplatePrefs.LIME;
        keyboardColor = contactsColor = mailColor = TemplatePrefs.TURQUOISE;

        if (overKeyboard(KEYBOARDX, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT)) keyboardColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        if (overContacts(CONTACTSX, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT)) contactsColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        if (overMail(MAILX, MAILY, BUTTONWIDTH, BUTTONHEIGHT)) mailColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;

        // buttons
        fill(homeColor);
        noStroke();
        rect(HOMEPAGEX, HOMEPAGEY, BUTTONWIDTH, BUTTONHEIGHT);

        fill(keyboardColor);
        noStroke();
        rect(KEYBOARDX, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT);

        fill(contactsColor);
        noStroke();
        rect(CONTACTSX, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT);

        fill(mailColor);
        noStroke();
        rect(MAILX, MAILY, BUTTONWIDTH, BUTTONHEIGHT);

        image(homeImg, HOMEPAGEX + ICON_OFFSET_X, HOMEPAGEY, ICONSIZE, ICONSIZE);
        image(keyboardImg, KEYBOARDX + ICON_OFFSET_X, KEYBOARDY, ICONSIZE, ICONSIZE);
        image(contactsImg, CONTACTSX + ICON_OFFSET_X, CONTACTSY, ICONSIZE, ICONSIZE);
        image(mailImg, MAILX + ICON_OFFSET_X, MAILY, ICONSIZE, ICONSIZE);

        textFont(f);
        fill(0);

        text("Home", (int)(HOMEPAGEX + (ICON_OFFSET_X * 0.5)), ICONY);
        text("Keyboard", (int)(KEYBOARDX + (ICON_OFFSET_X * 0.5)), ICONY);
        text("Contacts", (int)(CONTACTSX + (ICON_OFFSET_X * 0.5)), ICONY);
        text("Mail", (int)(MAILX + (ICON_OFFSET_X * 0.5)), ICONY);
    }

    /**
     * Actions when mouse is clicked
     */
    public void mousePressed() {
        if(overKeyboard(KEYBOARDX, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT)) {
            MainViewController.removeTopEmbed("keyboard");
            MainViewController.showKeyboard(-1);
        }else if(overContacts(CONTACTSX, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT)){
            MainViewController.removeTopEmbed("contacts");
            MainViewController.showContacts(); // CHANGE THIS TO showContacts() WHEN FINISEHD
        }else if(overMail(MAILX, MAILY, BUTTONWIDTH, BUTTONHEIGHT)){
            MainViewController.removeTopEmbed("mail");
            MainViewController.showMail();
        }else {
            System.out.println("mousePressed on something else");
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

}
