package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

public class CSky extends JComponent {

    int width = GraphicsFrame.WIDTH;
//    int height = GraphicsFrame.HEIGHT / 2;
    int height = 150;

    public CSky() {
        setSize(width, height);
    }

    Color backgroundColor = Color.CYAN.brighter();
    Color borderColor = Color.CYAN.darker();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setPaint(backgroundColor);
//        g2d.fillRect(0, 0, width, height / 2);
//        g2d.setPaint(borderColor);
//        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setPaint(backgroundColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setPaint(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
    }
}
