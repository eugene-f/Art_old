package kz.ef.art.graphics;


import java.awt.*;

abstract class ComponentScope extends BaseComponent {

    private final Container container;
    final AImage image;

    ComponentScope(Container container, AImage image) {
        this.container = container;
        this.image = image;
    }

    Container getContainer() {
        return container;
    }

    @Override
    void drawComponent(Graphics2D g2d) {
//        g2d.setPaint(COLOR_BACKGROUND);
//        g2d.fillRect(0, 0, getWidth(), getHeight());
        image.drawAbsolute(0, 0, /*g*/ g2d);
    }

    abstract void updatePosition();

    abstract void updateSize();

}
