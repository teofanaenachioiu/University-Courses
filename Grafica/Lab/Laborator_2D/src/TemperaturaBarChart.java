import java.awt.*;
import javax.swing.*;

public class TemperaturaBarChart extends JPanel {

    private double[] values;
    private String[] labels;
    private String title;

    public TemperaturaBarChart(double[] values, String[] labels, String title) {
        this.labels = labels;
        this.values = values;
        this.title = title;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.length == 0) {
            return;
        }

        double minValue = 0;
        double maxValue = 0;

        for (int i = 0; i < values.length; i++) {
            if (minValue > values[i]) {
                minValue = values[i];
            }
            if (maxValue < values[i]) {
                maxValue = values[i];
            }
        }

        Dimension dim = getSize();
        int panelWidth = dim.width;
        int panelHeight = dim.height;

        int barWidth = panelWidth / values.length;

        Font titleFont = new Font("Book Antiqua", Font.BOLD, 15);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);

        Font labelFont = new Font("Book Antiqua", Font.ITALIC, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int stringHeight = titleFontMetrics.getAscent();
        int stringWidth = (panelWidth - titleWidth) / 2;
        g.setFont(titleFont);
        g.drawString(title, stringWidth, stringHeight);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue) {
            return;
        }

        double scale = (panelHeight - top - bottom) / (maxValue - minValue);
        stringHeight = panelHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);
        for (int j = 0; j < values.length; j++) {
            int valueP = j * barWidth + 1;
            int valueQ = top;
            int height = (int) (values[j] * scale);
            if (values[j] >= 0) {
                valueQ += (int) ((maxValue - values[j]) * scale);
            } else {
                valueQ += (int) (maxValue * scale);
                height = -height;
            }

            g.setColor(Color.RED);
            g.fillRect(valueP, valueQ, barWidth - 2, height);
            g.setColor(Color.black);
            g.drawRect(valueP, valueQ, barWidth - 2, height);

            int labelWidth = labelFontMetrics.stringWidth(labels[j]);
            stringWidth = j * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(labels[j], stringWidth, stringHeight);
        }
    }

    public static void main(String[] args) {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Temperaturi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);

        String title = "Temperaturi";
        double[] values = new double[]{-70,-7,7,10,14,22.5,20,22,16,10,6,4};
        String[] labels = new String[]{"Ian","Feb","Mar","Apr","Mai","Iun","Iul","Aug","Sept","Oct","Noi","Dec"};
        TemperaturaBarChart bc = new TemperaturaBarChart(values, labels, title);

        frame.add(bc);
        frame.setVisible(true);
    }
}