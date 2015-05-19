package kz.ef.art.graphics;

import kz.ef.art.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CTank extends JComponent {

    static final AImage IMAGE = new AImage(AImage.TANK);
    static final int POSITION_X = (CEarth.SIZE_WIDTH + CEarth.POSITION_X) / 2;
    static final int POSITION_Y = (CEarth.SIZE_HEIGHT + CEarth.POSITION_Y) / 2;
    static final int SIZE_WIDTH = IMAGE.getImage().getWidth();
    static final int SIZE_HEIGHT = IMAGE.getImage().getHeight();
    static boolean drawBorders = false;

    static int timerStep = 375;
    static int timerStep_max = 1000;

    boolean isStopped = true;

    public CTank() {
        setSize(SIZE_WIDTH, SIZE_HEIGHT);
    }

    Timer timer;
    static int currentDirection = 5;

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
                //            @Override
                int x = getX();
                int y = getY();
                int[] a = {0, 0};
                int dX = 0;
                int dY = 0;
                void generateDelta1() { // it's faster
                    switch (direction) {
                        case 8: a = new int[]{0, -1}; break;
                        case 9: a = new int[]{+1, -1}; break;
                        case 6: a = new int[]{+1, 0}; break;
                        case 3: a = new int[]{+1, +1}; break;
                        case 2: a = new int[]{0, +1}; break;
                        case 1: a = new int[]{-1, +1}; break;
                        case 4: a = new int[]{-1, 0}; break;
                        case 7: a = new int[]{-1, -1}; break;
                        default: {
                            System.out.println("Unknown direction");
                            timer.stop();
                            break;
                        }
                    }
                }
                void generateDelta2() { // it's slowly
                    switch (direction) {
                        case 8: { dX = 0;  dY = -1; break; }
                        case 9: { dX = +1; dY = -1; break; }
                        case 6: { dX = +1; dY = 0;  break; }
                        case 3: { dX = +1; dY = +1; break; }
                        case 2: { dX = 0;  dY = +1; break; }
                        case 1: { dX = -1; dY = +1; break; }
                        case 4: { dX = -1; dY = 0;  break; }
                        case 7: { dX = -1; dY = -1; break; }
                        default: {
                            System.out.println("Unknown direction");
                            timer.stop();
                            break;
                        }
                    }
                }
                private void plusDelta() {
                    x = x + a[0];
                    y = y + a[1];
                    x = x + dX;
                    y = y + dY;
                }
                boolean noBorder() {
                    int left = CEarth.POSITION_X;
                    int top = CEarth.POSITION_Y;
                    int right = GraphicsMainFrame.WIDTH - IMAGE.getImage().getWidth();
                    int bottom = GraphicsMainFrame.HEIGHT - IMAGE.getImage().getHeight();
                    if (   !(   (x >= left && x <= right) && ((y >= top && y <= bottom))   )   ) {
                        return false;
                    }
                    return true;
                }
                void reflectDirection() { // fixme: two values for reflect
                    switch (direction) {
                        case 8: drive(2);
                            break;
                        case 9: drive(3); // 3 or 7
                            break;
                        case 6: drive(4);
                            break;
                        case 3: drive(1); // 1 or 9
                            break;
                        case 2: drive(8);
                            break;
                        case 1: drive(3); // 3 or 7
                            break;
                        case 4: drive(6);
                            break;
                        case 7: drive(1); // 1 or 9
                            break;
                    }
                }

                public void actionPerformed(ActionEvent e) {
                    generateDelta1();
                    plusDelta();
                    if (noBorder()) {
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
        int direction = Util.random.nextInt(9) + 1;
        while (direction == 5) {
            direction = Util.random.nextInt(9) + 1;
        }
        return direction;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawComponent(g);
        if (drawBorders) drawBorders(g2d);
    }

    private void drawComponent(Graphics g) {
        //        IMAGE.draw(0, 0, g);
        IMAGE.draw(SIZE_WIDTH / 2, SIZE_HEIGHT / 2, g);
    }

    private void drawBorders(Graphics2D g2d) {
        g2d.drawRect(0, 0, SIZE_WIDTH - 1, SIZE_HEIGHT - 1);
    }

    private int getRandomX() {
        return Util.random.nextInt(GraphicsMainFrame.WIDTH - SIZE_WIDTH);
    }

    private int getRandomY() {
        return Util.random.nextInt(GraphicsMainFrame.HEIGHT - SIZE_HEIGHT) + GraphicsMainFrame.HEIGHT / 2;
    }
}
