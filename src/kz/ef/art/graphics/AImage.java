package kz.ef.art.graphics;

import kz.ef.art.Runner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class AImage {

    public static final String TANK = "/ic_directions_car_black_24dp.png"; // 48
    public static final String BOOM = "/ic_whatshot_black_24dp.png"; // 48

    private BufferedImage image;

    public AImage(String file) {
//      image = ImageIO.read(new File("res/boom.png"));
        try {
            image = ImageIO.read(Runner.class.getResource(file));
        } catch (IOException e) {
            e.printStackTrace(); // fixme
        }
    }

    public void draw(int x, int y, Graphics g) {
        if (image == null) {
            System.out.println("image == null");
        } else {
            g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

}
