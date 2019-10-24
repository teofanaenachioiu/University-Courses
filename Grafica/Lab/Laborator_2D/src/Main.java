import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Main extends JPanel {
    private Sinus sines = new Sinus();

    public Main() {
        super(new BorderLayout());
        JLabel labelX = new JLabel("none x");
        labelX.setBounds(500, 20, 78, 16);
        add(labelX);


        JLabel labelY = new JLabel("none y");
        labelY.setBounds(500, 50, 78, 16);
        add( labelY);

        add(BorderLayout.CENTER,sines);
        sines.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent arg0) {
                int x = arg0.getX();
                int y = arg0.getY();
                if(sines.sinContains(x,y)){
                    labelX.setText(String.valueOf(x));
                    labelY.setText(String.valueOf(y));
                }
                else{
                    labelX.setText("n/a");
                    labelY.setText("n/a");
                }
            }
        });
    }

    public static void main(String[] args) {
        JPanel p = new Main();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(p);
        frame.setSize(600,300 );

        frame.setVisible(true);
    }
}