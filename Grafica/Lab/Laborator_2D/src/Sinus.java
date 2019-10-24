import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;

class Sinus extends JPanel {
    private static final int SCALEFACTOR = 200;

    private int cycles;

    private int points;

    private double[] sines;

    private int[] pts;

    private double hstep;

    private int maxHeight;
    private int maxWidth;

    private int epsilon = 5;

    public Sinus() {
        setCycles(6);
    }

    public void setCycles(int newCycles) {
        cycles = newCycles;
        points = SCALEFACTOR * cycles;
        sines = new double[points];
        for (int i = 0; i < points; i++) {
            double radians = (Math.PI / SCALEFACTOR) * i;
            sines[i] = Math.sin(radians);
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        maxWidth = getWidth() - 200;
        maxHeight = maxWidth / cycles;

//        g2.drawLine(0, maxHeight/2, maxWidth, maxHeight/2);
//        g2.drawLine(maxWidth/2, 0, maxWidth/2, maxHeight);

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLUE);

        hstep = (double) maxWidth / (double) points;

        pts = new int[points];

        for (int i = 0; i < points; i++)
            pts[i] = (int) (sines[i] * maxHeight / 2 + maxHeight / 2);

        for (int i = 1; i < points; i++) {
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1 = pts[i - 1];
            int y2 = pts[i];
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public boolean sinContains(int x2, int y2) {
        int x = (int) (x2 / hstep);
        double radians = (Math.PI / SCALEFACTOR) * x;
        float sinesX = (float) Math.sin(radians);
        return  Math.abs(y2-(int) (sinesX * maxHeight / 2 + maxHeight / 2))<epsilon;
    }

    public Pair<Float, Float> getCoords(int x, int y){
        float xp = (float) (x * hstep);
        float radians = (float) (Math.PI / SCALEFACTOR) * xp;
        float sinesX = (float) Math.sin(radians);
        float yp= sinesX * maxHeight / 2 + maxHeight / 2;
        return new Pair<Float, Float>(xp, yp);
    }
}
