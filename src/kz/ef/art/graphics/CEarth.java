package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

public class CEarth extends JComponent {

    static final int D_X = 0;
    static final int D_Y = 150;
    static final int D_WIDTH = GraphicsFrame.WIDTH;
    static final int D_HEIGHT = (int) (GraphicsFrame.HEIGHT * 0.75); // 450

    private static final Color BACKGROUND_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = Color.GREEN.brighter();

    public CEarth() {
        setSize(D_WIDTH, D_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, D_WIDTH, D_HEIGHT);

        g2d.setPaint(BORDER_COLOR);
        g2d.drawRect(0, 0, D_WIDTH - 1, D_HEIGHT - 1);
    }
}
