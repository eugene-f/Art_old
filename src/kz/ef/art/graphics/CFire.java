package kz.ef.art.graphics;

import kz.ef.art.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CFire extends JComponent {

    AImage aImage = new AImage(AImage.BOOM);
    int width = aImage.getImg().getWidth();
    int height = aImage.getImg().getHeight();


    int x = 100;
    int y = 100;

    public CFire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CFire() {
        setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        aImage.draw(0, 0, g);
        aImage.draw(width / 2, height / 2, g);
    }

}
