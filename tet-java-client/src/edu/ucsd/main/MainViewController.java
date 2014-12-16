package edu.ucsd.main;

import edu.ucsd.bolognese.src.HomePage;
import edu.ucsd.bolognese.src.ProfileView;
import edu.ucsd.bolognese.src.TemplatePrefs;
import edu.ucsd.bolognese.src.TypingView;
import edu.ucsd.marinara.KeyboardView;
import processing.core.PImage;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ryanliao on 11/30/14.
 */
public class MainViewController extends JFrame {

    static LoopApplet home_embed;
    static LoopApplet prof_embed;
    static LoopApplet cont_embed;

    static JLayeredPane main_pane;
    static JFrame main_frame;

    // general flow of PApplet: new() -> pane.add(...) -> init() -> noLoop()
    public MainViewController() {
        super("Embedded PApplet");
        setSize(TemplatePrefs.WINDOWWIDTH, TemplatePrefs.WINDOWHEIGHT);
        setTitle("Loop");

        main_frame = this;
        main_pane = new JLayeredPane();

        showHome();

        add(main_pane);
    }

    public static void showHome(){
        HomePage home_embed = new HomePage();
        home_embed.init();

        home_embed.resetTimedClick();
        home_embed.loop();
        home_embed.setLocation(0, 0);
        main_pane.add(home_embed, 0);

        main_frame.setVisible(true);
    }

    public static void showKeyboard(int uid){
        KeyboardView keyboard = new KeyboardView(uid);
        TypingView kb_embed = new TypingView(keyboard);

        kb_embed.init();
        keyboard.init();

        kb_embed.resetTimedClick();
        kb_embed.loop();

        kb_embed.setLocation(0, 0);
        keyboard.setLocation(TemplatePrefs.WINDOWWIDTH/6, TemplatePrefs.WINDOWHEIGHT/20);

        pauseTopEmbed();
        main_pane.add(kb_embed, 0);
        main_pane.add(keyboard, 0);

        main_frame.setVisible(true);
    }

    public static void showContacts(){
        ContactsView cont_embed = new ContactsView();
        cont_embed.init();

        cont_embed.resetTimedClick();
        cont_embed.loop();
        cont_embed.setLocation(0, 0);

        pauseTopEmbed();
        main_pane.add(cont_embed, 0);

        main_frame.setVisible(true);
    }

    public static void showProfile(PImage img, String name, int uid){
        ProfileView prof_embed = new ProfileView(img, name, uid);
        prof_embed.init();
        prof_embed.resetTimedClick();
        prof_embed.loop();
        System.out.println("Profile name: " + name);
        prof_embed.setLocation(0, 0);

        pauseTopEmbed();
        main_pane.add(prof_embed, 0);

        main_frame.setVisible(true);
    }

    public static void pauseTopEmbed() {
        int numComponents = main_pane.getComponentCount();
        if (numComponents > 0) {
            LoopApplet loopApplet = (LoopApplet) main_pane.getComponent(0);
            loopApplet.resetTimedClick();
            loopApplet.noLoop();
        }
    }

    public static void removeTopEmbed(){
        int numComponents = main_pane.getComponentCount();
        if (numComponents <= 0) {
            return;
        }

        LoopApplet loopApplet = (LoopApplet) main_pane.getComponent(0);
        loopApplet.noLoop();
        main_pane.remove(0);

        // Check if there are still any left after removing
        if (numComponents - 2 >= 0 ) {
            loopApplet = (LoopApplet) main_pane.getComponent(0);
            loopApplet.resetTimedClick();
            loopApplet.loop();
        }
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
//                new DatabaseConnect();
                new MainViewController();
            }
       });
    }

    /**
     * Used for debugging purposes. Prints out the components currently on the main pane
     */
    private static void printMainPane() {
        System.out.println("*****PRINTING COMPONENTS*****");
        for(Component c :main_pane.getComponents()) {
            System.out.println(c.getClass().getName());
        }
        System.out.println("***************************");
    }
}
