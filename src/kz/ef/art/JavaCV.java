package kz.ef.art;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacv.CanvasFrame;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

public class JavaCV {

    public CvScalar binaryMinColor = cvScalar(95, 150, 75, 0);
    public CvScalar binaryMaxColor = cvScalar(145, 255, 255, 0);

    private void run() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                new ColorC(JavaCV.this);
            }
        }).start();

        CvCapture cvCapture = cvCreateCameraCapture(CV_CAP_ANY);

        IplImage iplImage;
        IplImage iplImageHSV = cvCreateImage(cvSize(640, 480), 8, 3);
        IplImage iplImageBIN = cvCreateImage(cvSize(640, 480), 8, 1);

//        CvScalar minColor = cvScalar(95, 150, 75, 0);
//        CvScalar maxColor = cvScalar(145, 255, 255, 0);

        while (true) {
            iplImage = cvQueryFrame(cvCapture);

            if (iplImage == null) {
                System.out.println("No image");
//                System.exit(0);
                break;
            }

            cvCvtColor(iplImage, iplImageHSV, CV_RGB2HSV);
//            cvInRangeS(iplImageHSV, minColor, maxColor, iplImageBIN);
            cvInRangeS(iplImageHSV, binaryMinColor, binaryMaxColor, iplImageBIN);

            cvShowImage("ImageRGB", iplImage);
            cvShowImage("ImageHSV", iplImageHSV);
            cvShowImage("ImageBIN", iplImageBIN);

            char c = (char) cvWaitKey(30);
            if(c==27) break;
        }

        cvReleaseCapture(cvCapture);
        cvReleaseImage(iplImage);
        cvReleaseImage(iplImageHSV);
        cvReleaseImage(iplImageBIN);
        cvDestroyWindow("Image");

/*
        IplImage image = cvLoadImage("EV.jpg");

        CanvasFrame canvas = new CanvasFrame("Demo");

        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); // закрытие фрейма кресиктом!

        while (true) {

            if (image == null) {
                System.out.println("No image");
                System.exit(1);
            }

            int key = cvWaitKey(30);
            if (key == 27) {
                break;
            }

        }
*/

    }

    public static void main(String[] args) {
        new JavaCV().run();
    }

}
