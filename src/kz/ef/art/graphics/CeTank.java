package kz.ef.art.graphics;

import kz.ef.art.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CeTank extends ComponentElement {

    private final CsEarth EARTH;
    private Timer timer;

    static DriveDirection currentDirection = DriveDirection.D5;
    static int timerStep = 375;
    public boolean reflectDirection = false;
    public boolean dynamicSize = false;

    private boolean isStopped = false;

    public CeTank(CsEarth earth) {
        super(new AImage(Texture.TANK_2));
        EARTH = earth;
        updatePosition();
        if (!isStopped) drive(getRandomDirection());
    }

    @Override
//    void drawComponent(Graphics g) {
    void drawComponent(Graphics2D g2d) {
//        image.drawCenter(0, 0, g);
        if (dynamicSize) {
            image.drawCenter(getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), /*g*/ g2d);
        } else {
            int width = this.image.getImage().getWidth();
            int height = this.image.getImage().getHeight();
            setSize(width, height);
            image.drawCenter(getWidth() / 2, getHeight() / 2, /*g*/ g2d);
        }
    }

    void updatePosition() {
        int x = (EARTH.getWidth() + EARTH.getX()) / 2;
        int y = (EARTH.getHeight() + EARTH.getY()) / 2;
        setLocation(x, y);
        repaint();
    }

    void stop() {
        if (timer != null) timer.stop();
        isStopped = true;
    }

    void drive(int dir) {
        drive(DriveDirection.get(dir));
    }

    private int deltaX = 0;
    private int deltaY = 0;
    void drive(DriveDirection direction) {
        if (direction == DriveDirection.D5) {
            stop();
            return;
        }
        if (direction != currentDirection) {
            stop();
            currentDirection = direction;
        }
        if (isStopped) { // fixme: move genDir and variables to root of this method

            switchView(currentDirection);

            deltaX = direction.getDeltaX();
            deltaY = direction.getDeltaY();

            timer = new Timer(timerStep, new ActionListener() {
                int x = getX();
                int y = getY();
                boolean checkFreeWay() {
                    int left = EARTH.getX();
                    int top = EARTH.getY();
                    int right = EARTH.getX() + EARTH.getWidth() - image.getImage().getWidth();
                    int bottom = EARTH.getY() + EARTH.getHeight() - image.getImage().getHeight();
                    return (left <= x && x <= right) && ((top <= y && y <= bottom));
                }
                void reflectDirection() { // fixme: two values for reflect
                    int left = EARTH.getX();
                    int top = EARTH.getY();
                    int right = EARTH.getX() + EARTH.getWidth() - image.getImage().getWidth();
                    int bottom = EARTH.getY() + EARTH.getHeight() - image.getImage().getHeight();
                    if (x <= left || x >= right) deltaX = -deltaX;
                    if (y <= top || y >= bottom) deltaY = -deltaY;
                    currentDirection = DriveDirection.get(deltaX, deltaY);
                    switchView(currentDirection);
                }
                public void actionPerformed(ActionEvent e) {
//                    x += direction.dX;
//                    y += direction.dY;
                    x += deltaX;
                    y += deltaY;
                    if (checkFreeWay()) {
                        setLocation(x, y);
                        if (dynamicSize) {
                            if (currentDirection.getDeltaY() != 0) {
                                changeSize(currentDirection.getDeltaY() * getY() * 0.00005);
                            }
                        }
                    } else {
//                        drive(getRandomDirection());
                        if (reflectDirection) reflectDirection();
                        else stop();
                    }
                }
            });

            timer.start();
            isStopped = false;
        }
    }

    private void switchView(DriveDirection direction) {
        switch (direction) {
            case D5: break;
            case D8: setImage(Texture.TANK_8); break;
            case D9: setImage(Texture.TANK_9); break;
            case D6: setImage(Texture.TANK_6); break;
            case D3: setImage(Texture.TANK_3); break;
            case D2: setImage(Texture.TANK_2); break;
            case D1: setImage(Texture.TANK_1); break;
            case D4: setImage(Texture.TANK_4); break;
            case D7: setImage(Texture.TANK_7); break;
        }
    }

    public void changeSize(double sizeDelta) {
        double v = 1.00 + sizeDelta;
        Dimension currentSize = getSize();
        double currentSizeWidth = currentSize.getWidth();
        double height = currentSize.getHeight();
        int newWidth = (int) (currentSizeWidth * v);
        int newHeight = (int) (height * v);
        Dimension d = new Dimension(newWidth, newHeight);
        setSize(d);

        Point location = getLocation();
        double x = location.getX();
        double y = location.getY();
        int newX = (int) (x - newWidth / 2);
        int newY = (int) (y - newHeight / 2);
        setLocation(newX, newY);

        //        double v = 1.00 + sizeDelta;
//
//        Dimension currentSize = getSize();
//        double currentWidth = currentSize.getWidth();
//        double currentHeight = currentSize.getHeight();
//        double newWidth = currentWidth * v;
//        double newHeight = currentHeight * v;
//
//        Point currentLocation = getLocation();
//        double currentX = currentLocation.getX();
//        double currentY = currentLocation.getY();
//        double currentCenterX = currentX + (currentWidth / 2);
//        double currentCenterY = currentY + (currentHeight / 2);
////        int newX = (int) (currentX - newWidth / 2);
////        int newY = (int) (currentY - newHeight / 2);
//        double newX = currentCenterX - (newWidth / 2);
//        double newY = currentCenterY - (newHeight / 2);
//
//        setSize((int) newWidth, (int) newHeight);
//        setLocation((int) newX, (int) newY);
//        repaint();
    }

    private DriveDirection getRandomDirection() {
        DriveDirection[] values = DriveDirection.values();
        DriveDirection direction;
        do {
            int i = Utils.random.nextInt(values.length);
            direction = values[i];
        } while (direction == DriveDirection.D5);
        return direction;
    }

    private int getRandomX() {
        return Utils.random.nextInt((int) (GraphicsMainFrame.FORM_SIZE.getWidth() - getWidth()));
    }

    private int getRandomY() {
        return (int) (Utils.random.nextInt(
                (int) (GraphicsMainFrame.FORM_SIZE.getHeight() - getHeight())
        ) + GraphicsMainFrame.FORM_SIZE.getHeight() / 2);
    }

}
