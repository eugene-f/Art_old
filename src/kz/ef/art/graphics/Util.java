package kz.ef.art.graphics;

import javax.swing.*;


public class Util {

    static boolean inArea(int tX, int tY, int aX0, int aY0, int aX1, int aY1) {
        return (aX0 <= tX) && (tX <= aX1) && (aY0 <= tY) && (tY <= aY1);
    }

    static boolean inComponentArea(int x, int y, JComponent c) {
        return inArea(x, y, c.getX(), c.getY(), c.getX() + c.getWidth(), c.getY() + c.getHeight());
    }

}
