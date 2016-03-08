package kz.ef.art;

import kz.ef.art.graphics.GraphicsMainFrame;
import kz.ef.art.j3d.SimpleModelView;
import kz.ef.art.vision.MomentsMain;
import kz.ef.art.vision.sandbox.VisionMainLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ResourceBundle;

public class MainFrame extends JPanel implements ActionListener, ItemListener {

    private JPanel rootPanel;
    private JButton visionButton;
    private JButton graphicsButton;
    private JButton sandboxButton;
    private JCheckBox cameraCheckBox;
    private JButton runButton;
    private JButton a3DButton;

//    static {
//        System.loadLibrary("jniopencv_core.dll");
//        System.loadLibrary("opencv_core2410");
//    }

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(new MainFrame().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setTitle(ResourceBundle.getBundle("strings").getString("appName"));
        frame.setSize(240, 320);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainFrame() throws HeadlessException {
        a3DButton.addActionListener(this);
        runButton.addActionListener(this);
        visionButton.addActionListener(this);
        graphicsButton.addActionListener(this);
        sandboxButton.addActionListener(this);
        cameraCheckBox.addItemListener(this);
        cameraCheckBox.setSelected(VisionMainLogic.useCamera);
    }

    private static void runAsynchronouslyMethod(final MomentsMain momentsMain) {
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
        if (source == a3DButton) {
            try {
                new SimpleModelView();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (source == runButton) {
            new GraphicsMainFrame();
            MomentsMain.withGraphics = true;
            MomentsMain momentsMain = new MomentsMain();
            runAsynchronouslyMethod(momentsMain);
//            new VisionColorChooserFrame(momentsMain);
        }
        if (source == visionButton) {
            MomentsMain.withGraphics = false;
            MomentsMain momentsMain = new MomentsMain();
            runAsynchronouslyMethod(momentsMain);
//            new VisionColorChooserFrame(momentsMain);
        }
        if (source == graphicsButton) {
            new GraphicsMainFrame();
        }
        if (source == sandboxButton) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VisionMainLogic.main(null);
                }
            }).start();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() instanceof JCheckBox) {
            JCheckBox check = (JCheckBox) e.getSource();
            if (cameraCheckBox == check) VisionMainLogic.useCamera = check.isSelected();
        }
    }

}
