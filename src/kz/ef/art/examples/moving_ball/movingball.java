package kz.ef.art.examples.moving_ball;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class movingball extends JPanel{

    private int move=50;
    private Timer timer = new Timer(move, new TimerListener());
    private int radius = 10;
    private int x = 300;    
    private int y = 0;

    public  movingball() {
          timer.start();
    }

    private class TimerListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
        x=x-20;
        repaint();  }//trying to get the oval to move left 20 
    }

    protected void paintComponent(Graphics2D g){
    super.paintComponent(g);
    g.fillOval(x, 100 , radius * 2, radius * 2);    }
    }