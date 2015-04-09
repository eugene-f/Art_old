package kz.ef.art.examples.test;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class movingballListener extends MouseAdapter{

    public void mouseReleased(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
          System.out.println(  (e.getPoint()));}}
    }