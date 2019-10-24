import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import javax.swing.*;

public class LocGeometric extends JPanel {

    private int width = 600;
    private int height = 400;

    private int xVarf = 400, yVarf = height / 2;
    private int x = 80, y = 50;

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        // draw X-Y axis
        g2.drawLine(0, height / 2, width, height / 2);
        g2.drawLine(width / 2, 0, width / 2, height);

        // draw quadratic curve
        g2.setColor(Color.red);
        Path2D p = new Path2D.Double();
        p.moveTo(x, height - y);
        p.quadTo(xVarf, yVarf, x, y);

        Path2D p1 = new Path2D.Double();
        p1.moveTo(width - x, height - y);
        p1.quadTo(width - xVarf, yVarf, width - x, y);

        g2.draw(p);
        g2.draw(p1);
    }

//    public void paintComponent(Graphics g) {
//
//        Graphics2D g2 = (Graphics2D) g;
//
//        // draw X-Y axis
//        g2.drawLine(0, height / 2, width, height / 2);
//        g2.drawLine(width / 2, 0, width / 2, height);
//
//        g2.setColor(Color.black);
//
//        float x;
//        float y;
//
//        float x2;
//        float y2;
//
//        for (x = 0; x <= width + 200; x = x + 1f) {
//            y = x * x;
//            x2 = x + 1f;
//            y2 = x2 * x2;
//            g2.draw(new Line2D.Double(x, y, x2, y2));
//        }
//    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Hyperbola");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(610, 430);

        LocGeometric panel = new LocGeometric();

        JLabel labelX = new JLabel("none x");
        labelX.setBounds(550, 375, 78, 16);
        frame.add(labelX);

        JLabel labelY = new JLabel("none y");
        labelY.setBounds(550, 400, 78, 16);
        frame.add(labelY);
        frame.add(BorderLayout.CENTER, panel);
        frame.setVisible(true);

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent arg0) {
                int x = arg0.getX();
                int y = arg0.getY();

//                int indx = ((samples-1)*x)/panelWidth+1;

//                double val = (Math.cos(indx)*-1+1)*amplitude+panelHeight/2-amplitude/2;
//                if (Math.abs(val-y)<EPSILON )
//                {
//                    labelX.setText(x+"");
//                    labelY.setText(Math.sin(indx)+"");
//                }
//                else
//                {
                labelX.setText(String.valueOf(x));
                labelY.setText(String.valueOf(y));
//                }
            }
        });
    }
}
