package edu.ucsd.marinara;

import com.theeyetribe.client.GazeManager;
import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

public class TETSimple
{

    private GazeListener gazeAccessor;

    public TETSimple()
    {
        final GazeManager gm = GazeManager.getInstance();
        boolean success = gm.activate(GazeManager.ApiVersion.VERSION_1_0, GazeManager.ClientMode.PUSH);

        final GazeListener gazeListener = new GazeListener();
        gazeAccessor = gazeListener;
        gm.addGazeListener(gazeListener);

        //TODO: Do awesome gaze control wizardry

        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                gm.removeGazeListener(gazeListener);
                gm.deactivate();
            }
        });
    }

    public double getX() {
        return  gazeAccessor.getGazeX();
    }
    public double getY() {
        return  gazeAccessor.getGazeY();
    }

    private static class GazeListener implements IGazeListener
    {
        private double gazeX;
        private double gazeY;

        @Override
        public void onGazeUpdate(GazeData gazeData)
        {
            gazeX = gazeData.smoothedCoordinates.x;
            gazeY = gazeData.smoothedCoordinates.y;
        }

        public double getGazeX(){
            return gazeX;
        }
        public double getGazeY(){
            return gazeY;
        }

    }
}