package edu.ucsd.main;

import edu.ucsd.bolognese.src.HomePage;
import edu.ucsd.bolognese.src.ProfileView;
import edu.ucsd.bolognese.src.TemplatePrefs;
import edu.ucsd.marinara.KeyboardView;
import processing.core.PApplet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by ryanliao on 11/30/14.
 */
public class MainViewController extends JFrame {

    static PApplet kb_embed;
    static PApplet home_embed;
    static PApplet prof_embed;
    static JLayeredPane main_pane;
    static JFrame main_frame;

    static String curr_top_embed;

    // general flow of PApplet: new() -> pane.add(...) -> init() -> noLoop()
    public MainViewController() {
        super("Embedded PApplet");
        setSize(TemplatePrefs.WINDOWWIDTH, TemplatePrefs.WINDOWHEIGHT);
        setTitle("Spaghetti App");

        main_frame = this;
        main_pane = new JLayeredPane();
        home_embed = new HomePage();
        kb_embed = new KeyboardView();
        prof_embed = new ProfileView();

        curr_top_embed = "none";

        home_embed.init();
        home_embed.noLoop();
        kb_embed.init();
        kb_embed.noLoop();
        prof_embed.init();
        prof_embed.noLoop();

        main_pane.add(home_embed, 1, 0);

        add(main_pane);
        setVisible(true);
    }

    public static void showKeyboard(){
        if(!curr_top_embed.equals("keyboard")) {
            System.out.println("show Keyboard");
            kb_embed.loop();
            kb_embed.setLocation(HomePage.BUTTONWIDTH, 0);
            main_pane.add(kb_embed, 2, 1);
            curr_top_embed = "keyboard";
        }
    }

    public static void showContacts(){
        if(!curr_top_embed.equals("contacts")) {
            System.out.println("show Contacts");
            curr_top_embed = "contacts";
        }
    }

    public static void showProfile(){
        if(!curr_top_embed.equals("profile")){
            System.out.println("show Profile");
            prof_embed.loop();
            prof_embed.setLocation(HomePage.BUTTONWIDTH, 0);
            main_pane.add(prof_embed, 2, 1);
            curr_top_embed = "profile";
        }
    }

    public static void showMail(){
        if(!curr_top_embed.equals("mail")) {
            System.out.println("show Mail");
            curr_top_embed = "mail";
        }
    }

    public static void removeTopEmbed(String top_embed){
        if(!curr_top_embed.equals("none") && !curr_top_embed.equals(top_embed)){
            if(curr_top_embed.equals("keyboard")) {
                kb_embed.noLoop();
                main_pane.remove(kb_embed);
            }else if(curr_top_embed.equals("profile")){
                prof_embed.noLoop();
                main_pane.remove(prof_embed);
            }
            curr_top_embed = "none";
        }
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MainViewController();
            }
       });
    }



}
