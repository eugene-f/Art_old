package kz.ef.art.graphics;

import kz.ef.art.Runner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class AImage {

    private BufferedImage image;

    public AImage(String filePath) {
        setImage(filePath);
    }

    public void drawCenter(int x, int y, int w, int h, Graphics g) {
        if (image != null) g.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), x - (w / 2), y - (h / 2), null);
        else System.out.println("image == null");
    }

    public void drawCenter(int x, int y, Graphics g) {
        if (image != null) g.drawImage(image, x - (image.getWidth() / 2), y - (image.getHeight() / 2), null);
        else System.out.println("image == null");
    }

    public void drawAbsolute(int x, int y, Graphics g) {
        if (image != null) g.drawImage(image, x, y, null);
        else System.out.println("image == null");
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(String filePath) {
        try {
//            image = ImageIO.read(new File("res/boom.png"));
            image = ImageIO.read(Runner.class.getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
