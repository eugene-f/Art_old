package kz.ef.art.vision.test;

import kz.ef.art.vision.VisionColorChooserFrame;
import org.bytedeco.javacpp.opencv_core.IplImage;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static kz.ef.art.vision.test.VisionEffectsSettings.*;

public class VisionLogic {

    private static final int CAP_WIDTH = 640; // 1280;
    private static final int CAP_HEIGHT = 480; // 960;
    public static boolean useCamera = false;

    static int cap_img_width;
    static int cap_img_height;

    public static CvScalar binFilterColorMin = cvScalar(95, 150, 75, 0);
    public static CvScalar binFilterColorMax = cvScalar(145, 255, 255, 0);

    IplImage imgOrig;
    IplImage imgHsv;
    IplImage imgBin;
    IplImage imgSmooth;
    IplImage imgErode;
    IplImage imgDilate;

    private IplImage imgContours;

    private void con_mom() {
        binFlag = true;

        if (imgContours == null) {
            imgContours = cvCloneImage(imgOrig);
        }

        CvMemStorage storage = CvMemStorage.create();
        CvSeq seq = new CvSeq();

        if (imgBin != null) {
            cvFindContours(imgBin, storage, seq);

            for (CvSeq seq0 = seq; seq0 != null; seq0.h_next()) {
                cvDrawContours(imgContours, seq0, CV_RGB(255,216,0), CV_RGB(0,0,250), 0, 1, 8);
            }
        }

        cvShowImage("Contours", imgContours);
    }

    private void run() {

        new VisionSettingsFrame();

        cvNamedWindow("Image", CV_WINDOW_AUTOSIZE);

        CvCapture cvCapture = useCamera ? cvCreateCameraCapture(CV_CAP_ANY) : cvCreateFileCapture("150424-145013.mpg");
        setCaptureSize(cvCapture);
        initCaptureSize(cvCapture);

        while (true) {
            imgOrig = cvQueryFrame(cvCapture);

            if (imgOrig == null) {
                System.out.println("No image");
                System.exit(0); break;
            }

            cvShowImage("Image", imgOrig);

            imgHsv = VisionEffects.effectHsv(imgOrig, imgHsv);
            imgBin = VisionEffects.effectBin(imgOrig, imgBin);
            imgSmooth = VisionEffects.effectSmooth(imgOrig, imgSmooth);
            imgErode = VisionEffects.effectErode(imgOrig, imgErode);
            imgDilate = VisionEffects.effectDilate(imgOrig, imgDilate);
            VisionEffects.effectChannels(imgOrig, null);
//            con_mom();

            char c = (char) cvWaitKey(30);
            if(c==27) break;
        }

        cvReleaseCapture(cvCapture);
        cvReleaseImage(imgOrig);
        cvReleaseImage(imgHsv);
        cvReleaseImage(imgBin);
//        cvDestroyWindow("Image");

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

    static void freeRes(String form, IplImage image) {
        if (image != null) {
            System.out.println("freeRes: " + form);
            cvDestroyWindow(form);
            cvReleaseImage(image);
            image = null;
            System.gc();
//        image = null; // fixme: move to super method;
        } else {
            System.out.println("clean");
        }
    }

    private void initCaptureSize(CvCapture cvCapture) {
        cap_img_width = (int) cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_WIDTH);
        cap_img_height = (int) cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_HEIGHT);
        System.out.println("cam_img_size: " + cap_img_width + ", " + cap_img_height);
    }

    private void setCaptureSize(CvCapture cvCapture) {
        cvSetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_WIDTH, CAP_WIDTH);
        cvSetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_HEIGHT, CAP_HEIGHT);
    }

    public static void main(String[] args) {
        final VisionLogic visionLogic = new VisionLogic();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new VisionColorChooserFrame(visionLogic);
            }
        }).start();
        visionLogic.run();
    }

}
