package kz.ef.art.graphics;

import kz.ef.art.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CTank extends JComponent {

    static final AImage IMAGE = new AImage(AImage.TANK);
    static final int D_X = 50;
    static final int D_Y = 200;
    static final int D_WIDTH = IMAGE.getImage().getWidth();
    static final int D_HEIGHT = IMAGE.getImage().getHeight();

    boolean isStopped = true;

    public CTank() {
        setSize(D_WIDTH, D_HEIGHT);
    }

    Timer timer;
    int currentDirection = 5;

    void stop() {
        if (timer != null) {
            timer.stop();
        }
        isStopped = true;
    }

    void drive(final int direction) {
        if (direction == 5) {
            stop();
            return;
        }
        if (direction != currentDirection) {
            stop();
            currentDirection = direction;
        }
        if (isStopped) { // fixme: move genDir and variables to root of this method
            timer = new Timer(10, new ActionListener() {
                //            @Override
                int x = getX();
                int y = getY();
                int[] a = {0, 0};
                int dX = 0;
                int dY = 0;
                void generateDelta() {
                    switch (direction) {
///*
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
//*/
/*
                        // it's slowly
                        case 8: { dX = 0;  dY = -1; break; }
                        case 9: { dX = +1; dY = -1; break; }
                        case 6: { dX = +1; dY = 0;  break; }
                        case 3: { dX = +1; dY = +1; break; }
                        case 2: { dX = 0;  dY = +1; break; }
                        case 1: { dX = -1; dY = +1; break; }
                        case 4: { dX = -1; dY = 0;  break; }
                        case 7: { dX = -1; dY = -1; break; }
*/
                        default: {
                            System.out.println("Unknown direction");
                            timer.stop();
//                            return; // fixme: methid not stopt, this always run in timer
                            break;
                        }
                    }
                }
                boolean noBorder() {
                    int left = 0;
                    int top = 150;
                    int right = GraphicsFrame.WIDTH - IMAGE.getImage().getWidth();
                    int bottom = GraphicsFrame.HEIGHT - IMAGE.getImage().getHeight();

                    if (   !(   (x >= left && x <= right) && ((y >= top && y <= bottom))   )   ) {
                        return false;
                    }
                    return true;
                }

                void reflectDirection() { // fixme: current direction delimetres to two branches
                    switch (direction) {
                        case 8: drive(2);
                            break;
                        case 9: drive(7);
                            break;
                        case 6: drive(4);
                            break;
                        case 3: drive(1);
                            break;
                        case 2: drive(8);
                            break;
                        case 1: drive(3);
                            break;
                        case 4: drive(6);
                            break;
                        case 7: drive(9);
                            break;
                    }
                }

                public void actionPerformed(ActionEvent e) {
                    generateDelta();
                    plusDelta();
                    if (noBorder()) {
                        setLocation(x, y);
                    } else {
                        drive(getRandomDirection());
//                        stop();
                    }
                }

                private void plusDelta() {
                    x = x + a[0];
                    y = y + a[1];
                    x = x + dX;
                    y = y + dY;
                }
            });
            timer.start();
            isStopped = false;
        }
    }

    private int getRandomDirection() {
        int newDir = Util.random.nextInt(9) + 1;
        while (newDir == 5) {
            newDir = Util.random.nextInt(9) + 1;
        }
        return newDir;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

//        IMAGE.draw(0, 0, g);
        IMAGE.draw(D_WIDTH / 2, D_HEIGHT / 2, g);
    }

    private int generatePositionX() {
        return Util.random.nextInt(GraphicsFrame.WIDTH - D_WIDTH);
    }

    private int generatePositionY() {
        return Util.random.nextInt(GraphicsFrame.HEIGHT - D_HEIGHT) + GraphicsFrame.HEIGHT / 2;
    }
}
