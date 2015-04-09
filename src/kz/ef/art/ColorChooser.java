package kz.ef.art;

import kz.ef.art.vision.MomentsMain;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvScalar;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.bytedeco.javacpp.opencv_core.cvScalar;

public class ColorChooser extends JFrame {

    public MomentsMain momentsMain;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        new ColorChooser();
    }

    public ColorChooser() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Slider");
        setSize(320, 240);
//        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JColorChooser colorChooserMin = new JColorChooser();
        colorChooserMin.setToolTipText("Min");
        ColorSelectionModel selectionModeMin = colorChooserMin.getSelectionModel();
        selectionModeMin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color colorMin = colorChooserMin.getColor();
                CvScalar Bminc = cvScalar(colorMin.getRed(), colorMin.getGreen(), colorMin.getBlue(), colorMin.getAlpha());
                momentsMain.Bminc = Bminc;
            }
        });
//        colorChooserMin.setSelectionModel(selectionModeMin);

        JColorChooser colorChooserMax = new JColorChooser();
        colorChooserMax.setToolTipText("Max");
        ColorSelectionModel selectionModelMax = colorChooserMax.getSelectionModel();
        selectionModelMax.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color colorMax = colorChooserMax.getColor();
                CvScalar Bmaxc = cvScalar(colorMax.getRed(), colorMax.getGreen(), colorMax.getBlue(), colorMax.getAlpha());
                momentsMain.Bmaxc = Bmaxc;
            }
        });
//        colorChooserMax.setSelectionModel(selectionModeMax);



        add(colorChooserMax);
        add(colorChooserMin);

    }

}
