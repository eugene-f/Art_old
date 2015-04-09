package kz.ef.art.graphics;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GraphicsFrame extends JFrame {

    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    AImage aImageBoom = new AImage(AImage.BOOM);
    AImage aImageTank = new AImage(AImage.TANK);

    Graphics graphics = getGraphics();

    JComponent cSky = new CSky();
    JComponent cEarth = new CEarth();
    JComponent cTank = new CTank();
    JComponent cFire = new CFire();
    JPanel pFire = new JPanel();

    private void createForm() {
        setTitle("Graphics");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // todo: delete thia after build release
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onClick(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            java.util.List<Character> integerList = new ArrayList<Character>();

            {
                for (Character i = '0'; i <= '9'; i++) {
                    integerList.add(i);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == 'q') {
                    System.exit(0);
                }
                if (integerList.contains(e.getKeyChar())) {
                    ((CTank) cTank).drive(Integer.parseInt(String.valueOf(e.getKeyChar())));
                }
            }
        });

        cSky.setLayout(null);
        cEarth.setLayout(null);
        cTank.setLayout(null);
        cFire.setLayout(null);

        pFire.setLayout(null);
        pFire.setLocation(0, 0);
        pFire.setSize(WIDTH, HEIGHT);
        pFire.setOpaque(false);

        add(pFire);
        add(cTank).setLocation(CTank.D_X, CTank.D_Y);
        add(cEarth).setLocation(CEarth.D_X, CEarth.D_Y);
        add(cSky).setLocation(CSky.D_X, CSky.D_Y);

        setVisible(true);
        graphics = getGraphics();
    }

    Timer timerAdd;
    Timer timerRemove;

    private void onClick(MouseEvent e) {
        final int mouseX = getMousePosition().x;
        final int mouseY = getMousePosition().y;
        System.out.println("X: " + mouseX + "   Y: " + mouseY);

        graphics.setColor(Color.red);
        graphics.drawLine(mouseX, mouseY, mouseX, mouseY);
        graphics.drawOval(mouseX - 2, mouseY - 2, 4, 4);

        if (   ( timerAdd == null || !timerAdd.isRunning() ) && ( timerRemove == null || !timerRemove.isRunning() )   ) {
            timerAdd = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pFire.add(cFire).setLocation(mouseX - CFire.D_WIDTH / 2, mouseY - CFire.D_HEIGHT / 2);

                    if (
                                    (mouseX >= cTank.getX()) && (mouseX <= cTank.getX()+CTank.D_WIDTH)
                                    &&
                                    (mouseY >= cTank.getY()) && (mouseY <= cTank.getY()+CTank.D_HEIGHT)
                            )
                    {
                        ((CTank) cTank).drive(5);
                        System.out.println("Hit");
                    }

                }
            });
            timerAdd.setRepeats(false);
            timerAdd.start();

            timerRemove = new Timer(4000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    pFire.remove(cFire);
                    pFire.repaint();
                }
            });
            timerRemove.setRepeats(false);
            timerRemove.start();
        }
    }

    public static void main(String[] args) {
        new GraphicsFrame();
    }

    public GraphicsFrame() throws HeadlessException {
        createForm();
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
            gr2d.setPaint(Color.getHSBColor(5 + i * 350, 5 + i * 100, 5 + i * 100));
            gr2d.drawLine(400 + i * 5, 400 - i * 6, 400 + i * 4, 400 + i * 3);
        }

        // Очистка области прямоугольгой формы
        gr2d.clearRect(50, 40, 200, 200);
    }
}
