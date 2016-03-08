package kz.ef.art.graphics;

import java.awt.*;

abstract class ComponentElement extends BaseComponent {

    final AImage image;

    ComponentElement(AImage image) {
        this.image = image;
        int width = this.image.getImage().getWidth();
        int height = this.image.getImage().getHeight();
        setSize(width, height);
    }

    @Override
//    void drawComponent(Graphics g) {
    void drawComponent(Graphics2D g2d) {
//        image.drawCenter(0, 0, g);
        image.drawCenter(getWidth() / 2, getHeight() / 2, /*g*/ g2d);
    }

    public void setImage(String filePath) {
        this.image.setImage(filePath);
        int width = this.image.getImage().getWidth();
        int height = this.image.getImage().getHeight();
        setSize(width, height);
    }

}
