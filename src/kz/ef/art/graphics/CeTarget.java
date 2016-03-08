package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CeTarget extends BaseComponent {

    private static final int SIZE = 5;
    private static final int CENTER = (SIZE - 1) / 2;
    private static final int SHOW_TIME = 3000;

    private final JPanel PANEL;
    private final JLabel LABEL;

    public CeTarget(JPanel panel, int mouseX, int mouseY) {
        PANEL = panel;
        setSize(SIZE, SIZE);
        setLocation(mouseX, mouseY);

        LABEL = new JLabel("00000");
        LABEL.setSize(LABEL.getPreferredSize());
        LABEL.setLocation((int) (mouseX - LABEL.getPreferredSize().getWidth() / 2), mouseY + 5);
        LABEL.setBackground(Color.BLACK);
        LABEL.setVerticalAlignment(JLabel.CENTER);
        LABEL.setHorizontalAlignment(JLabel.CENTER);
        LABEL.setForeground(Color.RED);
        LABEL.setOpaque(true); // for show background
        PANEL.add(LABEL);
        PANEL.repaint();

        final long startTimeMillis = System.currentTimeMillis();
        new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final long currentTimeMillis = System.currentTimeMillis();
                final long timeUp = currentTimeMillis - startTimeMillis;
                LABEL.setText(String.valueOf(SHOW_TIME - timeUp));
                if (timeUp >= SHOW_TIME) {
                    PANEL.remove(LABEL);
                    PANEL.remove(CeTarget.this);
                    PANEL.repaint();
                }
            }
        }).start();
    }

    @Override
    void drawComponent(Graphics2D g2d) {
        g2d.setColor(Color.RED.darker());
        g2d.drawLine(CENTER, CENTER, CENTER, CENTER);
        g2d.drawOval(0, 0, SIZE - 1, SIZE - 1);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x - CENTER, y - CENTER);
    }

}
