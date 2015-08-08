package kz.ef.art.vision;

import org.bytedeco.javacpp.Loader;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class MomentsMain {

    public CvScalar bMinC = cvScalar(95, 150, 75, 0);
    public CvScalar bMaxC = cvScalar(145, 255, 255, 0);

    public void run() {

        IplImage img1, imgHsv, imgBin;
//      CvScalar bMinC = cvScalar(95,150,75,0), bMaxC = cvScalar(145,255,255,0);
        CvScalar rMinC = cvScalar(150, 150, 75, 0), rMaxC = cvScalar(190, 255, 255, 0);

        CvSeq contour1 = new CvSeq(), contour2;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));

        double areaMax, areaC = 0;
        double m10, m01, m_area;

        int posX = 0, posY = 0;


        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        imgHsv = cvCreateImage(cvSize(640, 480), 8, 3);
        imgBin = cvCreateImage(cvSize(640, 480), 8, 1);

        int i = 1;
        while (true) {

            img1 = cvQueryFrame(capture1);

            if (img1 == null) {
                System.err.println("No Image");
                break;
            }

            cvCvtColor(img1, imgHsv, CV_BGR2HSV);
            cvInRangeS(imgHsv, bMinC, bMaxC, imgBin);

            contour1 = new CvSeq();
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

            m10 = cvGetSpatialMoment(moments, 1, 0);
            m01 = cvGetSpatialMoment(moments, 0, 1);
            m_area = cvGetCentralMoment(moments, 0, 0);

            posX = (int) (m10 / m_area);
            posY = (int) (m01 / m_area);

            if (posX > 0 && posY > 0)
                System.out.println("x = " + posX + ", y= " + posY);

            cvCircle(img1, cvPoint(posX, posY), 5, cvScalar(0, 255, 0, 0), 9, 0, 0);

            cvShowImage("Color", img1);
            cvShowImage("CF", imgBin);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;

        }

        cvReleaseImage(imgHsv);
        cvReleaseImage(imgBin);
        cvReleaseMemStorage(storage);
        cvReleaseCapture(capture1);
        cvDestroyAllWindows();

    }

}
