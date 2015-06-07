package kz.ef.art.vision.test;

public class VisionEffectsSettings {

    /*** HSV ***/
    public static boolean hsvFlag;
    static String hsvFrame = "HSV";

    /*** Bin ***/
    public static boolean binFlag;
    static String binFrame = "Bin";

    /*** Smooth ***/
    public static boolean smoothFlag = false;
    static String smoothFrame = "Smooth";

    /*** Erode ***/
    public static boolean erodeFlag;
    static String erodeFrame = "Erode";
    public static int erodeRadius = 0;
    public static int erodeRadiusMax = 10;
    public static int erodeIterations = 0;
    public static int erodeIterationsMax = 10;

    /*** Dilate ***/
    public static boolean dilateFlag;
    static String dilateFrame = "Dilate";
    public static int dilateRadius = 0;
    public static int dilateRadiusMax = 10;
    public static int dilateIterations = 0;
    public static int dilateIterationsMax = 10;

    /*** Channel ***/
    public static boolean channelsFlag;
    static String channelFrame = "Channel";
    public static boolean channelFlagR;
    public static int rMin = 0;
    public static int rMax = 255;
    public static boolean channelFlagG;
    public static int gMin = 0;
    public static int gMax = 255;
    public static boolean channelFlagB;
    public static int bMin = 0;
    public static int bMax = 255;
    public static int channelRgbMax = 255; // or 256

}
