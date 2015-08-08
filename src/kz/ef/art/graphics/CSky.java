package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

class CSky extends ComponentScope {

    public CSky(JFrame frame) {
        super(frame);
        COLOR_BACKGROUND = Color.CYAN.brighter();
        COLOR_BORDER = Color./*CYAN*/RED.darker();
        updatePosition();
        updateSize();
    }

    @Override
    void updatePosition() {
        int x = 0;
        int y = 0;
        setLocation(x, y);
        repaint();
    }

    @Override
    void updateSize() {
        int width = getContentPane().getWidth();
        int height = (int) (getContentPane().getHeight() * 0.25);
        setSize(width, height);
        repaint();
    }

}
