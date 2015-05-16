package kz.ef.art;

import kz.ef.art.graphics.GraphicsMainFrame;
import kz.ef.art.vision.MomentsMain;
import kz.ef.art.vision.VisionColorChooserFrame;
import kz.ef.art.vision.test.VisionLogic;

import javax.swing.UIManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.cvFindContours;
import static org.bytedeco.javacpp.opencv_imgproc.cvContourArea;
import static org.bytedeco.javacpp.opencv_imgproc.cvMoments;

//import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_highgui.cvShowImage;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_highgui.cvReleaseCapture;

public class Runner implements ActionListener, ChangeListener {

    static JFrame frame;
    static JButton visionButton;
    static JButton graphicsButton;
    static JButton testButton;
    static JCheckBox cameraCheckBox;

    public static void main(String[] args) { new Runner(); }

    public Runner() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame();
        setFrameParams(frame);

        visionButton = createButton("Vision");
        graphicsButton = createButton("Graphics");
        cameraCheckBox = createJCheckBox("Camera");
        testButton = createButton("Test");

        frame.setVisible(true);
    }

    private void setFrameParams(JFrame frame) {
        frame.setTitle("Art");
        frame.setSize(240, 320);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JCheckBox createJCheckBox(String title) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.addChangeListener(this);
        frame.add(checkBox);
        return checkBox;
    }

    private JButton createButton(String title) {
        JButton button = new JButton();
        button.setText(title);
        button.setSize(240, 20);
        button.addActionListener(this);
        frame.add(button);
        return button;
    }

    static void runAsynchronouslyMethod(final MomentsMain momentsMain) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                momentsMain.run();
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (source == visionButton) {
            // ScannerRunner.run();
            MomentsMain momentsMain = new MomentsMain();
            runAsynchronouslyMethod(momentsMain);
            new VisionColorChooserFrame(momentsMain);
        }
        if (source == graphicsButton) {
            new GraphicsMainFrame();
        }
        if (source == testButton) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VisionLogic.main(null);
                }
            }).start();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JComponent component = (JComponent) e.getSource();
        if (component instanceof JCheckBox) {
            JCheckBox check = (JCheckBox) component;
            if (cameraCheckBox == check) {
                VisionLogic.useCamera = check.isSelected();
            }
        }
    }
}

