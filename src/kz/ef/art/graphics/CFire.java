package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

public class CFire extends JComponent {

    static final AImage IMAGE = new AImage(AImage.BOOM);
    static final int POSITION_X = 0;
    static final int POSITION_Y = 0;
    static final int SIZE_WIDTH = IMAGE.getImage().getWidth();
    static final int SIZE_HEIGHT = IMAGE.getImage().getHeight();
    static boolean drawBorders = false;

    public CFire() {
        setSize(SIZE_WIDTH, SIZE_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawComponent(g);
        if (drawBorders) drawBorders(g2d);
    }

    private void drawBorders(Graphics2D g2d) {
        g2d.drawRect(0, 0, SIZE_WIDTH - 1, SIZE_HEIGHT - 1);
    }

    private void drawComponent(Graphics g) {
        //        IMAGE.draw(0, 0, g);
        IMAGE.draw(SIZE_WIDTH / 2, SIZE_HEIGHT / 2, g);
    }

}
