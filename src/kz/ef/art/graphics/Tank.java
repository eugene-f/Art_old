package kz.ef.art.graphics;

import kz.ef.art.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tank extends JPanel {

    static final int WIDTH = 25;
    static final int HEIGHT = 10;

    static int x = 100;
    static int y = 100;

    void move(Graphics g) {
        Graphics2D gr2d = (Graphics2D) g;
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x = x + 1;
//                paint(gr2d);
                paintComponent(gr2d);
//                update(gr2d);
//                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr2d = (Graphics2D) g;
        gr2d.setPaint(Color.YELLOW.darker());
        gr2d.fillRect(
                Tank.x,
                Tank.y,
                Tank.WIDTH,
                Tank.HEIGHT
        );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr2d = (Graphics2D) g;
        gr2d.setPaint(Color.YELLOW.darker());
        gr2d.fillRect(
                Tank.x,
                Tank.y,
                Tank.WIDTH,
                Tank.HEIGHT
        );
    }

    int generatePositionX() {
        x = Util.random.nextInt(GraphicsFrame.WIDTH - Tank.WIDTH);
        return x;
    }

    int generatePositionY() {
        y = Util.random.nextInt(GraphicsFrame.HEIGHT - Tank.HEIGHT) + GraphicsFrame.HEIGHT / 2;
        return y;
    }

}
