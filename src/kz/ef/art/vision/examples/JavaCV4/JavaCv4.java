package kz.ef.art.vision.examples.JavaCV4;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
public class JavaCv4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		IplImage img1, imghsv, imgbin;
		
		imghsv = cvCreateImage(cvSize(640,480),8,3);
		imgbin = cvCreateImage(cvSize(640,480),8,1);
		
		CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
		
		int i=1;
		
		while(i==1)
		{
				
			img1 = cvQueryFrame(capture1);
			
			if(img1 == null) break;
					
			cvCvtColor(img1,imghsv,CV_BGR2HSV);
			CvScalar minc = cvScalar(95,150,75,0), maxc = cvScalar(145,255,255,0);
			cvInRangeS(imghsv,minc,maxc,imgbin);
		
			cvShowImage("color",img1);
			cvShowImage("Binary",imgbin);
			char c = (char)cvWaitKey(15);
			if(c == 'q') break; 
		
		}
		
		cvReleaseImage(imghsv);
		cvReleaseImage(imgbin);
		cvReleaseCapture(capture1);
	}

}
