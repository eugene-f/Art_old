package kz.ef.art.vision.test;

import kz.ef.art.vision.VisionColorChooserFrame;

import static kz.ef.art.vision.test.VisionEffectsParams.binFlag;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.cvFindContours;

public class VisionMainLogic {

    private static final int CAP_WIDTH = 640; // 1280;
    private static final int CAP_HEIGHT = 480; // 960;
    public static boolean useCamera = true;

    static int cap_img_width;
    static int cap_img_height;

    public static CvScalar binFilterColorMin = cvScalar(95, 150, 75, 0);
    public static CvScalar binFilterColorMax = cvScalar(145, 255, 255, 0);

    private IplImage imgOrig;
    private IplImage imgHsv;
    private IplImage imgBin;
    private IplImage imgSmooth;
    private IplImage imgErode;
    private IplImage imgDilate;

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

            for (; seq != null; seq.h_next()) {
                cvDrawContours(imgContours, seq, CV_RGB(255, 216, 0), CV_RGB(0, 0, 250), 0, 1, 8);
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
        cvSetCaptureProperty(cvCapture, CV_CAP_PROP_POS_AVI_RATIO, 0.8); // repeat video

        while (true) {
            imgOrig = cvQueryFrame(cvCapture);

            if (cvGetCaptureProperty(cvCapture, CV_CAP_PROP_POS_AVI_RATIO) == 0.9) {
                cvSetCaptureProperty(cvCapture, CV_CAP_PROP_POS_AVI_RATIO, 0); // repeat video
                continue;
            }

            if (imgOrig == null) {
                System.out.println("No image");
                System.exit(0);
                break;
            }

            cvShowImage("Image", imgOrig);

            imgHsv = VisionEffects.effectHsv(imgOrig, imgHsv);
            imgBin = VisionEffects.effectBin(imgOrig, imgBin);
            imgSmooth = VisionEffects.effectSmooth(imgOrig, imgSmooth);
            imgErode = VisionEffects.effectErode(imgOrig, imgErode);
            imgDilate = VisionEffects.effectDilate(imgOrig, imgDilate);
//            VisionEffects.effectChannels(imgOrig, null);
            VisionEffects.effectChannels(imgErode);
//            con_mom();


            char c = (char) cvWaitKey(30);
            if (c == 27) break;
        }

        cvReleaseCapture(cvCapture);
        cvReleaseImage(imgOrig);
        cvReleaseImage(imgHsv);
        cvReleaseImage(imgBin);
//        cvDestroyWindow("Image");

/*
        IplImage image = cvLoadImage("EV.jpg");
        CanvasFrame canvas = new CanvasFrame("Demo");
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
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

    @SuppressWarnings("UnusedAssignment")
    static void freeRes(String form, IplImage image) {
        if (image != null) {
//            System.out.println("freeRes: " + form);
            cvDestroyWindow(form);
            cvReleaseImage(image);
            image = null;
            System.gc();
//        image = null; // fixme: move to super method;
        } else /*System.out.println("clean")*/;
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
        final VisionMainLogic visionMainLogic = new VisionMainLogic();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new VisionColorChooserFrame(visionMainLogic);
            }
        }).start();
        visionMainLogic.run();
    }

}
