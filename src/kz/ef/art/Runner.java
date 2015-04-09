package kz.ef.art;

import kz.ef.art.graphics.GraphicsFrame;
import kz.ef.art.vision.Scanner;
import kz.ef.art.vision.Vision;
import org.bytedeco.javacv.FrameRecorder;

import javax.swing.UIManager;

//import static org.bytedeco.javacpp.opencv_core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static org.bytedeco.javacpp.opencv_core.cvScalar;
import static org.bytedeco.javacpp.opencv_core.cvDrawContours;
import static org.bytedeco.javacpp.opencv_core.cvCircle;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_core.cvReleaseMemStorage;

//import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.cvFindContours;
import static org.bytedeco.javacpp.opencv_imgproc.cvContourArea;
import static org.bytedeco.javacpp.opencv_imgproc.cvMoments;

//import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_highgui.cvShowImage;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseCapture;

public class Runner {

    public static void main(String[] args) throws FrameRecorder.Exception, AWTException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame jFrameMain = new JFrame();
        jFrameMain.setTitle("Art");
        jFrameMain.setSize(240, 320);
        jFrameMain.setLocationRelativeTo(null);
        jFrameMain.setLayout(new FlowLayout());
        jFrameMain.setResizable(false);
        jFrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton jButtonVision = new JButton();
        jButtonVision.setText("Vision");
        jButtonVision.setSize(240, 20);
        jButtonVision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Vision.Example6_Moments();
                Scanner.run();
            }
        });

        JButton jButtonGraphics = new JButton();
        jButtonGraphics.setText("Graphics");
        jButtonGraphics.setSize(240, 20);
        jButtonGraphics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphicsFrame graphicsFrame = new GraphicsFrame();
            }
        });

        jFrameMain.add(jButtonVision);
        jFrameMain.add(jButtonGraphics);
        jFrameMain.setVisible(true);

//        Vision.Example1_Basics();
//        Vision.Example2_VideoProcessing();
//        Vision.Example3_VideoRecording();
//        Vision.Example4_ColorFiltering();
//        Vision.Example5_ContourFiltering();
//        Vision.Example6_Moments();
//        Vision.Example7_VirtualMouse();

//        GraphicsFrame app = new GraphicsFrame();

//        JButton jButton = new JButton();
//        jButton.setAction(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        jButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });

    }

}

