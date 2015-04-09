package kz.ef.art.graphics;

import kz.ef.art.test.SnniperGameApp;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphicsFrame extends JFrame {

    private static final int HALF_FRAME_WIDTH = 400;
    private static final int HALF_FRAME_HEIGHT = 300;
    private static final int FRAME_WIDTH = HALF_FRAME_WIDTH * 2;
    private static final int FRAME_HEIGHT = HALF_FRAME_HEIGHT * 2;
//    private static final int FRAME_WIDTH = 800;
//    private static final int FRAME_HEIGHT = 600;
//    private static final int HALF_FRAME_WIDTH = FRAME_WIDTH / 2;
//    private static final int HALF_FRAME_HEIGHT = FRAME_WIDTH / 2;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    Tank tank = new Tank();

    public static void main(String[] args) {
        new GraphicsFrame();
    }

    public GraphicsFrame() throws HeadlessException {
        createForm();
//        this.pack();
//        System.out.println("frame width : "+getWidth());
//        System.out.println("frame height: "+getHeight());
//        System.out.println("content pane width : "+getContentPane().getWidth());
//        System.out.println("content pane height: "+getContentPane().getHeight());
//        System.out.println("width  of left + right  borders: "+(getWidth()-getContentPane ().getWidth()));
//        System.out.println("height of top  + bottom borders: "+(getHeight()-getContentPane().getHeight()));
//        this.getContentPane().setPreferredSize(new Dimension(500, 500));
//        this.pack();
    }

    private void createForm() {
        setTitle("Graphics");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
//        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
//        setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // todo: delete thia after build release
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Graphics graphics = getGraphics();
                int mouseX = getMousePosition().x;
                int mouseY = getMousePosition().y;
                System.out.println("X: " + mouseX + "   Y: " + mouseY);
                Image.draw(mouseX, mouseY, graphics, true);
                graphics.setColor(Color.red);
                graphics.drawLine(mouseX, mouseY, mouseX, mouseY);
                graphics.drawOval(mouseX - 2, mouseY - 2, 4, 4);
                tank.move(graphics);
//                tank.paintComponent(graphics);
//                repaint();
            }
        });
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gr2d = (Graphics2D) g;

        // Sky
        gr2d.setPaint(Color.BLUE.brighter());
        gr2d.fillRect(0, 0, FRAME_WIDTH, 300);

        // Earth
        gr2d.setPaint(Color.GREEN.darker());
        gr2d.fillRect(0, HALF_FRAME_HEIGHT, FRAME_WIDTH, HALF_FRAME_HEIGHT);

        tank.paint(gr2d);
//        tank.move(gr2d);

//        gr2d.setPaint(Color.RED);
//        Rectangle rectangle = new Rectangle(
//                Tank.generatePositionX(),
//                Tank.generatePositionY(),
//                Tank.WIDTH,
//                Tank.HEIGHT
//        );
    }

    private void sample(Graphics g, Graphics2D gr2d) {

        // Фон
        gr2d.setBackground(Color.green);

        // Линии
        gr2d.setPaint(Color.RED);
        gr2d.drawLine(300, 50, -50, 300);
        gr2d.setPaint(Color.BLUE);
        gr2d.drawLine(500, 50, 300, 300);

        // Рисуем многоугольник (треугольник или звезда частный случай многоугольника)
        BasicStroke с = new BasicStroke(3); //толщина линии 3  многоугольника
        gr2d.setStroke(с);

        gr2d.setPaint(Color.MAGENTA);
        Polygon j = new Polygon();
        j.addPoint(270, 439);
        j.addPoint(185, 400);
        j.addPoint(100, 470);
        j.addPoint(200, 550);
        j.addPoint(240, 590);
        j.addPoint(270, 539);
        g.drawPolygon(j);

        gr2d.setPaint(Color.YELLOW);
        gr2d.drawRoundRect(200, 50, 200, 300, 200, 400);
        gr2d.setPaint(Color.DARK_GRAY);

        // Прямоугольник с закругленными краями
        gr2d.drawRoundRect(500, 500, 70, 40, 10, 10);

        // Овал
        gr2d.drawOval(300, 50, 300, 300);

        // Заполненный овал
        gr2d.fillOval(100, 50, 200, 300);
        gr2d.setPaint(Color.pink);
        gr2d.drawArc(100, 200, 300, 300, ABORT, ABORT);

        // Получаем толстую линию
        gr2d.setPaint(Color.lightGray);
        BasicStroke p = new BasicStroke(10); //толщина линии 20
        gr2d.setStroke(p);

        //Овал с толстой линией
        gr2d.setPaint(Color.red);
        gr2d.drawOval(100, 100, 300, 300);

        // Цветной треугольник
        for (int i = 0; i < 30; i++) {
            gr2d.setPaint(Color.getHSBColor(5+i*350, 5+i*100, 5+i*100));
            gr2d.drawLine(400 + i*5, 400- i*6, 400 + i*4, 400 + i*3);
        }

        // Очистка области прямоугольгой формы
        gr2d.clearRect(50, 40, 200, 200);
    }
}
