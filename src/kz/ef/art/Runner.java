package kz.ef.art;

import kz.ef.art.graphics.GraphicsMainFrame;
import kz.ef.art.vision.MomentsMain;
import kz.ef.art.vision.VisionColorChooserFrame;
import kz.ef.art.vision.test.VisionMainLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;

public class Runner extends JFrame implements ActionListener, ItemListener {

    private static JButton visionButton;
    private static JButton graphicsButton;
    private static JButton sandboxButton;
    private static JCheckBox cameraCheckBox;

    public static void main(String[] args) {
        new Runner();
    }

    public Runner() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(ResourceBundle.getBundle("strings").getString("appName"));
        setSize(240, 320);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        visionButton = createButton("Модуль зрения");
        graphicsButton = createButton("Модуль графики");
        cameraCheckBox = createJCheckBox("Использовать веб-камеру в песочнице");
        sandboxButton = createButton("Песочница");

        cameraCheckBox.setSelected(VisionMainLogic.useCamera);

        setVisible(true);
    }

    private JCheckBox createJCheckBox(String title) {
        JCheckBox checkBox = new JCheckBox(title);
        checkBox.addItemListener(this);
        this.add(checkBox);
        return checkBox;
    }

    private JButton createButton(String title) {
        JButton button = new JButton();
        button.setText(title);
        button.setSize(240, 20);
        button.addActionListener(this);
        this.add(button);
        return button;
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
        if (source == visionButton) {
//            ScannerRunner.run();
            MomentsMain momentsMain = new MomentsMain();
            runAsynchronouslyMethod(momentsMain);
            new VisionColorChooserFrame(momentsMain);
        }
        if (source == graphicsButton) new GraphicsMainFrame();
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

