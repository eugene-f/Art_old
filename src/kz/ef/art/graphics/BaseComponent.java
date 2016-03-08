package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;

abstract class BaseComponent extends JComponent {

    private Color colorBackground = Color.WHITE;
    private Color colorBorder = Color.BLACK;
    private static boolean drawBorders = false;

    BaseComponent() {
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

    abstract void drawComponent(Graphics2D g2d);

    private void drawBorders(Graphics2D g2d) {
        g2d.setPaint(colorBorder);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

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

    public Color getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(Color colorBackground) {
        this.colorBackground = colorBackground;
    }

    public Color getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }

    public static boolean isDrawBorders() {
        return drawBorders;
    }

    public static void setDrawBorders(boolean drawBorders) {
        BaseComponent.drawBorders = drawBorders;
    }

}
