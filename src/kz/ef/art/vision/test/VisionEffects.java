package kz.ef.art.vision.test;

import static kz.ef.art.vision.test.VisionEffectsSettings.*;
import static kz.ef.art.vision.test.VisionEffectsSettings.channelFrame;
import static kz.ef.art.vision.test.VisionEffectsSettings.channelsFlag;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvAnd;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_highgui.cvShowImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class VisionEffects {

    // Erode Ч размывание(операци€ сужени€)
    // Dilate Ч раст€гивание(операци€ расширени€)

    /*** Effects Logic ***/

    static IplImage effectHsv(IplImage image, IplImage result) {
        if (hsvFlag) {
            System.out.println("hsvFlag == true");
            if (result == null) {
                System.out.println("result == null");
                result = cvCreateImage(cvSize(VisionLogic.cap_img_width, VisionLogic.cap_img_height), 8, 3); // cvGetSize
            }
            cvCvtColor(image, result, CV_RGB2HSV);
            cvShowImage(hsvFrame, result);
            System.out.println("hsvDone");
        } else {
            System.out.println("hsvFlag == false");
//            if (result != null) {
                VisionLogic.freeRes(hsvFrame, result);
//                result = null;
//            }
        }
        return result;
    }

    static IplImage effectBin(IplImage image, IplImage result) {
        if (binFlag) {
            if (result == null) {
                result = cvCreateImage(cvSize(VisionLogic.cap_img_width, VisionLogic.cap_img_height), 8, 1);
            }
            cvInRangeS(image, VisionLogic.binFilterColorMin, VisionLogic.binFilterColorMax, result);
            cvShowImage(binFrame, result);
        } else {
            if (result != null) {
                VisionLogic.freeRes(binFrame, result);
                result = null;
            }
        }
        return result;
    }

    static IplImage effectErode(IplImage image, IplImage result) {
        if (erodeFlag) {
            result = erodeImg(image, erodeRadius, erodeIterations);
            cvShowImage(erodeFrame, result);
        } else {
            if (result != null) {
                VisionLogic.freeRes(erodeFrame, result);
                result = null;
            }
        }
        return result;
    }

    static IplImage effectDilate(IplImage image, IplImage result) {
        if (dilateFlag) {
            result = dilateImg(image, dilateRadius, dilateIterations);
            cvShowImage(dilateFrame, result);
        } else {
            if (result != null) {
                VisionLogic.freeRes(dilateFrame, result);
                result = null;
            }
        }
        return result;
    }

    static IplImage effectSmooth(IplImage image, IplImage result) {
        if (smoothFlag) {
            result = smoothImg(image);
            cvShowImage(smoothFrame, result);
        } else {
            if (result != null) {
                VisionLogic.freeRes(smoothFrame, result);
                result = null;
            }
        }
        return result;
    }

    /*** Effects start ***/
    static IplImage dilateImg(IplImage iplImage, int radius, int iterations) {
        IplImage dilate = cvCloneImage(iplImage);
        IplConvKernel Kern = cvCreateStructuringElementEx(radius*2+1, radius*2+1, radius, radius, CV_SHAPE_ELLIPSE);
        cvDilate(iplImage, dilate, Kern, iterations);
        return dilate;
    }
    static IplImage erodeImg(IplImage iplImage, int radius, int iterations) {
        IplImage erode = cvCloneImage(iplImage);
        IplConvKernel Kern = cvCreateStructuringElementEx(radius*2+1, radius*2+1, radius, radius, CV_SHAPE_ELLIPSE);
        cvErode(iplImage, erode, Kern, iterations);
        return erode;
    }
    static IplImage smoothImg(IplImage iplImage) {
        IplImage smooth = cvCloneImage(iplImage);
        cvSmooth(iplImage, smooth, CV_GAUSSIAN, 3, 3, 0, 0);
        return smooth;
    }
    /*** Effects end ***/

    static IplImage effectChannels(IplImage image, IplImage result) {
        if (channelsFlag) {
            IplImage oRGB;
            IplImage oR;
            IplImage oG;
            IplImage oB;
            IplImage sR;
            IplImage sG;
            IplImage sB;
            IplImage sRGB;

            oRGB = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 3);
            oR = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            oG = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            oB = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            sR = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            sG = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            sB = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);
            sRGB = cvCreateImage(cvGetSize(image), IPL_DEPTH_8U, 1);

            cvCopy(image, oRGB);
//                cvSplit(imgOrig, oR, oG, oB, null);
            cvSplit(image, oB, oG, oR, null);

            cvInRangeS(oR, cvScalar(rMin), cvScalar(rMax), sR);
            cvInRangeS(oG, cvScalar(gMin), cvScalar(gMax), sG);
            cvInRangeS(oB, cvScalar(bMin), cvScalar(bMax), sB);

            cvShowImage(channelFrame + " oR", oR);
            cvShowImage(channelFrame + " oG", oG);
            cvShowImage(channelFrame + " oB", oB);

            cvShowImage(channelFrame + " sR", sR);
            cvShowImage(channelFrame + " sG", sG);
            cvShowImage(channelFrame + " sB", sB);

            cvAnd(sR, sG, sRGB, null);
            cvAnd(sRGB, sB, sRGB, null);

            cvShowImage(channelFrame + " sRGB", sRGB);

            channelsFlag = false;

            cvReleaseImage(oRGB);
            cvReleaseImage(oR);
            cvReleaseImage(oG);
            cvReleaseImage(oB);
            cvReleaseImage(sR);
            cvReleaseImage(sG);
            cvReleaseImage(sB);
            cvReleaseImage(sRGB);

        }
        return null;
    }

}
