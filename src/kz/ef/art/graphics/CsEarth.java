package kz.ef.art.graphics;

import java.awt.*;

class CsEarth extends ComponentScope {

    public CsEarth(Container container) {
        super(container, new AImage(Texture.EARTH));
        setColorBackground(Color.GREEN.darker());
        setColorBorder(Color.GREEN.brighter());
        updatePosition();
        updateSize();
    }

    @Override
    void updatePosition() {
        int x = 0;
        int y = (int) (getContainer().getHeight() * 0.25);
        setLocation(x, y);
        repaint();
    }

    @Override
    void updateSize() {
        int width = getContainer().getWidth();
        int height = (int) (getContainer().getHeight() * 0.75);
        setSize(width, height);
        repaint();
    }

}
