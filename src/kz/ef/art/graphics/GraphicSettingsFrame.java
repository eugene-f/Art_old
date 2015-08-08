package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

class GraphicSettingsFrame extends JFrame {

    public GraphicSettingsFrame(GraphicsMainFrame graphicsMainFrame) {
        this.setTitle("Настройки");
        this.setSize(240, 320);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PanelSettings panel = new PanelSettings(graphicsMainFrame);

        this.getContentPane().add(panel);
        this.pack();
        this.repaint();

        this.setVisible(true);
    }

}
