package edu.ucsd.main;

import edu.ucsd.bolognese.src.TemplatePrefs;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by ryanliao on 12/1/14.
 */
public class ContactsView extends LoopApplet {
    PFont f;

    ArrayList<String> prof_names;
    ArrayList<PImage> prof_images;

    static int exit_circle_radius = 100;
    static int view_width = (int)(TemplatePrefs.WINDOWWIDTH);
    static int view_height = (int)(TemplatePrefs.WINDOWHEIGHT);

    static int mid_col_x = (int)(view_width / 3);
    static int right_col_x = (int)(view_width * 2 / 3);
    static int mid_line_y = (view_height / 2) - 20; // FIX THIS...20 is temporary
    static int margin_x = (int)(mid_col_x * 0.125);
    static int margin_y = (int)(mid_line_y * 0.1);

    static int image_width = mid_col_x - (2 * margin_x);
    static int image_height = (int)(mid_line_y * 0.75) - margin_y;

    static int total_contacts = 8;
    static int curr_page_index = 0;
    static int last_page_index = total_contacts / 6;
    private boolean exit_pressed = false;
    static boolean next_pressed = false;

    static Timer exit_timer;
    public void testcase(){
        PImage karen_img = loadImage("karen.jpg");
        PImage taylor_img = loadImage("taylor.jpg");
        PImage angie_img = loadImage("angie.jpg");
        PImage justin_img = loadImage("justin.jpg");
        PImage ryan_img = loadImage("ryan.jpg");
        PImage sean_img = loadImage("sean.jpg");

        noLoop();
        prof_images.add(karen_img);
        prof_images.add(taylor_img);
        prof_images.add(angie_img);
        prof_images.add(justin_img);
        prof_images.add(ryan_img);
        prof_images.add(sean_img);


        prof_names.add("Karen Lo");
        prof_names.add("Taylor Fah");
        prof_names.add("Angie N.");
        prof_names.add("Justin Hung");
        prof_names.add("Ryan Liao");
        prof_names.add("Sean Rowan");
    }

    public ContactsView() {}

    public void setup() {
        size(view_width, view_height);
        background(TemplatePrefs.DEFAULT_BACKGROUND);

        f = createFont("Arial", 40, true);

        prof_names = new ArrayList<String>();
        prof_images = new ArrayList<PImage>();
        testcase();

        textFont(f);
        stroke(0);


        strokeWeight(3);
        stroke(0); // black
        line(mid_col_x, 0, mid_col_x, view_height); // left-mid separator
        stroke(0);
        line(right_col_x, 0, right_col_x, view_height); // mid-right separator
        stroke(0);
        line(0, mid_line_y, view_width, mid_line_y); // top-bot separator

        noFill();
        arc(0, mid_line_y, exit_circle_radius, exit_circle_radius, radians(0), radians(360));

        showImages(1);

        noLoop();
    }

    // assume page_num starts at 1
    public void showImages(int page_num){
        for(int i = page_num - 1; i < page_num * 6; i++){
            if(prof_images.get(i) == null){
                break;
            }

            drawOnBox(i, prof_images.get(i), prof_names.get(i));

        }
    }

    // 0, 1, 2
    // 3, 4, 5
    public void drawOnBox(int box_num, PImage img, String name){
        switch(box_num){
            case 0:
                image(img, margin_x, margin_y,  image_width , image_height);
                text(name, margin_x, mid_line_y - margin_y);
                break;
            case 1:
                image(img, mid_col_x + margin_x, margin_y, image_width, image_height);
                text(name, mid_col_x + margin_x, mid_line_y - margin_y);
                break;
            case 2:
                image(img, right_col_x + margin_x, margin_y, image_width, image_height);
                text(name, right_col_x + margin_x, mid_line_y - margin_y);
                break;
            case 3:
                image(img, margin_x, mid_line_y + margin_y, image_width, image_height);
                text(name, margin_x, TemplatePrefs.WINDOWHEIGHT - margin_y * 2); // NEED TO FIX Y-AXIS OF TEXT...CASE 3 4 5
                break;
            case 4:
                image(img, mid_col_x + margin_x, mid_line_y + margin_y, image_width, image_height);
                text(name, mid_col_x + margin_x, TemplatePrefs.WINDOWHEIGHT - margin_y * 2);
                break;
            case 5:
                image(img, right_col_x + margin_x, mid_line_y + margin_y, image_width, image_height);
                text(name, right_col_x + margin_x, TemplatePrefs.WINDOWHEIGHT - margin_y * 2);
                break;
            default:
                System.out.println("Error: Black Magic Detected");
        }
    }

    public void draw() {

        if(exit_pressed){
            fill(255, 0, 0);
            arc(0, mid_line_y, exit_circle_radius, exit_circle_radius, radians(0), radians(360));
        }else{
            fill(255, 255, 255);
            arc(0, mid_line_y, exit_circle_radius, exit_circle_radius, radians(0), radians(360));
        }

        noLoop();
    }

    public int getProfileClicked(){
        if(mouseY < mid_line_y && mouseX > 0){
            if(mouseX < mid_col_x){
                return 0;
            }else if(mouseX > mid_col_x && mouseX < right_col_x){
                return 1;
            }else{
                return 2;
            }
        }else if(mouseX > 0){
            if(mouseX > 0 && mouseX < mid_col_x){
                return 3;
            } else if(mouseX > mid_col_x && mouseX < right_col_x) {
                return 4;
            } else{
                return 5;
            }
        }

        return -1;
    }

    // formula for point within ellipse/circle: (x - center_x)^2 + (y - center_y)^2 < radius^2
    public boolean inCircle(int mouse_x, int mouse_y, int circle_x, int circle_y, int circle_radius){
        if(( ((mouse_x - circle_x)^2) + ((mouse_y - circle_y)^2) ) < (circle_radius^2)){
            return true;
        }
        return false;
    }

    public void mousePressed(){
        if(inCircle(mouseX, mouseY, 0, mid_line_y, (exit_circle_radius / 2))){
            exit_pressed = true;
            MainViewController.removeTopEmbed("contacts");
        }else {
            exit_pressed = false;
//            redraw();
            int prof_index = getProfileClicked() * (curr_page_index + 1);
            if(prof_images.get(prof_index) != null && prof_names.get(prof_index) != null){
                MainViewController.removeTopEmbed("contacts");
                MainViewController.showProfile(prof_images.get(prof_index), prof_names.get(prof_index), prof_index);
            }
        }
    }

}
