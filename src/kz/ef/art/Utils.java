package kz.ef.art;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Utils {

    public static final Random random = new Random();

    public static void printFrameSize(JFrame frame) {
        final Dimension formSize = frame.getSize();
        final Dimension paneSize = frame.getContentPane().getSize();
        final double bordersWidth = formSize.getWidth() - paneSize.getWidth();
        final double bordersHeight = formSize.getHeight() - paneSize.getHeight();

        System.out.println(frame.getTitle() + " paneSize: " + paneSize);
        System.out.println(frame.getTitle() + " formSize: " + formSize);
        System.out.println(frame.getTitle() + " bordersWidth: " + bordersWidth);
        System.out.println(frame.getTitle() + " bordersHeight: " + bordersHeight);
    }

}
