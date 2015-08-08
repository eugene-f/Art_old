package examples;

import kz.ef.art.graphics.GraphicsMainFrame;

public class ScannerRunner {

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        GraphicsMainFrame app = new GraphicsMainFrame();

//        Vision.Example1_Basics();
//        Vision.Example2_VideoProcessing();
//        Vision.Example3_VideoRecording();
//        Vision.Example4_ColorFiltering();
        Vision.Example5_ContourFiltering_WebCam();
//        Vision.Example5_ContourFiltering_Image();
//        Vision.Example6_Moments();
//        Vision.Example7_VirtualMouse();

    }

}
