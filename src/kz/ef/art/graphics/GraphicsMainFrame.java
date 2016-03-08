package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GraphicsMainFrame extends JFrame {

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension FORM_SIZE = new Dimension(800, 600);
    private static Dimension lastFormSize = GraphicsMainFrame.FORM_SIZE;
    private static Point lastFormLocation = null;
    private static GraphicsMainFrame INSTANCE;

    private URL url = GraphicsMainFrame.class.getResource(Texture.FIRED);
    private ImageIcon imageIcon = new ImageIcon(url);
    JLabel jLabel = new JLabel(imageIcon);
    private CsSky csSky;
    private CsEarth csEarth;
    CeTank ceTank;
    private CeFire ceFire;
    private Graphics graphics;
    private JPanel panelFire;

    public static GraphicsMainFrame getInstance() {
        if (INSTANCE == null) INSTANCE = new GraphicsMainFrame();
        return INSTANCE;
    }

    private void initGraphics() {
        graphics = getContentPane().getGraphics();
    }

    public GraphicsMainFrame() throws HeadlessException {
        INSTANCE = this;

        setTitle("Графика");

//        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        getContentPane().setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        repaint();

        getContentPane().setPreferredSize(FORM_SIZE);
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
                if (e.getKeyChar() == 'f') {
                    setImmersiveMode(false);
                }
                if (integerList.contains(e.getKeyChar())) {
                    jLabel.setVisible(false);
                    ceTank.drive(Integer.parseInt(String.valueOf(e.getKeyChar())));
                }
            }
        });

        setLayout(null);
        add(jLabel);
        jLabel.setBounds(15, 15, 64, 64);
        jLabel.setVisible(false);
        pack();

        initGraphics();
        csSky = new CsSky(this);
        csEarth = new CsEarth(this);
        ceTank = new CeTank(csEarth);
        ceFire = new CeFire();
        panelFire = new JPanel();

        panelFire.setLayout(null);

        panelFire.setLocation(0, 0);
        panelFire.setSize(getContentPane().getWidth(), getContentPane().getHeight());
        panelFire.setOpaque(false);


        add(panelFire);
        add(ceTank);
        add(csEarth);
        add(csSky);
        updateComponents();

        setVisible(true);
//        Utils.printFrameSize(this);
        PanelSettings.createForm(this);
    }

    public void setImmersiveMode(boolean value) {
        if (value) {
            updateLastSize();
            lastFormLocation = getLocation();

            dispose();
            setUndecorated(true);
            pack();
            setVisible(true);

            setSize(GraphicsMainFrame.SCREEN_SIZE);
            setLocation(0, 0);
        } else {
            dispose();
            setUndecorated(false);
            pack();
            setVisible(true);

            setSize(lastFormSize);
            if (lastFormLocation != null) {
                setLocation(lastFormLocation);
            } else {
                setLocationRelativeTo(null);
            }
        }
        updateComponents();
    }

    void updateLastSize() {
        boolean noChangeWidth = getSize().getWidth() != FORM_SIZE.getWidth();
        boolean noChangeHeight = getSize().getHeight() != FORM_SIZE.getHeight();
        if (noChangeWidth && noChangeHeight) {
            lastFormSize = getSize();
        }
    }

    public void updateComponents() {
        csSky.updatePosition();
        csSky.updateSize();
        csEarth.updatePosition();
        csEarth.updateSize();
        ceTank.updatePosition();
        panelFire.setSize(getContentPane().getSize());
        repaint();
    }

    private void onClick(MouseEvent e) {
        final int mouseX = getContentPane().getMousePosition().x;
        final int mouseY = getContentPane().getMousePosition().y;
        addShot(mouseX, mouseY);
    }

    public void shot(int x, int y, int width, int height) {
        final int shotX = (csEarth.getWidth() * x) / width;
        final int shotY = (csEarth.getHeight() * y) / height;
        shot(shotX, shotY);
    }

    public void shot(int x, int y) {
        final int shotX = csEarth.getX() + x;
        final int shotY = csEarth.getY() + y;
        addShot(shotX, shotY);
    }

    private static final int SHOW_AFTER_TIME = 3000;
    private static final int HIDE_AFTER_TIME = 3000;

    private int bullet_count = 0;

    private void addShot(final int x, final int y) {
        final CeTarget ceTarget = new CeTarget(panelFire, x, y);
        panelFire.add(ceTarget);
        repaint();
        bullet_count++;
        if (Util.inComponentArea(x, y, csEarth)) {
            final Timer addTimer = new Timer(SHOW_AFTER_TIME, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CeFire fire = new CeFire(x - ceFire.getWidth() / 2, y - ceFire.getHeight() + 30);
                    panelFire.add(fire);
                    if (Util.inComponentArea(x, y, ceTank)) {
                        ceTank.drive(5);
                        System.out.println("Hit: " + bullet_count);
                        bullet_count = 0;
                        jLabel.setBounds(x - 275, y - 275, 500, 400);
//                        jLabel.setVisible(true)/;
                        jLabel.repaint();
                    }
                    final Timer removeTimer = new Timer(HIDE_AFTER_TIME, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            panelFire.remove(fire);
                            panelFire.repaint();
                            jLabel.setVisible(true);
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
