package kz.ef.art.vision.test;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        dilateRadiusSlider = createJSlider("Dilate Radius", VisionLogic.dilate_radius_slider_max, 1, 5);
        dilateIterationsSlider = createJSlider("Dilate Iterations", VisionLogic.dilate_iterations_slider_max, 1, 5);

        erodeCheckBox = createJCheckBox("Erode");
        erodeRadiusSlider = createJSlider("Dilate Radius", VisionLogic.erode_radius_slider_max, 1, 5);
        erodeIterationsSlider = createJSlider("Dilate Iterations", VisionLogic.erode_iterations_slider_max, 1, 5);

        colorRedMinSlider = createJSlider("Channel Red Minimal", VisionLogic.channel_rgb_slider_max, 32, 255);
        colorRedMaxSlider = createJSlider("Channel Red Maximal", VisionLogic.channel_rgb_slider_max, 32, 255);
        colorGreenMinSlider = createJSlider("Channel Green Minimal", VisionLogic.channel_rgb_slider_max, 32, 255);
        colorGreenMaxSlider = createJSlider("Channel Green Maximal", VisionLogic.channel_rgb_slider_max, 32, 255);
        colorBlueMinSlider = createJSlider("Channel Blue Minimal", VisionLogic.channel_rgb_slider_max, 32, 255);
        colorBlueMaxSlider = createJSlider("Channel Blue Maximal", VisionLogic.channel_rgb_slider_max, 32, 255);

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
                    VisionLogic.dilate_radius = slider.getValue();
                }
                if (slider == dilateIterationsSlider) {
                    VisionLogic.dilate_iterations = slider.getValue();
                }
                if (slider == erodeRadiusSlider) {
                    VisionLogic.erode_radius = slider.getValue();
                }
                if (slider == erodeIterationsSlider) {
                    VisionLogic.erode_iterations = slider.getValue();
                }
                if (slider == colorRedMinSlider) {
                    VisionLogic.r_min = slider.getValue();
                }
                if (slider == colorRedMaxSlider) {
                    VisionLogic.r_max = slider.getValue();
                }
                if (slider == colorGreenMinSlider) {
                    VisionLogic.g_min = slider.getValue();
                }
                if (slider == colorGreenMaxSlider) {
                    VisionLogic.g_max = slider.getValue();
                }
                if (slider == colorBlueMinSlider) {
                    VisionLogic.b_min = slider.getValue();
                }
                if (slider == colorBlueMaxSlider) {
                    VisionLogic.b_max = slider.getValue();
                }
            }
        }

        if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox == hsvCheckBox) {
                VisionLogic.hsvFlag = checkBox.isSelected();
            }
            if (checkBox == binCheckBox) {
                VisionLogic.binFlag = checkBox.isSelected();
            }
            if (checkBox == erodeCheckBox) {
                VisionLogic.erodeFlag = checkBox.isSelected();
            }
            if (checkBox == dilateCheckBox) {
                VisionLogic.dilateFlag = checkBox.isSelected();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (source == updateButton) {
            VisionLogic.channelFlag = true;
        }
    }
}
