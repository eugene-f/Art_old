package kz.ef.art;

import org.bytedeco.javacpp.opencv_core.CvScalar;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static org.bytedeco.javacpp.opencv_core.cvScalar;

public class ColorC extends JFrame {

    public ColorC(JavaCV javaCV) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("ColorChooser");
        setSize(320, 240);
//        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JColorChooser colorChooserMin = new JColorChooser();
        colorChooserMin.setToolTipText("Min");
        colorChooserMin.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color c = colorChooserMin.getColor();
                CvScalar scalar = cvScalar(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                javaCV.binaryMinColor = scalar;
            }
        });
//        colorChooserMin.setSelectionModel(selectionModeMin);

        JColorChooser colorChooserMax = new JColorChooser();
        colorChooserMax.setToolTipText("Max");
        colorChooserMax.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color c = colorChooserMax.getColor();
                CvScalar scalar = cvScalar(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
                javaCV.binaryMaxColor = scalar;
            }
        });
//        colorChooserMax.setSelectionModel(selectionModeMax);

        add(new JLabel("Min"));
        add(colorChooserMin);
        add(new JLabel("Max"));
        add(colorChooserMax);

    }

}
