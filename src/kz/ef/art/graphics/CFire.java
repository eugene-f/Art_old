package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

public class CFire extends JComponent {

    static final AImage IMAGE = new AImage(AImage.BOOM);
    static final int D_X = 0;
    static final int D_Y = 0;
    static final int D_WIDTH = IMAGE.getImage().getWidth();
    static final int D_HEIGHT = IMAGE.getImage().getHeight();
    static boolean drawBorders = false;

    public CFire() {
        setSize(D_WIDTH, D_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawComponent(g);
        if (drawBorders) drawBorders(g2d);
    }

    private void drawBorders(Graphics2D g2d) {
        g2d.drawRect(0, 0, D_WIDTH - 1, D_HEIGHT - 1);
    }

    private void drawComponent(Graphics g) {
        //        IMAGE.draw(0, 0, g);
        IMAGE.draw(D_WIDTH / 2, D_HEIGHT / 2, g);
    }

}
