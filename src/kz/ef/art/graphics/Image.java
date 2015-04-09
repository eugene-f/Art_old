package kz.ef.art.graphics;

import kz.ef.art.Runner;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    static BufferedImage img = null;
    static Graphics graphics;

    static {
        try {
//            img = ImageIO.read(new File("res/boom.png"));
            img = ImageIO.read(Runner.class.getResource("/boom.png"));
        } catch (IOException e) {
        }
//        graphics = img.getGraphics();
    }

    public static void draw(int x, int y, Graphics graphics) {
        graphics.drawImage(img, x, y, null);
    }

    public static void draw(int x, int y, Graphics graphics, boolean center) {
        graphics.drawImage(img, x-img.getWidth()/2-10, y-img.getHeight()+25, null);
    }

}
