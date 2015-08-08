package kz.ef.art.graphics;


import javax.swing.*;
import java.awt.*;

abstract class ComponentScope extends Component {

    private final JFrame FRAME;

    ComponentScope(JFrame FRAME) {
        this.FRAME = FRAME;
    }

    Container getContentPane() {
        return FRAME.getContentPane();
    }

    @Override
    void drawComponent(Graphics2D g2d) {
        g2d.setPaint(COLOR_BACKGROUND);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    abstract void updatePosition();

    abstract void updateSize();

}
