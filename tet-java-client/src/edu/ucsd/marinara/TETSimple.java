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

    public double[] getX() {
        return  gazeAccessor.getGazeX();
    }
    public double[] getY() {
        return  gazeAccessor.getGazeY();
    }

    private static class GazeListener implements IGazeListener
    {
        private double gazeX = -1;
        private double gazeY = -1;
        private double gazePrevX = -1;
        private double gazePrevY = -1;

        @Override
        public void onGazeUpdate(GazeData gazeData)
        {
            if ( gazeX == -1 && gazeY == -1 && gazePrevX == -1 && gazePrevY == -1) {
                gazeX = gazeData.smoothedCoordinates.x;
                gazeY = gazeData.smoothedCoordinates.y;
                gazePrevX = gazeX;
                gazePrevY = gazeY;
            }

            if (gazeData.smoothedCoordinates.x > 0 && gazeData.smoothedCoordinates.y > 0) {
                gazePrevX = gazeX;
                gazePrevY = gazeY;
                gazeX = gazeData.smoothedCoordinates.x;
                gazeY = gazeData.smoothedCoordinates.y;
            }
        }

        public double[] getGazeX(){
            double gazeArrayX[] = new double[2];
            gazeArrayX[0] = gazePrevX;
            gazeArrayX[1] = gazeX;
            return gazeArrayX;
        }
        public double[] getGazeY(){
            double gazeArrayY[] = new double[2];
            gazeArrayY[0] = gazePrevY;
            gazeArrayY[1] = gazeY;
            return gazeArrayY;
        }

    }
}