package kz.ef.art.vision.examples.test;

import javax.swing.JApplet;

public class SnniperGameApp extends JApplet {

static final long serialVersionUID = 2777718668465204446L;
//i dont know what this serial thing is. But my program wont start without it

public SnniperGameApp(){
    add(new movingControl());
}

}