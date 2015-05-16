package kz.ef.art.vision.test;

public class VisionPositionCalculator {

    /*

    vWidth / gWidth = vX / gX;

    vWidth     vX
    -------- = ----
    gWidth     gX

    */

    public static int getGX(int vWidth, int vX) {
        int gWidth = 0;
        int gX  = 0;

        gX = (gWidth * vX) / vWidth;

        return gX;
    }

    public static int getGY(int vHeight, int vY) {
        int gHeight = 0;
        int gY  = 0;

        gY = (gHeight * vY) / vHeight;

        return gY;
    }

    public static int getVWidth(int vX0, int vX1) {
        int vWidth = 0;

        vWidth = vX1 - vX0;

        return vWidth;
    }

    public static int getVHeight(int vY0, int vY1) {
        int vHeight = 0;

        vHeight = vY1 - vY0;

        return vHeight;
    }

}
