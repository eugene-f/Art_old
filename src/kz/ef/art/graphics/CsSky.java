package kz.ef.art.graphics;

import java.awt.*;

class CsSky extends ComponentScope {

    public CsSky(Container container) {
        super(container, new AImage(Texture.SKY));
        setColorBackground(Color.CYAN.brighter());
        setColorBorder(Color.CYAN.darker());
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
        int width = getContainer().getWidth();
        int height = (int) (getContainer().getHeight() * 0.25);
        setSize(width, height);
        repaint();
    }

}
