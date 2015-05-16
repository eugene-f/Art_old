package kz.ef.art.graphics;

import kz.ef.art.vision.test.VisionLogic;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GraphicSettingsFrame implements ChangeListener {

    GraphicsMainFrame graphicsMainFrame;
    CTank cTank;
    JFrame frame;
    JCheckBox fullScreen;
    JCheckBox borders;
    JCheckBox dilate;
    JSlider timerStep;
    JSlider timerHorTime;
    JLabel label;

    public GraphicSettingsFrame(GraphicsMainFrame graphicsMainFrame) {
        this.graphicsMainFrame = graphicsMainFrame;
        this.cTank = (CTank) graphicsMainFrame.cTank;

        frame = createFrame("Options");

        label = new JLabel("");
        frame.add(label);

        fullScreen = createJCheckBox("Full screen");
        borders = createJCheckBox("Draw borders");
        dilate = createJCheckBox("Dilate");
        timerStep = createJSlider("Timer Step", 1000);
        frame.add(timerStep);
        timerHorTime = createJSlider("Hor Time", 15);
        timerHorTime.setMajorTickSpacing(2);
        timerHorTime.setMinorTickSpacing(1);
        frame.add(timerHorTime);

        frame.setVisible(true);
    }

    private JFrame createFrame(String title) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(240, 320);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private JCheckBox createJCheckBox(String title) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.addChangeListener(this);
        frame.add(checkBox);
        return checkBox;
    }

    private JSlider createJSlider(String title, int max) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, max, 1);
//        slider.setMajorTickSpacing(10);
//        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        frame.add(new JLabel(title));
//        frame.add(slider);
        return slider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JComponent component = (JComponent) e.getSource();
        if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox == fullScreen) {
                if (!checkBox.isSelected()) {
                    graphicsMainFrame.setSize(GraphicsMainFrame.WIDTH, GraphicsMainFrame.HEIGHT);
                } else {
                    graphicsMainFrame.setSize(GraphicsMainFrame.FULL_WIDTH, GraphicsMainFrame.FULL_HEIGHT);
                }
                graphicsMainFrame.repaint();
                // fixme: frame position
                // fixme: tank drive area
                // fixme: tank position on resize frame
            }
            if (checkBox == borders) {
                CEarth.drawBorders = checkBox.isSelected();
                CFire.drawBorders = checkBox.isSelected();
                CSky.drawBorders = checkBox.isSelected();
                CTank.drawBorders = checkBox.isSelected();
                graphicsMainFrame.repaint();
            }
            if (checkBox == dilate) {
                VisionLogic.dilateFlag = checkBox.isSelected();
            }
        }
        if (component instanceof JSlider) {
            JSlider slider = (JSlider) component;
            if (!slider.getValueIsAdjusting()) {
                if (slider == timerStep) {
                    final int currentDirection = cTank.currentDirection;
                    cTank.stop();
                    CTank.timerStep = slider.getValue();
                    cTank.drive(currentDirection);
                    label.setText(String.valueOf(slider.getValue()));
                }
                if (slider == timerHorTime) {
                    final int currentDirection = cTank.currentDirection;
                    cTank.stop();
                    CTank.timerStep = (slider.getValue() * 60 * 1000) / 800;
                    cTank.drive(currentDirection);
                }
            }
        }
    }
}
