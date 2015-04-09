package kz.ef.art.graphics;

import kz.ef.art.Runner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AImage {

    public static final String TANK = "/ic_directions_car_black_48dp.png";
    public static final String BOOM = "/ic_whatshot_black_48dp.png";

    BufferedImage img;
//    String name = "/boom.png";
//    static Graphics graphics;

    public AImage(String name) {
//        this.name = name;
//        img = ImageIO.read(new File("res/boom.png"));
        try {
            img = ImageIO.read(Runner.class.getResource(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    graphics=img.getGraphics();

    public void draw(int x, int y, Graphics graphics) {
        if (img == null) {
            System.out.println("image==null");
        } else {
            graphics.drawImage(img, x - img.getWidth() / 2, y - img.getHeight() / 2, null);
        }
    }

    public BufferedImage getImg() {
        return img;
    }



}