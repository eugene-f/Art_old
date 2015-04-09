package kz.ef.art.vision;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.helper.opencv_core.CV_RGB;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_core.cvReleaseMemStorage;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.cvGetCentralMoment;
import static org.bytedeco.javacpp.opencv_imgproc.cvGetSpatialMoment;

public class Scanner {

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        IplImage image, imagehsv, imagebin;
        CvScalar Bminc = cvScalar(95, 150, 75, 0), Bmaxc = cvScalar(145, 255, 255, 0);
        CvScalar Rminc = cvScalar(150, 150, 75, 0), Rmaxc = cvScalar(190, 255, 255, 0);
        CvScalar redMinC = cvScalar(191, 63, 63, 0), redMaxC = cvScalar(255, 255, 255, 0);


        CvSeq contour1 = new CvSeq(), contour2;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));

        double areaMax, areaC = 0;
        double m10, m01, m_area;

        int posX = 0, posY = 0;


        CvCapture cameraCapture = cvCreateCameraCapture(CV_CAP_ANY);
        imagehsv = cvCreateImage(cvSize(640, 480), 8, 3);
        imagebin = cvCreateImage(cvSize(640, 480), 8, 1);

        while (true) {

            image = cvQueryFrame(cameraCapture);

            if (image == null) {
                System.err.println("No Image");
                break;
            }

            cvCvtColor(image, imagehsv, CV_BGR2HSV);
//            cvInRangeS(imagehsv, Bminc, Bmaxc, imagebin);
            cvInRangeS(imagehsv, redMinC, redMaxC, imagebin);

            contour1 = new CvSeq();
            areaMax = 1000;

            cvFindContours(imagebin, storage, contour1, Loader.sizeof(CvContour.class),
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
                    cvDrawContours(imagebin, contour2, CV_RGB(0, 0, 0), CV_RGB(0, 0, 0),
                            0, CV_FILLED, 8, cvPoint(0, 0));
                }

                contour2 = contour2.h_next();
            }

            cvMoments(imagebin, moments, 1);

            m10 = cvGetSpatialMoment(moments, 1, 0);
            m01 = cvGetSpatialMoment(moments, 0, 1);
            m_area = cvGetCentralMoment(moments, 0, 0);

            posX = (int) (m10 / m_area);
            posY = (int) (m01 / m_area);

            if (posX > 0 && posY > 0) {
                System.out.println("x = " + posX + ", y = " + posY);
            }

            cvCircle(image, cvPoint(posX, posY), 5, cvScalar(0, 255, 0, 0), 9, 0, 0);

            cvShowImage("Color", image);
            cvShowImage("CF", imagebin);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;

        }

        cvReleaseImage(imagehsv);
        cvReleaseImage(imagebin);
        cvReleaseMemStorage(storage);
        cvReleaseCapture(cameraCapture);

    }

}
