package edu.ucsd.bolognese.src;

import com.sun.corba.se.spi.ior.IORTemplateList;
import edu.ucsd.main.MainViewController;
import edu.ucsd.marinara.KeyboardView;
import processing.core.*;

/**
 * Created by karenlo on 11/24/14.
 */
public class HomePage extends PApplet {

    //title icon
    static int TITLEHEIGH = 204;
    static int TITLEWIDTH = 480;
    static int TITLEX = TemplatePrefs.WINDOWWIDTH/2 - TITLEWIDTH/2;
    static int TITLEY = TemplatePrefs.WINDOWHEIGHT/10;

    //center coordinates
    static int mid_x = (int)(TemplatePrefs.WINDOWWIDTH / 2);
    static int mid_y = (int)(TemplatePrefs.WINDOWHEIGHT / 2);

    //button size
    static final int BUTTONWIDTH = TemplatePrefs.WINDOWWIDTH/5 ;
    static final int BUTTONHEIGHT = BUTTONWIDTH;
    static final int CORNERS = 30;

    //keyboard button
    static final int KEYBOARDY = TITLEY + TITLEHEIGH + TemplatePrefs.WINDOWHEIGHT/10;
    static final int KEYBOARDX = TemplatePrefs.WINDOWWIDTH/10;

    //contact button
    static final int CONTACTSY = TITLEY + TITLEHEIGH + TemplatePrefs.WINDOWHEIGHT/10;
    static final int CONTACTSX = KEYBOARDX + BUTTONWIDTH + TemplatePrefs.WINDOWWIDTH/10;

    //mail button
    static final int MAILY = TITLEY + TITLEHEIGH + TemplatePrefs.WINDOWHEIGHT/10;
    static final int MAILX = CONTACTSX + BUTTONWIDTH + TemplatePrefs.WINDOWWIDTH/10;

    //icons
    static final int ICONSIZE = (int)(BUTTONWIDTH / 2);
    static final int ICON_OFFSET_X = (BUTTONWIDTH / 2) - (ICONSIZE / 2) ;
    static final int ICON_OFFSET_Y = (BUTTONHEIGHT / 3) - (ICONSIZE / 2);

    //static final int ICON_OFFSET_Y
    static final int ZERO = 0;

    int keyboardColor, contactsColor, mailColor;
    PImage titleImg, keyboardImg, contactsImg, mailImg;
    PFont f;

    public void setup() {
        size(TemplatePrefs.WINDOWWIDTH, TemplatePrefs.WINDOWHEIGHT);
        background(TemplatePrefs.DEFAULT_BACKGROUND);
        titleImg = loadImage("loop.png");
        keyboardImg = loadImage("keyboard.png");
        contactsImg = loadImage("contacts.png");
        mailImg = loadImage("mail.png");

        f = createFont("Arial", 40, true);

        noLoop(); // prevent thread from starving everything else
    }

    public void draw() {

        keyboardColor = contactsColor = mailColor = TemplatePrefs.TURQUOISE;

        //highlight keyboard
        if (overKeyboard(KEYBOARDX, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT)) keyboardColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        if (overContacts(CONTACTSX, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT)) contactsColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;
        if (overMail(MAILX, MAILY, BUTTONWIDTH, BUTTONHEIGHT)) mailColor = TemplatePrefs.DEFAULT_BUTTON_SELECTED;

        //drawing buttons
        fill(keyboardColor);
        noStroke();
        rect(KEYBOARDX, KEYBOARDY, BUTTONWIDTH, BUTTONHEIGHT, CORNERS);

        fill(contactsColor);
        noStroke();
        rect(CONTACTSX, CONTACTSY, BUTTONWIDTH, BUTTONHEIGHT, CORNERS);

        fill(mailColor);
        noStroke();
        rect(MAILX, MAILY, BUTTONWIDTH, BUTTONHEIGHT, CORNERS);

        //drawing buttons's icon
        image(titleImg, TITLEX, TITLEY, TITLEWIDTH, TITLEHEIGH);
        image(keyboardImg, KEYBOARDX + ICON_OFFSET_X, KEYBOARDY + ICON_OFFSET_Y, ICONSIZE, ICONSIZE);
        image(contactsImg, CONTACTSX + ICON_OFFSET_X, CONTACTSY + ICON_OFFSET_Y, ICONSIZE, ICONSIZE);
        image(mailImg, MAILX + ICON_OFFSET_X, MAILY  + ICON_OFFSET_Y + 20, ICONSIZE, ICONSIZE*3/4);

        textFont(f);
        fill(0);

        text("Keyboard", KEYBOARDX + BUTTONWIDTH/4 - 20, KEYBOARDY + ICON_OFFSET_Y + BUTTONWIDTH*2/3);
        text("Contacts", CONTACTSX + BUTTONWIDTH/4, CONTACTSY + ICON_OFFSET_Y + BUTTONWIDTH*2/3);
        text("E-Mail", MAILX + BUTTONWIDTH/4 + 20, MAILY + ICON_OFFSET_Y + BUTTONWIDTH*2/3);

        loop(); //temporarily fix for highlight button
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


    static public void main(String args[]) {
        PApplet.main(new String[] { "edu.ucsd.bolognese.src.HomePage" });
    }
}
