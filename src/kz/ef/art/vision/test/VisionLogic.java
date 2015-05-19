package kz.ef.art.vision.test;

import kz.ef.art.vision.VisionColorChooserFrame;
import org.bytedeco.javacpp.opencv_core.IplImage;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class VisionLogic {

    private static final int CAP_WIDTH = 640; // 1280;
    private static final int CAP_HEIGHT = 480; // 960;
    public static boolean useCamera = false;

    int cap_img_width;
    int cap_img_height;

    public CvScalar binFilterColorMin = cvScalar(95, 150, 75, 0);
    public CvScalar binFilterColorMax = cvScalar(145, 255, 255, 0);

    IplImage imgOrig;
    IplImage img2;
    IplImage img3;

/*
Erode — размывание(операция сужения)
Dilate — растягивание(операция расширения)
*/

    /*** HSV ***/
    public static boolean hsvFlag;
    IplImage imgHsv;
    String hsv_frame = "HSV";

    /*** Bin ***/
    public static boolean binFlag;
    IplImage imgBin;
    String bin_frame = "Bin";

    /*** Bin ***/
    public static boolean smoothFlag = false;
    IplImage imgSmooth;
    String smooth_frame = "Smooth";

    /*** Erode ***/
    public static boolean erodeFlag;
    IplImage imgErode;
    String erode_frame = "Erode";
    public static int erode_radius = 0;
    public static int erode_radius_slider_max = 10;
    public static int erode_iterations = 0;
    public static int erode_iterations_slider_max = 10;

    /*** Dilate ***/
    public static boolean dilateFlag;
    IplImage imgDilate;
    String dilate_frame = "Dilate";
    public static int dilate_radius = 0;
    public static int dilate_radius_slider_max = 10;
    public static int dilate_iterations = 0;
    public static int dilate_iterations_slider_max = 10;

    /*** Channel ***/
    public static boolean channelFlag;
    String channel_frame = "Channel";
    public static int r_min = 0;
    public static int r_max = 255;
    public static int g_min = 0;
    public static int g_max = 255;
    public static int b_min = 0;
    public static int b_max = 255;
    public static int channel_rgb_slider_max = 255; // or 256

    private void run() {

        new VisionSettingsFrame();

        cvNamedWindow("Image", CV_WINDOW_AUTOSIZE);

        CvCapture cvCapture = useCamera ? cvCreateCameraCapture(CV_CAP_ANY) : cvCreateFileCapture("150424-145013.mpg");
        setCaptureSize(cvCapture);
        initCaptureSize(cvCapture);

        while (true) {
            imgOrig = cvQueryFrame(cvCapture);

            if (imgOrig == null) { System.out.println("No image"); System.exit(0); break; }

            cvShowImage("Image", imgOrig);

            if (hsvFlag) {
                if (imgHsv == null) {
                    imgHsv = cvCreateImage(cvSize(cap_img_width, cap_img_height), 8, 3); // cvGetSize
                }
                cvCvtColor(imgOrig, imgHsv, CV_RGB2HSV);
                cvShowImage(hsv_frame, imgHsv);
            } else {
                if (imgHsv != null) {
                    freeRes(hsv_frame, imgHsv);
                    imgHsv = null;
                }
            }

            if (binFlag) {
                if (imgBin == null) {
                    imgBin = cvCreateImage(cvSize(cap_img_width, cap_img_height), 8, 1);
                }
                cvInRangeS(imgOrig, binFilterColorMin, binFilterColorMax, imgBin);
                cvShowImage(bin_frame, imgBin);
            } else {
                if (imgBin != null) {
                    freeRes(bin_frame, imgBin);
                    imgBin = null;
                }
            }

            if (smoothFlag) {
                imgSmooth = smoothImg(imgOrig);
                cvShowImage(smooth_frame, imgSmooth);
            } else {
                if (imgSmooth != null) {
                    freeRes(smooth_frame, imgSmooth);
                    imgSmooth = null;
                }
            }

            if (erodeFlag) {
                imgErode = erodeImg(imgOrig, erode_radius, erode_iterations);
                cvShowImage(erode_frame, imgErode);
            } else {
                if (imgErode != null) {
                    freeRes(erode_frame, imgErode);
                    imgErode = null;
                }
            }

            if (dilateFlag) {
                imgDilate = dilateImg(imgOrig, dilate_radius, dilate_iterations);
                cvShowImage(dilate_frame, imgDilate);
            } else {
                if (imgDilate != null) {
                    freeRes(dilate_frame, imgDilate);
                    imgDilate = null;
                }
            }



            if (channelFlag) {

                IplImage oRGB;
                IplImage oR;
                IplImage oG;
                IplImage oB;

                IplImage sR;
                IplImage sG;
                IplImage sB;
                IplImage sRGB;



                oRGB = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 3);
                oR = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);
                oG = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);
                oB = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);

                sR = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);
                sG = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);
                sB = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);
                sRGB = cvCreateImage(cvGetSize(imgOrig), IPL_DEPTH_8U, 1);

                cvCopy(imgOrig, oRGB);

//                cvSplit(imgOrig, oR, oG, oB, null);
                cvSplit(imgOrig, oB, oG, oR, null);

                cvInRangeS(oR, cvScalar(r_min), cvScalar(r_max), sR);
                cvInRangeS(oG, cvScalar(g_min), cvScalar(g_max), sG);
                cvInRangeS(oB, cvScalar(b_min), cvScalar(b_max), sB);

                cvShowImage(channel_frame + " oR", oR);
                cvShowImage(channel_frame + " oG", oG);
                cvShowImage(channel_frame + " oB", oB);

                cvShowImage(channel_frame + " sR", sR);
                cvShowImage(channel_frame + " sG", sG);
                cvShowImage(channel_frame + " sB", sB);

                cvAnd(sR, sG, sRGB, null);
                cvAnd(sRGB, sB, sRGB, null);

                cvShowImage(channel_frame + " sRGB", sRGB);

                channelFlag = false;

                cvReleaseImage(oRGB);
                cvReleaseImage(oR);
                cvReleaseImage(oG);
                cvReleaseImage(oB);
                cvReleaseImage(sR);
                cvReleaseImage(sG);
                cvReleaseImage(sB);
                cvReleaseImage(sRGB);

            }



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

    private void freeRes(String form, IplImage image) {
        System.out.println("freeRes: " + form);
        cvDestroyWindow(form);
        cvReleaseImage(image);
//        image = null; // fixme: move to super method;
    }



    /*** Effects start ***/
    private IplImage dilateImg(IplImage iplImage, int radius, int iterations) {
        IplImage dilate = cvCloneImage(iplImage);
        IplConvKernel Kern = cvCreateStructuringElementEx(radius*2+1, radius*2+1, radius, radius, CV_SHAPE_ELLIPSE);
        cvDilate(iplImage, dilate, Kern, iterations);
        return dilate;
    }
    private IplImage erodeImg(IplImage iplImage, int radius, int iterations) {
        IplImage erode = cvCloneImage(iplImage);
        IplConvKernel Kern = cvCreateStructuringElementEx(radius*2+1, radius*2+1, radius, radius, CV_SHAPE_ELLIPSE);
        cvErode(iplImage, erode, Kern, iterations);
        return erode;
    }
    private IplImage smoothImg(IplImage iplImage) {
        IplImage smooth = cvCloneImage(iplImage);
        cvSmooth(iplImage, smooth, CV_GAUSSIAN, 3, 3, 0, 0);
        return smooth;
    }
    /*** Effects end ***/



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
