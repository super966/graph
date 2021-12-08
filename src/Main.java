import MathComponent.Vector2d;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Vector2d v1 = new Vector2d(250,250);
        Vector2d v2 = new Vector2d(400, 400);
        Vector2d v3 = new Vector2d(400, 250);
        Triangle triangle = new Triangle(v1, v2, v3);

        JFrame frame = new JFrame("Points");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container bg = frame.getContentPane();
        bg.setBackground(Color.black);


        bg.add(triangle);
        bg.validate();

        frame.setSize(500, 500);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }
}
