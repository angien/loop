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
    stroke(255);
    line(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    line(CANVAS_WIDTH, 0, 0, CANVAS_HEIGHT);
    rectMode(CENTER);
    rect(CANVAS_WIDTH/2, CANVAS_HEIGHT/2, CANVAS_WIDTH/4, CANVAS_HEIGHT/4);

    if (gaze != null) {
      double[] gazeX = gaze.getX();
      double[] gazeY = gaze.getY();
      line((float)gazeX[0], (float)gazeY[0],(float) gazeX[1],(float) gazeY[1]);
      double y1 = (double) CANVAS_HEIGHT/CANVAS_WIDTH * gazeX[0];
      double y2 = (double) -CANVAS_HEIGHT/CANVAS_WIDTH * gazeX[0] + CANVAS_HEIGHT;
      double midX = CANVAS_WIDTH/2;
      double midY = CANVAS_HEIGHT/2;

      if (gazeY[0] >= y1 && gazeY[0] <= y2 &&  gazeX[0] <= midX - CANVAS_WIDTH/8) {
        System.out.println("Q1");
      } else if (gazeY[0]<= y1 && gazeY[0] <= y2 && gazeY[0] <= midY - CANVAS_HEIGHT/8) {
        System.out.println("Q2");
      } else if (gazeY[0] <= y1 && gazeY[0] >= y2 && gazeX[0] >= midX + CANVAS_WIDTH/8) {
        System.out.println("Q3");
      } else if (gazeY[0] >= y1 && gazeY[0] >= y2 && gazeY[0] >= midY + CANVAS_HEIGHT/8) {
        System.out.println("Q4");
      } else {
        System.out.println("CENTER");
      }
    }
  }
}
