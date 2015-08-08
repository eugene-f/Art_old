package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

class CEarth extends ComponentScope {

    public CEarth(JFrame frame) {
        super(frame);
        COLOR_BACKGROUND = Color.GREEN.darker();
        COLOR_BORDER = Color.GREEN.brighter();
        updatePosition();
        updateSize();
    }

    @Override
    void updatePosition() {
        int x = 0;
        int y = (int) (getContentPane().getHeight() * 0.25);
        setLocation(x, y);
        repaint();
    }

    @Override
    void updateSize() {
        int width = getContentPane().getWidth();
        int height = (int) (getContentPane().getHeight() * 0.75);
        setSize(width, height);
        repaint();
    }

}
