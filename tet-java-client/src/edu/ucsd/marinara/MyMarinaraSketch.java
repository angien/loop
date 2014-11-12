package edu.ucsd.marinara;

import processing.core.*;

public class MyMarinaraSketch extends PApplet {

  private static int CANVAS_WIDTH = 1920;
  private static int CANVAS_HEIGHT = 1080;
  TETSimple gaze = new TETSimple();

  public static void main(String args[]) {
    PApplet.main(new String[] { "edu.ucsd.marinara.MyMarinaraSketch" });
  }

  public void setup() {
    size(CANVAS_WIDTH, CANVAS_HEIGHT);
    background(0);


  }

  public void draw() {
    System.out.println("X=" + gaze.getX() + "  Y=" + gaze.getY());
    stroke(255);
    line(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    line(CANVAS_WIDTH, 0, 0, CANVAS_HEIGHT);
    rectMode(CENTER);
    rect(CANVAS_WIDTH/2, CANVAS_HEIGHT/2, CANVAS_WIDTH/4, CANVAS_HEIGHT/4);

    if (gaze != null) {
      line(CANVAS_WIDTH/2, CANVAS_HEIGHT/2,(float)gaze.getX(),(float)gaze.getY());
      double y1 = (double) CANVAS_HEIGHT/CANVAS_WIDTH * mouseX;
      double y2 = (double) -CANVAS_HEIGHT/CANVAS_WIDTH * mouseX + CANVAS_HEIGHT;
      double midX = CANVAS_WIDTH/2;
      double midY = CANVAS_HEIGHT/2;

      if (mouseY >= y1 && mouseY <= y2 && mouseX <= midX - CANVAS_WIDTH/8) {
        System.out.println("Q1");
      } else if (mouseY <= y1 && mouseY <= y2 && mouseY <= midY - CANVAS_HEIGHT/8) {
        System.out.println("Q2");
      } else if (mouseY <= y1 && mouseY >= y2 && mouseX >= midX + CANVAS_WIDTH/8) {
        System.out.println("Q3");
      } else if (mouseY >= y1 && mouseY >= y2 && mouseY >= midY + CANVAS_HEIGHT/8) {
        System.out.println("Q4");
      } else {
        System.out.println("CENTER");
      }
    }
  }
}
