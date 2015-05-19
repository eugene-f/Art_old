package kz.ef.art.graphics;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GraphicsMainFrame extends JFrame {

    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int FULL_WIDTH = (int) SCREEN_SIZE.getWidth();
    static final int FULL_HEIGHT = (int) SCREEN_SIZE.getHeight();

    Graphics graphics = getGraphics();

    JComponent cSky = new CSky();
    JComponent cEarth = new CEarth();
    JComponent cTank = new CTank();
    JComponent cFire = new CFire();
    JPanel pFire = new JPanel();

    private void createForm() {
        new GraphicSettingsFrame(this);

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
            List<Character> integerList = new ArrayList<Character>();
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
        add(cTank).setLocation(CTank.POSITION_X, CTank.POSITION_Y);
        add(cEarth).setLocation(CEarth.POSITION_X, CEarth.POSITION_Y);
        add(cSky).setLocation(CSky.POSITION_X, CSky.POSITION_Y);

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
                    pFire.add(cFire).setLocation(mouseX - CFire.SIZE_WIDTH / 2, mouseY - CFire.SIZE_HEIGHT / 2);
                    if (
                                    (mouseX >= cTank.getX()) && (mouseX <= cTank.getX()+CTank.SIZE_WIDTH)
                                    &&
                                    (mouseY >= cTank.getY()) && (mouseY <= cTank.getY()+CTank.SIZE_HEIGHT)
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
        new GraphicsMainFrame();
    }

    public GraphicsMainFrame() throws HeadlessException {
        createForm();
    }

}
