package kz.ef.art.vision.test;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static kz.ef.art.vision.test.VisionEffectsParams.*;

class VisionSettingsFrame extends JFrame implements ChangeListener, ItemListener, ActionListener {

    private JCheckBox hsvCheckBox;
    private JCheckBox binCheckBox;

    private JCheckBox dilateCheckBox;
    private JSlider dilateRadiusSlider;
    private JSlider dilateIterationsSlider;

    private JCheckBox erodeCheckBox;
    private JSlider erodeRadiusSlider;
    private JSlider erodeIterationsSlider;

    private JSlider colorRedMinSlider;
    private JSlider colorRedMaxSlider;
    private JSlider colorGreenMinSlider;
    private JSlider colorGreenMaxSlider;
    private JSlider colorBlueMinSlider;
    private JSlider colorBlueMaxSlider;

    private JButton updateButton;

    private JCheckBox autoUpdateChannelsCheckBox;

    public VisionSettingsFrame() {
        setTitle("Параметры зрения");
//        setSize(240, 320);
        setSize(240, 480);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
//        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        hsvCheckBox = createJCheckBox("HSV");
        binCheckBox = createJCheckBox("Bin");

        dilateCheckBox = createJCheckBox("расширение");
        dilateRadiusSlider = createJSlider("Радиус расширения", dilateRadiusMax, 1, 5);
        dilateIterationsSlider = createJSlider("Количество итераций расширения", dilateIterationsMax, 1, 5);

        erodeCheckBox = createJCheckBox("Сужение");
        erodeRadiusSlider = createJSlider("Радиус сужения", erodeRadiusMax, 1, 5);
        erodeIterationsSlider = createJSlider("Количество итераций сужения", erodeIterationsMax, 1, 5);

        colorRedMinSlider = createJSlider("Красный канал - минимум", channelRgbMax, 32, 255);
        colorRedMaxSlider = createJSlider("Красный канал - максимум", channelRgbMax, 32, 255);
        colorGreenMinSlider = createJSlider("Зеленый канал - минимум", channelRgbMax, 32, 255);
        colorGreenMaxSlider = createJSlider("Зеленый канал - максимум", channelRgbMax, 32, 255);
        colorBlueMinSlider = createJSlider("Синий канал - минимум", channelRgbMax, 32, 255);
        colorBlueMaxSlider = createJSlider("Синий канал - максимум", channelRgbMax, 32, 255);

        updateButton = createButton("Обновить каналы");

        autoUpdateChannelsCheckBox = createJCheckBox("Автообновление каналов");

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (source == updateButton) {
            showChannelsFlag = true;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                if (slider == dilateRadiusSlider) dilateRadius = slider.getValue();
                if (slider == dilateIterationsSlider) dilateIterations = slider.getValue();
                if (slider == erodeRadiusSlider) erodeRadius = slider.getValue();
                if (slider == erodeIterationsSlider) erodeIterations = slider.getValue();
                if (slider == colorRedMinSlider) rMin = slider.getValue();
                if (slider == colorRedMaxSlider) rMax = slider.getValue();
                if (slider == colorGreenMinSlider) gMin = slider.getValue();
                if (slider == colorGreenMaxSlider) gMax = slider.getValue();
                if (slider == colorBlueMinSlider) bMin = slider.getValue();
                if (slider == colorBlueMaxSlider) bMax = slider.getValue();
            }
        }
    }

    private JCheckBox createJCheckBox(String title) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.addItemListener(this);
        this.add(checkBox);
        return checkBox;
    }

    private JSlider createJSlider(String title, int max, int minor, int major) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, max, 1);
        slider.setMinorTickSpacing(minor);
        slider.setMajorTickSpacing(major);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        this.add(new JLabel(title));
        this.add(slider);
        return slider;
    }

    private JButton createButton(String title) {
        JButton button = new JButton();
        button.setText(title);
        button.setSize(240, 20);
        button.addActionListener(this);
        this.add(button);
        return button;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox == hsvCheckBox) hsvFlag = checkBox.isSelected();
            if (checkBox == binCheckBox) binFlag = checkBox.isSelected();
            if (checkBox == erodeCheckBox) erodeFlag = checkBox.isSelected();
            if (checkBox == dilateCheckBox) dilateFlag = checkBox.isSelected();
            if (checkBox == autoUpdateChannelsCheckBox) {
                autoUpdateChannelsFlag = checkBox.isSelected();
                showChannelsFlag = autoUpdateChannelsFlag;
            }
        }
    }

}
