package kz.ef.art.graphics;

import kz.ef.art.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CTank extends ComponentElement {

    private final CEarth EARTH;

    private Timer timer;
    static int currentDirection = 5;
    static int timerStep = 375;

    private boolean isStopped = false;

    public CTank(CEarth earth) {
        super(new AImage(AImage.TANK));
        EARTH = earth;
        updatePosition();
        if (!isStopped) drive(getRandomDirection());
    }

    void updatePosition() {
        int x = (EARTH.getWidth() + EARTH.getX()) / 2;
        int y = (EARTH.getHeight() + EARTH.getY()) / 2;
        setLocation(x, y);
        repaint();
    }

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
            timer = new Timer(timerStep, new ActionListener() {
                int x = getX();
                int y = getY();
                int[] a = {0, 0};
                int dX = 0;
                int dY = 0;

                void generateShift() {
                    switch (direction) {

                        // it's faster
                        case 8: a = new int[]{+0, -1}; break;
                        case 9: a = new int[]{+1, -1}; break;
                        case 6: a = new int[]{+1, +0}; break;
                        case 3: a = new int[]{+1, +1}; break;
                        case 2: a = new int[]{-0, +1}; break;
                        case 1: a = new int[]{-1, +1}; break;
                        case 4: a = new int[]{-1, -0}; break;
                        case 7: a = new int[]{-1, -1}; break;

                        // it's slowly
//                        case 8: { dX = +0; dY = -1; break; }
//                        case 9: { dX = +1; dY = -1; break; }
//                        case 6: { dX = +1; dY = +0; break; }
//                        case 3: { dX = +1; dY = +1; break; }
//                        case 2: { dX = -0; dY = +1; break; }
//                        case 1: { dX = -1; dY = +1; break; }
//                        case 4: { dX = -1; dY = -0; break; }
//                        case 7: { dX = -1; dY = -1; break; }

                        default: {
                            System.out.println("Unknown direction");
                            timer.stop();
                            break;
                        }
                    }
                }

                private void applyShift() {
                    x = x + a[0];
                    y = y + a[1];
                    x = x + dX;
                    y = y + dY;
                }

                boolean checkFreeWay() {
                    int left = EARTH.getX();
                    int top = EARTH.getY();
                    int right = EARTH.getX() + EARTH.getWidth() - IMAGE.getImage().getWidth();
                    int bottom = EARTH.getY() + EARTH.getHeight() - IMAGE.getImage().getHeight();
                    return (left <= x && x <= right) && ((top <= y && y <= bottom));
                }

                void reflectDirection() { // fixme: two values for reflect
                    switch (direction) {
                        case 8: drive(2); break;
                        case 9: drive(3); break; // 3 or 7
                        case 6: drive(4); break;
                        case 3: drive(1); break; // 1 or 9
                        case 2: drive(8); break;
                        case 1: drive(3); break; // 3 or 7
                        case 4: drive(6); break;
                        case 7: drive(1); break; // 1 or 9
                    }
                }

                public void actionPerformed(ActionEvent e) {
                    generateShift();
                    applyShift();
                    if (checkFreeWay()) {
                        setLocation(x, y);
                    } else {
                        drive(getRandomDirection());
//                        reflectDirection();
                    }
                }
            });
            timer.start();
            isStopped = false;
        }
    }

    private int getRandomDirection() {
        int direction = Utils.random.nextInt(9) + 1;
        while (direction == 5) {
            direction = Utils.random.nextInt(9) + 1;
        }
        return direction;
    }

    private int getRandomX() {
        return Utils.random.nextInt((int) (GraphicsMainFrame.DEFAULT_SIZE.getWidth() - getWidth()));
    }

    private int getRandomY() {
        return (int) (Utils.random.nextInt(
                (int) (GraphicsMainFrame.DEFAULT_SIZE.getHeight()
                        - getHeight())
        ) + GraphicsMainFrame.DEFAULT_SIZE.getHeight() / 2);
    }

}
