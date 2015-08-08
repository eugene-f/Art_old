package examples.moving_ball;

import javax.swing.*;
import java.awt.*;

public class movingControl extends JPanel {

    private movingball ball = new movingball();

    public movingControl() {
        JPanel panel = new JPanel();
        ball.setBorder(new javax.swing.border.LineBorder(Color.red));
        panel.addMouseListener(new movingballListener());
        setLayout(new BorderLayout());
        add(ball, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }
}
