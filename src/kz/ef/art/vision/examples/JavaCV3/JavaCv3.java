package kz.ef.art.vision.examples.JavaCV3;

import static org.bytedeco.javacpp.opencv_highgui.*;

import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.FrameRecorder.Exception;
import org.bytedeco.javacv.OpenCVFrameRecorder;
import org.bytedeco.javacpp.opencv_core.IplImage;
public class JavaCv3 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
		cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_WIDTH,640);
		cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_HEIGHT,480);
		
		cvNamedWindow("LiveVid",CV_WINDOW_AUTOSIZE);
		
		FrameRecorder recorder1 = new OpenCVFrameRecorder("RecordVid.avi",640,480);
//		recorder1.setVideoCodec(CV_FOURCC('M','J','P','G')); // FixMe: Disable comment on this line
		recorder1.setFrameRate(15);
		recorder1.setPixelFormat(1);
		recorder1.start();
		
		IplImage img1;
		
		for(;;){
			
			img1 = cvQueryFrame(capture1);
			
			if(img1==null) break;
			
			cvShowImage("LiveVid",img1);
			recorder1.record(img1);
			
			char c = (char) cvWaitKey(15);
			if(c == 'q') break;
			
		}
		
		recorder1.stop();
		cvDestroyWindow("LiveVid");
		cvReleaseCapture(capture1);
		
		
	}

}
