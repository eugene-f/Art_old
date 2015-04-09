package kz.ef.art.graphics;

import kz.ef.art.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CTank extends JComponent {

    AImage aImage = new AImage(AImage.TANK);
    int width = aImage.getImg().getWidth();
    int height = aImage.getImg().getHeight();

    boolean isStopped = true;

    int x = 100;
    int y = 100;

    public CTank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void move() {
        int distance;
        int direction;
        if (isStopped) {
            Timer timer = new Timer(10, new ActionListener() {
                //            @Override
                public void actionPerformed(ActionEvent e) {
                    setLocation(getX() + 1, getY() + 1);
                }
            });
            timer.start();
            isStopped = false;
        }
    }

    Timer timer;
    int dir = 5;

    void stop() {
        if (timer != null) {
            timer.stop();
        }
        isStopped = true;
    }

    void move(final int direction) {
        if (direction == 5) {
            stop();
            return;
        }
        if (direction != dir) {
            stop();
            dir = direction;
        }
        if (isStopped) { // fixme: move genDir and variables to root of this method
            timer = new Timer(10, new ActionListener() {
                //            @Override
                int x = getX();
                int y = getY();
                int[] a = {0, 0};
                int dX = 0;
                int dY = 0;
                void genDir() {
                    switch (direction) {
                        // it's faster
                        case 8: a = new int[]{0, -1};
                            break;
                        case 9: a = new int[]{+1, -1};
                            break;
                        case 6: a = new int[]{+1, 0};
                            break;
                        case 3: a = new int[]{+1, +1};
                            break;
                        case 2: a = new int[]{0, +1};
                            break;
                        case 1: a = new int[]{-1, +1};
                            break;
                        case 4: a = new int[]{-1, 0};
                            break;
                        case 7: a = new int[]{-1, -1};
                            break;

                        // it's slowly
//                        case 8: { dX = 0;  dY = -1; break; }
//                        case 9: { dX = +1; dY = -1; break; }
//                        case 6: { dX = +1; dY = 0;  break; }
//                        case 3: { dX = +1; dY = +1; break; }
//                        case 2: { dX = 0;  dY = +1; break; }
//                        case 1: { dX = -1; dY = +1; break; }
//                        case 4: { dX = -1; dY = 0;  break; }
//                        case 7: { dX = -1; dY = -1; break; }

                        default: {
                            System.out.println("Unknown direction");
//                            return; // fixme: methid not stopt, this always run in timer
                            break;
                        }

                    }
                }

                public void actionPerformed(ActionEvent e) {
                    genDir();
                    x = x + a[0];
                    y = y + a[1];
                    x = x + dX;
                    y = y + dY;
                    setLocation(x, y);
                }
            });
            timer.start();
            isStopped = false;
        }
    }

    public CTank() {
        setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        aImage.draw(0, 0, g);
        aImage.draw(width / 2, height / 2, g);
    }

    int generatePositionX() {
        x = Util.random.nextInt(GraphicsFrame.WIDTH - width);
        return x;
    }

    int generatePositionY() {
        y = Util.random.nextInt(GraphicsFrame.HEIGHT - height) + GraphicsFrame.HEIGHT / 2;
        return y;
    }
}
