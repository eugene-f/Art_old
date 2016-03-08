package kz.ef.art.vision;

import kz.ef.art.graphics.GraphicsMainFrame;
import org.bytedeco.javacpp.Loader;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class MomentsMain {

    public static boolean withGraphics = false;

    public static final CvScalar COLOR_MIN = cvScalar(0, 31, 31, 0);
    public static final CvScalar COLOR_MAX = cvScalar(255, 255, 255, 0);

    private static final int DELTA_WIDTH = 4;
    private static final int DELTA_HEIGHT = 3;
    private static final int DELTA_POSITION = 3;

    private int targetAreaX = 100;
    private int targetAreaY = 100;
    private int targetAreaWidth = 400;
    private int targetAreaHeight = 300;

    public void run() {
        IplImage imageOriginal, imageBinary;
        CvSeq contour1, contour2;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));

        double areaMax, areaC = 0;
        double m10, m01, m_area;

        int posX = -1;
        int posY = -1;

        CvCapture capture = cvCreateCameraCapture(CV_CAP_ANY);
        imageBinary = cvCreateImage(cvSize(640, 480), 8, 1);

        int foundCount = 0;

        imageOriginal = cvQueryFrame(capture);
        if (imageOriginal != null) {
            targetAreaWidth = imageOriginal.width() / 2;
            targetAreaHeight = imageOriginal.height() / 2;
            targetAreaX = targetAreaWidth - targetAreaWidth / 2;
            targetAreaY = targetAreaHeight - targetAreaHeight / 2;
        }

        while (true) {

            imageOriginal = cvQueryFrame(capture);

            if (imageOriginal == null) {
                System.err.println("No Image");
                break;
            }

            cvInRangeS(imageOriginal, COLOR_MIN, COLOR_MAX, imageBinary);
            cvSetImageROI(imageBinary, cvRect(targetAreaX, targetAreaY, targetAreaWidth, targetAreaHeight));

            contour1 = new CvSeq();
            areaMax = 7;

            cvFindContours(imageBinary, storage, contour1, Loader.sizeof(CvContour.class),
                    CV_RETR_LIST, CV_LINK_RUNS, cvPoint(0, 0));

            contour2 = contour1;

            while (contour1 != null && !contour1.isNull()) {
                areaC = cvContourArea(contour1, CV_WHOLE_SEQ, 1);
                if (areaC > areaMax) {
                    areaMax = areaC;
                }
                contour1 = contour1.h_next();
            }

            while (contour2 != null && !contour2.isNull()) {
                areaC = cvContourArea(contour2, CV_WHOLE_SEQ, 1);
                if (areaC < areaMax) {
                    cvDrawContours(imageBinary, contour2, CV_RGB(0, 0, 0), CV_RGB(0, 0, 0),
                            0, CV_FILLED, 8, cvPoint(0, 0));
                }
                contour2 = contour2.h_next();
            }

            cvMoments(imageBinary, moments, 1);

            m10 = cvGetSpatialMoment(moments, 1, 0);
            m01 = cvGetSpatialMoment(moments, 0, 1);
            m_area = cvGetCentralMoment(moments, 0, 0);

            posX = (int) (m10 / m_area);
            posY = (int) (m01 / m_area);

            if (posX != -1 && posY != -1) {
                int pointX = targetAreaX + posX;
                int pointY = targetAreaY + posY;
                System.out.println("x=" + pointX + ", y=" + pointY);
                cvCircle(imageOriginal, cvPoint(pointX, pointY), 3, cvScalar(0, 0, 255, 0), 9, 0, 0);
                foundCount++;
            } else {
                foundCount = 0;
            }

            if (withGraphics) {
                if (foundCount == 1) {
//                    GraphicsMainFrame.getInstance().shot(posX, posY);
                    GraphicsMainFrame.getInstance().shot(posX, posY, targetAreaWidth, targetAreaHeight);
                }
            }

            char ch = (char) cvWaitKey(15);
            switch (ch) {
                case '3': {
                    targetAreaWidth += DELTA_WIDTH;
                    targetAreaHeight += DELTA_HEIGHT;
                    break;
                }
                case '7': {
                    targetAreaWidth -= DELTA_WIDTH;
                    targetAreaHeight -= DELTA_HEIGHT;
                    break;
                }
                case '4':
                    targetAreaX -= DELTA_POSITION;
                    break;
                case '6':
                    targetAreaX += DELTA_POSITION;
                    break;
                case '8':
                    targetAreaY -= DELTA_POSITION;
                    break;
                case '2':
                    targetAreaY += DELTA_POSITION;
                    break;
            }

            cvResetImageROI(imageBinary);
            cvRectangle(
                    imageOriginal,
                    cvPoint(targetAreaX, targetAreaY),
                    cvPoint(targetAreaX + targetAreaWidth, targetAreaY + targetAreaHeight),
                    cvScalar(0, 255, 255, 0)
            );

            cvShowImage("Original", imageOriginal);
//            cvShowImage("Binary", imgBin);
            char c = (char) cvWaitKey(15);
            if (c == 'q') break;

        }

        cvReleaseImage(imageOriginal);
        cvReleaseImage(imageBinary);
        cvReleaseMemStorage(storage);
        cvReleaseCapture(capture);
        cvDestroyAllWindows();

    }

}
