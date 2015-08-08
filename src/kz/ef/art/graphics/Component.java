package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

abstract class Component extends JComponent {

    Color COLOR_BACKGROUND = Color.WHITE;
    Color COLOR_BORDER = Color.BLACK;
    boolean drawBorders = true;

    Component() {
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawComponent(g2d);
//        drawComponent(g);
        if (drawBorders) drawBorders(g2d);
    }

    private void drawBorders(Graphics2D g2d) {
        g2d.setPaint(COLOR_BORDER);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    abstract void drawComponent(Graphics2D g2d);

    void printPosition() {
        System.out.println(getClass().getSimpleName());
        System.out.println("X: " + getX());
        System.out.println("Y: " + getY());
    }

    void printSize() {
        System.out.println(getClass().getSimpleName());
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + getHeight());
    }

}
