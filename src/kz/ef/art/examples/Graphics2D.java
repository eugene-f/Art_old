package kz.ef.art.examples;

import javax.swing.*;
import java.awt.*;

public class Graphics2D {

    private void sample(Graphics g, java.awt.Graphics2D gr2d) {

        // ���
        gr2d.setBackground(Color.green);

        // �����
        gr2d.setPaint(Color.RED);
        gr2d.drawLine(300, 50, -50, 300);
        gr2d.setPaint(Color.BLUE);
        gr2d.drawLine(500, 50, 300, 300);

        // ������ ������������� (����������� ��� ������ ������� ������ ��������������)
        BasicStroke c = new BasicStroke(3); //������� ����� 3  ��������������
        gr2d.setStroke(c);

        gr2d.setPaint(Color.MAGENTA);
        Polygon j = new Polygon();
        j.addPoint(270, 439);
        j.addPoint(185, 400);
        j.addPoint(100, 470);
        j.addPoint(200, 550);
        j.addPoint(240, 590);
        j.addPoint(270, 539);
        g.drawPolygon(j);

        gr2d.setPaint(Color.YELLOW);
        gr2d.drawRoundRect(200, 50, 200, 300, 200, 400);
        gr2d.setPaint(Color.DARK_GRAY);

        // ������������� � ������������� ������
        gr2d.drawRoundRect(500, 500, 70, 40, 10, 10);

        // ����
        gr2d.drawOval(300, 50, 300, 300);

        // ����������� ����
        gr2d.fillOval(100, 50, 200, 300);
        gr2d.setPaint(Color.pink);
        gr2d.drawArc(100, 200, 300, 300, JFrame.ABORT, JFrame.ABORT);

        // �������� ������� �����
        gr2d.setPaint(Color.lightGray);
        BasicStroke p = new BasicStroke(10); //������� ����� 20
        gr2d.setStroke(p);

        //���� � ������� ������
        gr2d.setPaint(Color.red);
        gr2d.drawOval(100, 100, 300, 300);

        // ������� �����������
        for (int i = 0; i < 30; i++) {
            gr2d.setPaint(Color.getHSBColor(5 + i * 350, 5 + i * 100, 5 + i * 100));
            gr2d.drawLine(400 + i * 5, 400 - i * 6, 400 + i * 4, 400 + i * 3);
        }

        // ������� ������� ������������� �����
        gr2d.clearRect(50, 40, 200, 200);
    }

}
