package examples;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_highgui.*;
import org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.FrameRecorder.Exception;
import org.bytedeco.javacv.OpenCVFrameRecorder;

import java.awt.*;
import java.awt.event.InputEvent;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class Vision {

//    http://engineervisions.blogspot.com/p/javacv.html

    public static void Example1_Basics() {

        /*
        http://engineervisions.blogspot.in/2013/11/javacv-tutorials.html
        https://www.youtube.com/watch?v=YkC55xDREKE
        Source Code :  " https://drive.google.com/file/d/0Bxr1St4kFOnQS3d1VEpjc0ZxZHM/edit?usp=sharing "

        In this tutorial I've explained some simple image transformation techniques such as converting the RGB image into a HSV and Grayscale and saving the image file in different locations .
        In "cvWaitKey()" function, you can include an integer value to indicate the amount time that the function has to wait for the user input.
        Eg: cvWaitKey(1000) will wait for 1second for user I/p and automatically executes the statements below it if doesn't get any user i/p within the time specified.
        */

        IplImage img = cvLoadImage("EV.jpg");

        IplImage hsvimg = cvCreateImage(cvGetSize(img), IPL_DEPTH_8U, 3);
        IplImage grayimg = cvCreateImage(cvGetSize(img), IPL_DEPTH_8U, 1);

        cvCvtColor(img, hsvimg, CV_BGR2HSV);
        cvCvtColor(img, grayimg, CV_BGR2GRAY);

        cvShowImage("Original", img);
        cvShowImage("HSV", hsvimg);
        cvShowImage("GRAY", grayimg);
        cvWaitKey();

        cvSaveImage("Original.jpg", img);
        cvSaveImage("HSV.jpg", hsvimg);
        cvSaveImage("GRAY.jpg", grayimg);

        cvReleaseImage(img);
        cvReleaseImage(hsvimg);
        cvReleaseImage(grayimg);

    }

    public static void Example2_VideoProcessing() {

        /*
        http://engineervisions.blogspot.in/2013/11/javacv-tutorial-2-video-processing.html
        https://www.youtube.com/watch?v=gBImQDZsMxo
        Source Code: " https://drive.google.com/file/d/0Bxr1St4kFOnQT3ZhbFlTS2V1UFE/edit?usp=sharing "

        In this tutorial I've explained some basic video processing operations such as getting the input from a Video file or a webcam, process those frame and apply some image trans formation operations.
        In the function "cvCreateFileCapture("");" , the default location is the project directory from where it loads the Video file, you can also include the path of the video file inside quotes just like in the last tutorial I've explained.
        In the function "cvCreateCameraCapture();", 'CV_CAP_ANY ' argument detects the default webcam connected to PC and gets the input from that. But if you are having multiple webcams then you can include arguments like :
        Arguments           Numerical Value
        CV_CAP_ANY          0
        CV_CAP_MIL          100
        CV_CAP_VFW          200
        CV_CAP_V4L          200
        CV_CAP_V4L2         200
        CV_CAP_FIREWIRE     300
        CV_CAP_IEEE1394     300
        CV_CAP_DC1394       300
        CV_CAP_CMU1394      300

        In " if(c==27) " statement you can compare the value with any other key pressed and break the loop, visit the link below to know the ascii values of different keys :
        " http://www.lucidtechnologies.info/ascii.htm "
        */

        CvCapture capture = cvCreateFileCapture("Vid.mp4"); //cvCreateCameraCapture(CV_CAP_ANY);  //
        IplImage frame;
        IplImage grayimg = cvCreateImage(cvSize(640, 480), IPL_DEPTH_8U, 1);

        cvNamedWindow("Video", CV_WINDOW_AUTOSIZE);

        for (; ; ) {
            frame = cvQueryFrame(capture);
            if (frame == null) {
                System.out.println("ERROR: NO Video File");
                break;
            }

            cvCvtColor(frame, grayimg, CV_BGR2GRAY);
            cvShowImage("Video", grayimg);
            char c = (char) cvWaitKey(30);

            if (c == 27) break;
        }

        cvReleaseCapture(capture);
        cvDestroyWindow("Video");

    }

    public static void Example3_VideoRecording() throws Exception {

        /*
        http://engineervisions.blogspot.in/2013/11/javacv-tutorial-3-video-recording.html
        https://www.youtube.com/watch?v=oliqc3B9Wrc
        Source Code: " https://drive.google.com/file/d/0Bxr1St4kFOnQcDFZRWczc0xwR3c/edit?usp=sharing "

        In this tutorial I've explained the process of recording a video from the Webcam with different resolutions and with different formats.
        Setting different Resolutions :
        " cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_WIDTH,Wd);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_HEIGHT,Ht); "

        " FrameRecorder recorder1 = new OpenCVFrameRecorder("RecordVid.avi",Wt , Ht); "

        "Wd = 1920;1280;720;640;320;
          Ht = 1080;720;480;360;240; "

        Setting Different Codecs :
        For MJPG: "  recorder1.setVideoCodec(CV_FOURCC('M','J','P','G')); "
        For DIVX: "  recorder1.setVideoCodec(CV_FOURCC('D','I','V','X')); "
        For MP4V: "  recorder1.setVideoCodec(CV_FOURCC('M','P','4','V')); "
        */

        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        cvSetCaptureProperty(capture1, CV_CAP_PROP_FRAME_WIDTH, 640);
        cvSetCaptureProperty(capture1, CV_CAP_PROP_FRAME_HEIGHT, 480);

        cvNamedWindow("LiveVid", CV_WINDOW_AUTOSIZE);

        FrameRecorder recorder1 = new OpenCVFrameRecorder("RecordVid.avi", 640, 480);
//        recorder1.setVideoCodec(CV_FOURCC('M','J','P','G')); // FixMe: Disable comment on this line
        recorder1.setFrameRate(15);
        recorder1.setPixelFormat(1);
        recorder1.start();

        IplImage img1;

        for (; ; ) {

            img1 = cvQueryFrame(capture1);

            if (img1 == null) break;

            cvShowImage("LiveVid", img1);
            recorder1.record(img1);

            char c = (char) cvWaitKey(15);
            if (c == 'q') break;

        }

        recorder1.stop();
        cvDestroyWindow("LiveVid");
        cvReleaseCapture(capture1);

    }

    public static void Example4_ColorFiltering() {

        /*
        http://engineervisions.blogspot.in/2013/12/javacv-tutorial-4-color-filtering.html
        https://www.youtube.com/watch?v=ZPEdSu4AD_k&list=UUrJS7ZbccF4slEomoJFdAyQ
        Source Codes:  " https://drive.google.com/file/d/0Bxr1St4kFOnQb0FyYnl3aDBfRnM/edit?usp=sharing "

        In this tutorial I've explained the color filtering operations in javacv using the function "cvInRangeS()" .
        */

        IplImage img1, imghsv, imgbin;

        imghsv = cvCreateImage(cvSize(640, 480), 8, 3);
        imgbin = cvCreateImage(cvSize(640, 480), 8, 1);

        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);

        int i = 1;

        while (i == 1) {

            img1 = cvQueryFrame(capture1);

            if (img1 == null) break;

            cvCvtColor(img1, imghsv, CV_BGR2HSV);
            CvScalar minc = cvScalar(95, 150, 75, 0), maxc = cvScalar(145, 255, 255, 0);
            cvInRangeS(imghsv, minc, maxc, imgbin);

            cvShowImage("color", img1);
            cvShowImage("Binary", imgbin);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;

        }

        cvReleaseImage(imghsv);
        cvReleaseImage(imgbin);
        cvReleaseCapture(capture1);

    }

    public static void Example5_ContourFiltering_WebCam() {

        /*
        http://engineervisions.blogspot.in/2013/12/javacv-tutorial-5-contour-filtering.html
        https://www.youtube.com/watch?v=8pDBz7e5L_s
        Source Codes:  " https://drive.google.com/file/d/0Bxr1St4kFOnQV1ozWkNQbzh0TXM/edit?usp=sharing "

        In this tutorial I've briefly explained the contour detection and filtering operations in opencv-javacv. The filtering operation is based on the maximum area of the contour detected.
        */

        IplImage img1, imghsv, imgbin;
        CvScalar minc = cvScalar(95, 150, 75, 0), maxc = cvScalar(145, 255, 255, 0);
        CvSeq contour1 = new CvSeq(), contour2;
        CvMemStorage storage = CvMemStorage.create();
        double areaMax, areaC = 0;

        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        imghsv = cvCreateImage(cvSize(640, 480), 8, 3);
        imgbin = cvCreateImage(cvSize(640, 480), 8, 1);

        int i = 1;
        while (i == 1) {

            img1 = cvQueryFrame(capture1);

            if (img1 == null) break;

            cvCvtColor(img1, imghsv, CV_BGR2HSV);
            cvInRangeS(imghsv, minc, maxc, imgbin);

            contour1 = new CvSeq();
            areaMax = 1000;

            cvFindContours(imgbin, storage, contour1, Loader.sizeof(CvContour.class),
                    CV_RETR_LIST, CV_LINK_RUNS, cvPoint(0, 0));

            contour2 = contour1;

            while (contour1 != null && !contour1.isNull()) {
                areaC = cvContourArea(contour1, CV_WHOLE_SEQ, 1);

                if (areaC > areaMax)
                    areaMax = areaC;

                contour1 = contour1.h_next();

            }

            while (contour2 != null && !contour2.isNull()) {
                areaC = cvContourArea(contour2, CV_WHOLE_SEQ, 1);

                if (areaC < areaMax) {
                    cvDrawContours(imgbin, contour2, CV_RGB(0, 0, 0), CV_RGB(0, 0, 0),
                            0, CV_FILLED, 8, cvPoint(0, 0));
                }

                contour2 = contour2.h_next();
            }

            cvShowImage("Color", img1);
            cvShowImage("CF", imgbin);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;


        }


        cvReleaseImage(imghsv);
        cvReleaseImage(imgbin);
        cvReleaseMemStorage(storage);
        cvReleaseCapture(capture1);

    }

    public static void Example5_ContourFiltering_Image() {

        IplImage img1, imghsv, imgbin;
        CvScalar minc = cvScalar(40, 150, 75, 0), maxc = cvScalar(80, 255, 255, 0);
        CvSeq contour1 = new CvSeq(), contour2;
        CvMemStorage storage = CvMemStorage.create();
        double areaMax = 1000, areaC = 0;


        img1 = cvLoadImage("ColorImg.jpg");
        imghsv = cvCreateImage(cvGetSize(img1), 8, 3);
        imgbin = cvCreateImage(cvGetSize(img1), 8, 1);

        cvCvtColor(img1, imghsv, CV_BGR2HSV);
        cvInRangeS(imghsv, minc, maxc, imgbin);

        cvShowImage("Binary", imgbin);

        cvFindContours(imgbin, storage, contour1, Loader.sizeof(CvContour.class),
                CV_RETR_LIST, CV_LINK_RUNS, cvPoint(0, 0));

        contour2 = contour1;

        while (contour1 != null && !contour1.isNull()) {
            areaC = cvContourArea(contour1, CV_WHOLE_SEQ, 1);

            if (areaC > areaMax)
                areaMax = areaC;

            contour1 = contour1.h_next();

        }

        while (contour2 != null && !contour2.isNull()) {
            areaC = cvContourArea(contour2, CV_WHOLE_SEQ, 1);

            if (areaC < areaMax) {
                cvDrawContours(imgbin, contour2, CV_RGB(0, 0, 0), CV_RGB(0, 0, 0),
                        0, CV_FILLED, 8, cvPoint(0, 0));
            }

            contour2 = contour2.h_next();
        }

        cvShowImage("Color", img1);
        cvShowImage("CF", imgbin);
        cvWaitKey();

        cvReleaseImage(img1);
        cvReleaseImage(imghsv);
        cvReleaseImage(imgbin);
        cvReleaseMemStorage(storage);

    }

    public static void Example6_Moments() {

        /*
        http://engineervisions.blogspot.in/2014/03/javacv-tutorial-6-moments.html
        https://www.youtube.com/watch?feature=player_embedded&v=eCVKTI_Czrw
        Source Code: " https://drive.google.com/file/d/0Bxr1St4kFOnQQ3lBRGt0a1FQQUU/edit?usp=sharing "

        This is the Coding Part of Vision-6 tutorial. In this tutorial I've briefly explained the concept " moments ". Moments are calculated using formula:

        m_(p,q) = E(n,i=1)(I(x,y)x^(p)x^(q))
        http://2.bp.blogspot.com/-B3pLgK6nSmo/Uxa3Ub0yB5I/AAAAAAAAAQ0/FP2EisHCO7c/s1600/mnts.png

        Here p is the x-order and q is the y-order, whereby order means the power to which the corresponding component is taken in the sum just displayed. The summation is over all of the pixels of the contour boundary (denoted by n in the equation). It then follows immediately that if p and q are both equal to 0, then the m00 moment is actually just the length in pixels of the contour.

        The function that computes these moments for us is
        void cvContoursMoments(
                                                  CvSeq contour,
                                                  CvMoments moments
                                                   );

        The first argument is the contour we are interested in and the second is a pointer to a structure that we must allocate to hold the return data. The CvMoments structure is defined as follows:

        typedef struct CvMoments {

        // spatial moments
        double m00, m10, m01, m20, m11, m02, m30, m21, m12, m03;

        // central moments
        double mu20, mu11, mu02, mu30, mu21, mu12, mu03;

        // m00 != 0 ? 1/sqrt(m00) : 0
        double inv_sqrt_m00;

        } CvMoments;

        The spatial moments give information about the object in the image, i.e. related (dependent) on the object position.

        The central moments are adjusted for translational invariance, by moving the origin of the "coordinate system" used for calculations to the centroid (center of gravity) of the object in question.

        The central normalized moments are scaled by the area of the object, and are thus scale invariant in addition to translational invariance.

        double cvGetCentralMoment(
                                                       CvMoments* moments,
                                                       int x_order,
                                                       int y_order
                                                        );


        double cvGetSpatialMoment(
                                                      CvMoments* moments,
                                                      int x_order,
                                                      int y_order
                                                       );

        double cvGetNormalizedCentralMoment(
                                                      CvMoments* moments,
                                                      int x_order,
                                                      int y_order
                                                      );

        void cvGetHuMoments(
                                                     CvMoments* moments,
                                                     CvHuMoments* HuMoments
                                              );
        */

        IplImage img1, imghsv, imgbin;
        CvScalar Bminc = cvScalar(95, 150, 75, 0), Bmaxc = cvScalar(145, 255, 255, 0);
        CvScalar Rminc = cvScalar(150, 150, 75, 0), Rmaxc = cvScalar(190, 255, 255, 0);


        CvSeq contour1 = new CvSeq(), contour2;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));

        double areaMax, areaC = 0;
        double m10, m01, m_area;

        int posX = 0, posY = 0;


        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        imghsv = cvCreateImage(cvSize(640, 480), 8, 3);
        imgbin = cvCreateImage(cvSize(640, 480), 8, 1);

        int i = 1;
        while (i == 1) {

            img1 = cvQueryFrame(capture1);

            if (img1 == null) {
                System.err.println("No Image");
                break;
            }

            cvCvtColor(img1, imghsv, CV_BGR2HSV);
            cvInRangeS(imghsv, Bminc, Bmaxc, imgbin);

            contour1 = new CvSeq();
            areaMax = 1000;

            cvFindContours(imgbin, storage, contour1, Loader.sizeof(CvContour.class),
                    CV_RETR_LIST, CV_LINK_RUNS, cvPoint(0, 0));

            contour2 = contour1;

            while (contour1 != null && !contour1.isNull()) {
                areaC = cvContourArea(contour1, CV_WHOLE_SEQ, 1);

                if (areaC > areaMax)
                    areaMax = areaC;

                contour1 = contour1.h_next();

            }

            while (contour2 != null && !contour2.isNull()) {
                areaC = cvContourArea(contour2, CV_WHOLE_SEQ, 1);

                if (areaC < areaMax) {
                    cvDrawContours(imgbin, contour2, CV_RGB(0, 0, 0), CV_RGB(0, 0, 0),
                            0, CV_FILLED, 8, cvPoint(0, 0));
                }

                contour2 = contour2.h_next();
            }

            cvMoments(imgbin, moments, 1);

            m10 = cvGetSpatialMoment(moments, 1, 0);
            m01 = cvGetSpatialMoment(moments, 0, 1);
            m_area = cvGetCentralMoment(moments, 0, 0);

            posX = (int) (m10 / m_area);
            posY = (int) (m01 / m_area);

            if (posX > 0 && posY > 0)
                System.out.println("x = " + posX + ", y= " + posY);

            cvCircle(img1, cvPoint(posX, posY), 5, cvScalar(0, 255, 0, 0), 9, 0, 0);

            cvShowImage("Color", img1);
            cvShowImage("CF", imgbin);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;


        }


        cvReleaseImage(imghsv);
        cvReleaseImage(imgbin);
        cvReleaseMemStorage(storage);
        cvReleaseCapture(capture1);

    }

    public static void Example7_VirtualMouse() throws AWTException {

        /*
        http://engineervisions.blogspot.in/2014/03/javacv-tutorial-6-virtual-mouse.html
        https://www.youtube.com/watch?feature=player_embedded&v=M714ZeGfZT0
        Source Code:" https://drive.google.com/file/d/0Bxr1St4kFOnQTnc5NGh4clBvdnc/edit?usp=sharing "

        In this tutorial I have explained about controlling a mouse with some simple hand gestures. You can use this technique to either control a mouse or input the coordinate values to a microcontroller- servos to move a webcam in the direction of the object and so on.
        */

        IplImage img1, imgbinG, imgbinB;
        IplImage imghsv;


        CvScalar Bminc = cvScalar(95, 150, 75, 0), Bmaxc = cvScalar(145, 255, 255, 0);
        CvScalar Gminc = cvScalar(40, 50, 60, 0), Gmaxc = cvScalar(80, 255, 255, 0);

        //img1 = cvLoadImage("Pic.jpg");
        CvArr mask;
        int w = 320, h = 240;
        imghsv = cvCreateImage(cvSize(w, h), 8, 3);
        imgbinG = cvCreateImage(cvSize(w, h), 8, 1);
        imgbinB = cvCreateImage(cvSize(w, h), 8, 1);
        IplImage imgC = cvCreateImage(cvSize(w, h), 8, 1);
        CvSeq contour1 = new CvSeq(), contour2 = null;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));

        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        cvSetCaptureProperty(capture1, CV_CAP_PROP_FRAME_WIDTH, w);
        cvSetCaptureProperty(capture1, CV_CAP_PROP_FRAME_HEIGHT, h);

        //int i=1;
        while (true) {

            img1 = cvQueryFrame(capture1);
            if (img1 == null) {
                System.err.println("No Image");
                break;
            }

            imgbinB = VccmFilter.Filter(img1, imghsv, imgbinB, Bmaxc, Bminc, contour1, contour2, storage, moments, 1, 0); // ToDo: Renamed from 'ccmFilter'
            imgbinG = VccmFilter.Filter(img1, imghsv, imgbinG, Gmaxc, Gminc, contour1, contour2, storage, moments, 0, 1);

            cvOr(imgbinB, imgbinG, imgC, mask = null);
            cvShowImage("Combined", imgC);
            cvShowImage("Original", img1);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;

        }
        cvReleaseImage(imghsv);
        cvReleaseImage(imgbinG);
        cvReleaseImage(imgbinB);
        cvReleaseImage(imghsv);
        cvReleaseMemStorage(storage);
        cvReleaseCapture(capture1);

    }

}

class VccmFilter {

    public static int t;

    public static IplImage Filter(IplImage img, IplImage imghsv, IplImage imgBin,
                                  CvScalar maxc, CvScalar minc,
                                  CvSeq contour1, CvSeq contour2, CvMemStorage storage, CvMoments moments,
                                  int b, int g) throws AWTException {

        double moment10, moment01, areaMax, areaC = 0, m_area;
        int posX = 0, posY = 0;
        Robot rbt = new Robot();

        cvCvtColor(img, imghsv, CV_BGR2HSV);
        cvInRangeS(imghsv, minc, maxc, imgBin);

        areaMax = 1000;

        cvFindContours(imgBin, storage, contour1, Loader.sizeof(CvContour.class),
                CV_RETR_LIST, CV_LINK_RUNS, cvPoint(0, 0));

        contour2 = contour1;

        while (contour1 != null && !contour1.isNull()) {
            areaC = cvContourArea(contour1, CV_WHOLE_SEQ, 1);

            if (areaC > areaMax)
                areaMax = areaC;

            contour1 = contour1.h_next();

        }

        while (contour2 != null && !contour2.isNull()) {
            areaC = cvContourArea(contour2, CV_WHOLE_SEQ, 1);

            if (areaC < areaMax) {
                cvDrawContours(imgBin, contour2, CV_RGB(0, 0, 0), CV_RGB(0, 0, 0),
                        0, CV_FILLED, 8, cvPoint(0, 0));
            }

            contour2 = contour2.h_next();
        }


        cvMoments(imgBin, moments, 1);

        moment10 = cvGetSpatialMoment(moments, 1, 0);
        moment01 = cvGetSpatialMoment(moments, 0, 1);
        m_area = cvGetCentralMoment(moments, 0, 0);


        posX = (int) (moment10 / m_area);
        posY = (int) (moment01 / m_area);

        if (b == 1)
            if (posX > 0 && posY > 0) {

                rbt.mouseMove(posX * 4, posY * 3);

            }

        if (g == 1) {
            if (posX > 0 && posY > 0) {
                rbt.mousePress(InputEvent.BUTTON1_MASK);
                t++;
            } else if (t > 0) {
                rbt.mouseRelease(InputEvent.BUTTON1_MASK);
                t = 0;
            }

        }


        return imgBin;

    }

}
