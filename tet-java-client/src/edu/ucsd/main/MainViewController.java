package edu.ucsd.main;

import edu.ucsd.bolognese.src.HomePage;
import edu.ucsd.bolognese.src.ProfileView;
import edu.ucsd.bolognese.src.TemplatePrefs;
import edu.ucsd.bolognese.src.TypingView;
import edu.ucsd.marinara.KeyboardView;
import processing.core.PApplet;
import processing.core.PImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by ryanliao on 11/30/14.
 */
public class MainViewController extends JFrame {

    static LoopApplet kb_embed;
    static LoopApplet home_embed;
    static LoopApplet prof_embed;
    static LoopApplet cont_embed;

    static JLayeredPane main_pane;
    static JFrame main_frame;

    static String curr_embed = "none";
    static String prev_embed = "none";

    static KeyboardView keyboard;

    // general flow of PApplet: new() -> pane.add(...) -> init() -> noLoop()
    public MainViewController() {
        super("Embedded PApplet");
        setSize(TemplatePrefs.WINDOWWIDTH, TemplatePrefs.WINDOWHEIGHT);
        setTitle("Spaghetti App");

        main_frame = this;
        main_pane = new JLayeredPane();

        prev_embed = "none";
        curr_embed = "none";


        showHome();

        add(main_pane);
    }

    public static void showHome(){
        if(!curr_embed.equals("home")) {
            home_embed = new HomePage();
            home_embed.init();
            home_embed.loop();
            home_embed.setLocation(0, 0);
            main_pane.add(home_embed, 1, 0);
            curr_embed = "home";

            main_frame.setVisible(true);
        }
    }

    public static void showKeyboard(int uid){
        if(!curr_embed.equals("keyboard")) {
            keyboard = new KeyboardView(uid);
            kb_embed = new TypingView(keyboard);

            kb_embed.init();
            keyboard.init();
            
            kb_embed.loop();

            kb_embed.setLocation(0, 0);
            keyboard.setLocation(TemplatePrefs.WINDOWWIDTH/6, TemplatePrefs.WINDOWHEIGHT/20);

            main_pane.add(kb_embed, 1, 0);
            main_pane.add(keyboard, 2, 0);
            curr_embed = "keyboard";

            main_frame.setVisible(true);
        }
    }

    public static void showContacts(){
        if(!curr_embed.equals("contacts")) {
            cont_embed = new ContactsView();
            cont_embed.init();
            cont_embed.loop();
            cont_embed.setLocation(0, 0);
            main_pane.add(cont_embed, 1, 0);
            curr_embed = "contacts";

            main_frame.setVisible(true);
        }
    }

    public static void showProfile(PImage img, String name, int uid){
        if(!curr_embed.equals("profile")){
            prof_embed = new ProfileView(img, name, uid);
            prof_embed.init();
            prof_embed.loop();
            System.out.println("Profile name: " + name);
            prof_embed.setLocation(0, 0);
            main_pane.add(prof_embed, 2, 0);
            curr_embed = "profile";

            main_frame.setVisible(true);
        }
    }

    public static void showMail(){
        if(!curr_embed.equals("mail")) {
            prev_embed = curr_embed;
            curr_embed = "mail";
        }

        main_frame.setVisible(true);
    }

    public static void removeTopEmbed(String top_embed){
        if(!curr_embed.equals("none")){
            if(curr_embed.equals("keyboard")) {
                kb_embed.noLoop();
                keyboard.noLoop();
                main_pane.remove(kb_embed);
                main_pane.remove(keyboard);
            }else if(curr_embed.equals("profile")){
                prof_embed.noLoop();
                main_pane.remove(prof_embed);
            }else if(curr_embed.equals("contacts")){
                cont_embed.noLoop();
                main_pane.removeAll();
                showHome();

            }else{
                System.out.println("Error: no view to remove");
                return;
            }
            curr_embed = "none";
        }
    }

    // Justin: would need this if we need to get back to the previous state that's not necesary the homepage
    // Ryan: I agree, and i think the easiest way is to put the views order in a stack, and just repopulate the
    //       previous view when gone back...caching the view is weird and I am having hard time with it so far, so I
    //       changed it just destroying and recreating view. Not that slow, more mem efficient, just slow startup
    public static void returnToPreviousState() {

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

    public static void mousePressed() {
        if (curr_embed == null) {
          return;
        }

        if (curr_embed.equals("home")) {
            home_embed.mousePressed();
        } else if (curr_embed.equals("keyboard")) {
            kb_embed.mousePressed();
        } else if (curr_embed.equals("contacts")) {
            cont_embed.mousePressed();
        } else if (curr_embed.equals("profile")) {
            prof_embed.mousePressed();
        }
    }

}
