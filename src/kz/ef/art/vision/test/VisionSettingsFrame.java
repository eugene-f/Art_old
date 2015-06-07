package kz.ef.art.vision.test;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static kz.ef.art.vision.test.VisionEffectsSettings.*;

public class VisionSettingsFrame implements ChangeListener, ActionListener {

    JFrame frame;

    JCheckBox hsvCheckBox;
    JCheckBox binCheckBox;

    JCheckBox dilateCheckBox;
    JSlider dilateRadiusSlider;
    JSlider dilateIterationsSlider;

    JCheckBox erodeCheckBox;
    JSlider erodeRadiusSlider;
    JSlider erodeIterationsSlider;

    JSlider colorRedMinSlider;
    JSlider colorRedMaxSlider;
    JSlider colorGreenMinSlider;
    JSlider colorGreenMaxSlider;
    JSlider colorBlueMinSlider;
    JSlider colorBlueMaxSlider;

    JButton updateButton;

    public VisionSettingsFrame() {
        frame = createFrame("VisionSettingsFrame");

        hsvCheckBox = createJCheckBox("HSV");
        binCheckBox = createJCheckBox("Bin");

        dilateCheckBox = createJCheckBox("Dilate");
        dilateRadiusSlider = createJSlider("Dilate Radius", dilateRadiusMax, 1, 5);
        dilateIterationsSlider = createJSlider("Dilate Iterations", dilateIterationsMax, 1, 5);

        erodeCheckBox = createJCheckBox("Erode");
        erodeRadiusSlider = createJSlider("Dilate Radius", erodeRadiusMax, 1, 5);
        erodeIterationsSlider = createJSlider("Dilate Iterations", erodeIterationsMax, 1, 5);

        colorRedMinSlider = createJSlider("Channel Red Minimal", channelRgbMax, 32, 255);
        colorRedMaxSlider = createJSlider("Channel Red Maximal", channelRgbMax, 32, 255);
        colorGreenMinSlider = createJSlider("Channel Green Minimal", channelRgbMax, 32, 255);
        colorGreenMaxSlider = createJSlider("Channel Green Maximal", channelRgbMax, 32, 255);
        colorBlueMinSlider = createJSlider("Channel Blue Minimal", channelRgbMax, 32, 255);
        colorBlueMaxSlider = createJSlider("Channel Blue Maximal", channelRgbMax, 32, 255);

        updateButton = createButton("Update Channels");

        frame.setVisible(true);
    }

    private JFrame createFrame(String title) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
//        frame.setSize(240, 320);
        frame.setSize(240, 480);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
//        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JCheckBox createJCheckBox(String title) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.addChangeListener(this);
        frame.add(checkBox);
        return checkBox;
    }

    private JSlider createJSlider(String title, int max, int minor, int major) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, max, 1);
        slider.setMinorTickSpacing(minor);
        slider.setMajorTickSpacing(major);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        frame.add(new JLabel(title));
        frame.add(slider);
        return slider;
    }

    private JButton createButton(String title) {
        JButton button = new JButton();
        button.setText(title);
        button.setSize(240, 20);
        button.addActionListener(this);
        frame.add(button);
        return button;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        JComponent component = (JComponent) e.getSource();

        if (component instanceof JSlider) {
            JSlider slider = (JSlider) component;
            if (!slider.getValueIsAdjusting()) {
                if (slider == dilateRadiusSlider) {
                    dilateRadius = slider.getValue();
                }
                if (slider == dilateIterationsSlider) {
                    dilateIterations = slider.getValue();
                }
                if (slider == erodeRadiusSlider) {
                    erodeRadius = slider.getValue();
                }
                if (slider == erodeIterationsSlider) {
                    erodeIterations = slider.getValue();
                }
                if (slider == colorRedMinSlider) {
                    rMin = slider.getValue();
                }
                if (slider == colorRedMaxSlider) {
                    rMax = slider.getValue();
                }
                if (slider == colorGreenMinSlider) {
                    gMin = slider.getValue();
                }
                if (slider == colorGreenMaxSlider) {
                    gMax = slider.getValue();
                }
                if (slider == colorBlueMinSlider) {
                    bMin = slider.getValue();
                }
                if (slider == colorBlueMaxSlider) {
                    bMax = slider.getValue();
                }
            }
        }

        if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox == hsvCheckBox) {
                hsvFlag = checkBox.isSelected();
            }
            if (checkBox == binCheckBox) {
                binFlag = checkBox.isSelected();
            }
            if (checkBox == erodeCheckBox) {
                erodeFlag = checkBox.isSelected();
            }
            if (checkBox == dilateCheckBox) {
                dilateFlag = checkBox.isSelected();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (source == updateButton) {
            channelsFlag = true;
        }
    }
}
