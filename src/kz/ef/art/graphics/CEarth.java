package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

public class CEarth extends JComponent {

    static final int POSITION_X = 0;
    static final int POSITION_Y = (int) (GraphicsMainFrame.HEIGHT * 0.25); // 150
    static final int SIZE_WIDTH = GraphicsMainFrame.WIDTH;
    static final int SIZE_HEIGHT = (int) (GraphicsMainFrame.HEIGHT * 0.75); // 450
    private static final Color COLOR_BACKGROUND = Color.GREEN.darker();
    private static final Color COLOR_BORDER = Color.GREEN.brighter();
    static boolean drawBorders = false;

    public CEarth() {
        setSize(SIZE_WIDTH, SIZE_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawComponent(g2d);
        if (drawBorders) drawBorders(g2d);
    }

    private void drawBorders(Graphics2D g2d) {
        g2d.setPaint(COLOR_BORDER);
        g2d.drawRect(0, 0, SIZE_WIDTH - 1, SIZE_HEIGHT - 1);
    }

    private void drawComponent(Graphics2D g2d) {
        g2d.setPaint(COLOR_BACKGROUND);
        g2d.fillRect(0, 0, SIZE_WIDTH, SIZE_HEIGHT);
    }

}
