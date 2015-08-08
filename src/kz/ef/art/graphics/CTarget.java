package kz.ef.art.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CTarget extends Component {

    private final CTarget TARGET;
    private final JPanel PANEL;
    private final JLabel LABEL;
    private static final int SIZE = 5;
    private static final int CENTER = (SIZE - 1) / 2;

    public CTarget(JPanel panel, int mouseX, int mouseY) {
        TARGET = this;
        PANEL = panel;
        setSize(SIZE, SIZE);
        setLocation(mouseX, mouseY);
        drawBorders = false;

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
                LABEL.setText(String.valueOf(3000 - timeUp));
                if (timeUp >= 3000) {
                    PANEL.remove(LABEL);
                    PANEL.remove(TARGET);
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
