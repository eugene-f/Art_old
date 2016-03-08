package kz.ef.art.graphics;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PanelSettings extends JPanel implements ChangeListener, ItemListener, ActionListener {

    private static final double TANK_SIZE_DELTA = 0.05;

    private GraphicsMainFrame graphicsMainFrame;

    private JPanel rootPanel;
    private JCheckBox fullScreen;
    private JCheckBox showBorders;
    private JSlider timerStep;
    private JSlider timerHorizontalTotal;
    private JButton dir1;
    private JButton dir2;
    private JButton dir3;
    private JButton dir4;
    private JButton dir5;
    private JButton dir6;
    private JButton dir7;
    private JButton dir8;
    private JButton dir9;
    private JLabel labelHor;
    private JCheckBox reflect;
    private JButton buttonTankSizeDec;
    private JButton buttonTankSizeInc;
    private JCheckBox tankSizeCheckBox;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public static void createForm(GraphicsMainFrame graphicsMainFrame) {
        JFrame frame = new JFrame("PanelSettings");
        frame.setContentPane(new PanelSettings(graphicsMainFrame).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        frame.setTitle("Настройки");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    public PanelSettings(GraphicsMainFrame graphicsMainFrame) {
        this.graphicsMainFrame = graphicsMainFrame;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        showBorders.setSelected(BaseComponent.isDrawBorders());
        fullScreen.addItemListener(this);
        showBorders.addItemListener(this);
        timerStep.setMinimum(0);
        timerStep.setMaximum(1000);
        timerStep.setValue(CeTank.timerStep);
        timerStep.setInverted(true);
        timerStep.setPaintTicks(true);
        timerStep.setPaintLabels(true);
        timerStep.setMinorTickSpacing(50);
        timerStep.setMajorTickSpacing(250);
        timerStep.addChangeListener(this);
        timerStep.setInverted(true);

        timerHorizontalTotal.setMinimum(0);
        timerHorizontalTotal.setMaximum(15);
        timerHorizontalTotal.setValue(1);
        timerHorizontalTotal.setMinorTickSpacing(1);
        timerHorizontalTotal.setMajorTickSpacing(2);
        timerHorizontalTotal.setPaintTicks(true);
        timerHorizontalTotal.setPaintLabels(true);
        timerHorizontalTotal.addChangeListener(this);

        labelHor.setVisible(false);
        timerHorizontalTotal.setVisible(false);

        dir1.addActionListener(this);
        dir2.addActionListener(this);
        dir3.addActionListener(this);
        dir4.addActionListener(this);
        dir5.addActionListener(this);
        dir6.addActionListener(this);
        dir7.addActionListener(this);
        dir8.addActionListener(this);
        dir9.addActionListener(this);
        reflect.addItemListener(this);

        buttonTankSizeDec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphicsMainFrame.ceTank.changeSize(-TANK_SIZE_DELTA);
            }
        });
        buttonTankSizeInc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphicsMainFrame.ceTank.changeSize(TANK_SIZE_DELTA);
            }
        });
        tankSizeCheckBox.addItemListener(this);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox == fullScreen) {
                graphicsMainFrame.setImmersiveMode(checkBox.isSelected());
            }
            if (checkBox == showBorders) {
                BaseComponent.setDrawBorders(checkBox.isSelected());
                graphicsMainFrame.repaint();
            }
            if (checkBox == reflect) {
                graphicsMainFrame.ceTank.reflectDirection = checkBox.isSelected();
            }
            if (checkBox == tankSizeCheckBox) {
                graphicsMainFrame.ceTank.dynamicSize = checkBox.isSelected();
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider) {
            JSlider slider = (JSlider) e.getSource();
            if (!slider.getValueIsAdjusting()) {
                if (slider == timerStep) {
                    DriveDirection currentDirection = CeTank.currentDirection;
                    graphicsMainFrame.ceTank.stop();
                    CeTank.timerStep = slider.getValue();
                    graphicsMainFrame.ceTank.drive(currentDirection);
                }
                if (slider == timerHorizontalTotal) {
                    DriveDirection currentDirection = CeTank.currentDirection;
                    graphicsMainFrame.ceTank.stop();
                    CeTank.timerStep = (slider.getValue() * 60 * 1000) / 800;
                    graphicsMainFrame.ceTank.drive(currentDirection);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        graphicsMainFrame.jLabel.setVisible(false);
        if (source == dir1) graphicsMainFrame.ceTank.drive(1);
        if (source == dir2) graphicsMainFrame.ceTank.drive(2);
        if (source == dir3) graphicsMainFrame.ceTank.drive(3);
        if (source == dir4) graphicsMainFrame.ceTank.drive(4);
        if (source == dir5) graphicsMainFrame.ceTank.drive(5);
        if (source == dir6) graphicsMainFrame.ceTank.drive(6);
        if (source == dir7) graphicsMainFrame.ceTank.drive(7);
        if (source == dir8) graphicsMainFrame.ceTank.drive(8);
        if (source == dir9) graphicsMainFrame.ceTank.drive(9);
    }

}
