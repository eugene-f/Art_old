package kz.ef.art.vision;

import kz.ef.art.vision.test.VisionLogic;
import org.bytedeco.javacpp.opencv_core.CvScalar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static org.bytedeco.javacpp.opencv_core.cvScalar;

public class VisionColorChooserFrame extends JFrame {

    private final VisionLogic visionLogic;
    private final MomentsMain momentsMain;

    public VisionColorChooserFrame(VisionLogic visionLogic) {
        this.visionLogic = visionLogic;
        this.momentsMain = null;
        setFrameParams();
    }

    public VisionColorChooserFrame(MomentsMain momentsMain) {
        this.momentsMain = momentsMain;
        this.visionLogic = null;
        setFrameParams();
    }

    private void setFrameParams() {
        setTitle("ColorChooser");
        setSize(320, 240);
//        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JColorChooser colorChooserMin = new JColorChooser();
        colorChooserMin.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color c = colorChooserMin.getColor();
                CvScalar scalar = cvScalar(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                if (momentsMain != null) momentsMain.Bminc = scalar;
                if (visionLogic != null) visionLogic.binFilterColorMin = scalar;
            }
        });
        JColorChooser colorChooserMax = new JColorChooser();
        colorChooserMax.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color c = colorChooserMax.getColor();
                CvScalar scalar = cvScalar(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                if (momentsMain != null) momentsMain.Bmaxc = scalar;
                if (visionLogic != null) visionLogic.binFilterColorMax = scalar;
            }
        });

        add(new JLabel("Minimal Color Value"));
        add(colorChooserMin);
        add(new JLabel("Maximal Color Value"));
        add(colorChooserMax);
        setVisible(true);
    }

}
