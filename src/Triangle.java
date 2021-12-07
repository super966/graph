import Vector.Vector2d;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JPanel {
    private Vector2d one;
    private Vector2d two;
    private Vector2d three;

    public Triangle(Vector2d one, Vector2d two, Vector2d three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;



        g2d.setColor(Color.red);
        g2d.drawLine((int)one.getX(),(int)one.getY(),(int)two.getX(),(int)two.getY());
        g2d.drawLine((int)two.getX(),(int)two.getY(),(int)three.getX(),(int)three.getY());
        g2d.drawLine((int)three.getX(),(int)three.getY(),(int)one.getX(),(int)one.getY());

    }
}
