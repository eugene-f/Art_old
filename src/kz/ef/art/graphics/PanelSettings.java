package kz.ef.art.graphics;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class PanelSettings extends JPanel implements ChangeListener, ItemListener {

    static Dimension lastFormSize = GraphicsMainFrame.DEFAULT_SIZE;
    private static Point lastFormLocation = null;

    private GraphicsMainFrame graphicsMainFrame;

    private JCheckBox fullScreen;
    private JCheckBox showBorders;
    private JSlider timerStep;
    private JSlider timerHorizontalTotal;

    public PanelSettings(GraphicsMainFrame graphicsMainFrame) {
        this.graphicsMainFrame = graphicsMainFrame;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        fullScreen = createJCheckBox("На весь экран");
        showBorders = createJCheckBox("Отображать границы");
        timerStep = createJSlider("Шаг таймера (1 px / T milliseconds)", 1000);
        this.add(timerStep);
        timerHorizontalTotal = createJSlider("Время по горизонтали (FRAME width / T minute)", 15);
        timerHorizontalTotal.setMajorTickSpacing(2);
        timerHorizontalTotal.setMinorTickSpacing(1);
        this.add(timerHorizontalTotal);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox == fullScreen) {
                if (checkBox.isSelected()) {
                    graphicsMainFrame.updateLastSize();
                    lastFormLocation = graphicsMainFrame.getLocation();

                    graphicsMainFrame.dispose();
                    graphicsMainFrame.setUndecorated(true);
                    graphicsMainFrame.pack();
                    graphicsMainFrame.setVisible(true);

                    graphicsMainFrame.setSize(GraphicsMainFrame.SCREEN_SIZE);
                    graphicsMainFrame.setLocation(0, 0);
                } else {
                    graphicsMainFrame.dispose();
                    graphicsMainFrame.setUndecorated(false);
                    graphicsMainFrame.pack();
                    graphicsMainFrame.setVisible(true);

                    graphicsMainFrame.setSize(lastFormSize);
                    if (lastFormLocation != null) {
                        graphicsMainFrame.setLocation(lastFormLocation);
                    } else {
                        graphicsMainFrame.setLocationRelativeTo(null);
                    }
                }
                graphicsMainFrame.updateComponents();
            }
            if (checkBox == showBorders) {
                graphicsMainFrame.cEarth.drawBorders = checkBox.isSelected();
                graphicsMainFrame.cFire.drawBorders = checkBox.isSelected();
                graphicsMainFrame.cSky.drawBorders = checkBox.isSelected();
                graphicsMainFrame.cTank.drawBorders = checkBox.isSelected();
                graphicsMainFrame.repaint();
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                if (slider == timerStep) {
                    final int currentDirection = CTank.currentDirection;
                    graphicsMainFrame.cTank.stop();
                    CTank.timerStep = slider.getValue();
                    graphicsMainFrame.cTank.drive(currentDirection);
                }
                if (slider == timerHorizontalTotal) {
                    final int currentDirection = CTank.currentDirection;
                    graphicsMainFrame.cTank.stop();
                    CTank.timerStep = (slider.getValue() * 60 * 1000) / 800;
                    graphicsMainFrame.cTank.drive(currentDirection);
                }
            }
        }
    }

    private JCheckBox createJCheckBox(String title) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.addItemListener(this);
        this.add(checkBox);
        return checkBox;
    }

    private JSlider createJSlider(String title, int max) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, max, 1);
//        slider.setMajorTickSpacing(10);
//        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        this.add(new JLabel(title));
        return slider;
    }

}
