import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Points extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.red);

        for (int i = 0; i <= 100000; i++) {
            Dimension size = getSize();

            int w = size.width ;

            int h = size.height;

            Random r = new Random();

            int x = Math.abs(r.nextInt()) % w;

            int y = Math.abs(r.nextInt()) % h;

            g2d.drawLine(x, y, x, y);

        }
    }
}
