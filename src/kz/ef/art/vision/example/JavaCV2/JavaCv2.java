package kz.ef.art.vision.example.JavaCV2;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;



import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
public class JavaCv2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CvCapture capture = cvCreateFileCapture("Vid.mp4"); //cvCreateCameraCapture(CV_CAP_ANY);  //
		IplImage frame;
		IplImage grayimg = cvCreateImage(cvSize(640,480),IPL_DEPTH_8U,1);
		
		cvNamedWindow("Video",CV_WINDOW_AUTOSIZE);
		
		for(;;)
		{
			frame = cvQueryFrame(capture);
			if(frame == null) 
				{
					System.out.println("ERROR: NO Video File");	
					break;
				}
			
			cvCvtColor(frame,grayimg,CV_BGR2GRAY);
			cvShowImage("Video",grayimg);
			char c = (char) cvWaitKey(30);
			
			if(c==27) break;
		}

		cvReleaseCapture(capture);
		cvDestroyWindow("Video");
	}

}
