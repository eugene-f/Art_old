package kz.ef.art.graphics;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GraphicsMainFrame extends JFrame {

    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private Graphics graphics;

    //    CSky cSky = new CSky(this);
//    CEarth cEarth = new CEarth(this);
//    CTank cTank = new CTank(cEarth);
//    CFire cFire = new CFire();
//    JPanel pFire = new JPanel();
    CSky cSky;
    CEarth cEarth;
    CTank cTank;
    CFire cFire;
    private JPanel pFire;

    public GraphicsMainFrame() throws HeadlessException {
        new GraphicSettingsFrame(this);

        setTitle("Графика");

//        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        getContentPane().setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        repaint();
        getContentPane().setPreferredSize(DEFAULT_SIZE);
        pack();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if (e.getComponent() instanceof GraphicsMainFrame) {
                    updateComponents();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onClick(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            List<Character> integerList = new ArrayList<>();

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
                    cTank.drive(Integer.parseInt(String.valueOf(e.getKeyChar())));
                }
            }
        });

        initGraphics();
        cSky = new CSky(this);
        cEarth = new CEarth(this);
        cTank = new CTank(cEarth);
        cFire = new CFire();
        pFire = new JPanel();

        pFire.setLayout(null);

        pFire.setLocation(0, 0);
        pFire.setSize(getContentPane().getWidth(), getContentPane().getHeight());
        pFire.setOpaque(false);

        add(pFire);
        add(cTank);
        add(cEarth);
        add(cSky);
        updateComponents();

        setVisible(true);
//        Utils.printFrameSize(this);
    }

    private void initGraphics() {
        graphics = getContentPane().getGraphics();
    }

    void updateLastSize() {
        System.out.println("lastFormSize before: " + PanelSettings.lastFormSize);
        if ((getSize().getWidth() != DEFAULT_SIZE.getWidth())
                &&
                (getSize().getHeight() != DEFAULT_SIZE.getHeight())) {
            PanelSettings.lastFormSize = getSize();
        }
        System.out.println("lastFormSize after: " + PanelSettings.lastFormSize);
    }

    public void updateComponents() {
        cSky.updatePosition();
        cSky.updateSize();
        cEarth.updatePosition();
        cEarth.updateSize();
        cTank.updatePosition();
        pFire.setSize(getContentPane().getSize());
        repaint();
    }

    private void onClick(MouseEvent e) {
        final int mouseX = getContentPane().getMousePosition().x;
        final int mouseY = getContentPane().getMousePosition().y;
//        System.out.println("X: " + mouseX + "   Y: " + mouseY);
//        System.out.println("in cSky: " + inComponentArea(mouseX, mouseY, cSky));
//        System.out.println("in cEarth: " + inComponentArea(mouseX, mouseY, cEarth));
//        System.out.println("in cTank: " + inComponentArea(mouseX, mouseY, cTank));

        final CTarget cTarget = new CTarget(pFire, mouseX, mouseY);
        pFire.add(cTarget);
        repaint();

        if (Util.inComponentArea(mouseX, mouseY, cEarth)) {
            final Timer addTimer = new Timer(3000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CFire fire = new CFire();
                    pFire.add(fire).setLocation(
                            mouseX - cFire.getWidth() / 2,
                            mouseY - cFire.getHeight() / 2
                    );
                    if (Util.inComponentArea(mouseX, mouseY, cTank)) {
                        cTank.drive(5);
                        System.out.println("Hit");
                    }
                    final Timer removeTimer = new Timer(3000, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            pFire.remove(fire);
                            pFire.repaint();
                        }
                    });
                    removeTimer.setRepeats(false);
                    removeTimer.start();
                }
            });
            addTimer.setRepeats(false);
            addTimer.start();
        }
    }

}
