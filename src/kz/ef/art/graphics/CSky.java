package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

public class CSky extends JComponent {

    static final int D_X = 0;
    static final int D_Y = 0;
    static final int D_WIDTH = GraphicsMainFrame.WIDTH;
    static final int D_HEIGHT = (int) (GraphicsMainFrame.HEIGHT * 0.25); // 150
    private static final Color BACKGROUND_COLOR = Color.CYAN.brighter();
    private static final Color BORDER_COLOR = Color.CYAN.darker();
    static boolean drawBorders = false;

    public CSky() {
        setSize(D_WIDTH, D_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawComponent(g2d);
        if (drawBorders) drawBorders(g2d);
    }

    private void drawBorders(Graphics2D g2d) {
        g2d.setPaint(BORDER_COLOR);
        g2d.drawRect(0, 0, D_WIDTH - 1, D_HEIGHT - 1);
    }

    private void drawComponent(Graphics2D g2d) {
        g2d.setPaint(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, D_WIDTH, D_HEIGHT);
    }

}
