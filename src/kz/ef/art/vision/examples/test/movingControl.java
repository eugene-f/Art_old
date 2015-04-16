package kz.ef.art.vision.examples.test;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class movingControl extends JPanel {

private movingball ball= new movingball();

public movingControl(){
    JPanel panel = new JPanel();
    ball.setBorder(new javax.swing.border.LineBorder(Color.red));
    panel.addMouseListener(new movingballListener());
    setLayout(new BorderLayout());
    add(ball, BorderLayout.CENTER);
    add(panel, BorderLayout.SOUTH);
}
}