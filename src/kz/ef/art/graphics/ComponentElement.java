package kz.ef.art.graphics;

import java.awt.*;

abstract class ComponentElement extends Component {

    final AImage IMAGE;

    ComponentElement(AImage image) {
        IMAGE = image;
        int width = IMAGE.getImage().getWidth();
        int height = IMAGE.getImage().getHeight();
        setSize(width, height);
    }

    @Override
//    void drawComponent(Graphics g) {
    void drawComponent(Graphics2D g2d) {
//        IMAGE.draw(0, 0, g);
        IMAGE.draw(getWidth() / 2, getHeight() / 2, /*g*/ g2d);
    }

}
