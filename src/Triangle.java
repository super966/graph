import MathComponent.Vector2d;

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

    public void dda(Vector2d v1, Vector2d v2, Graphics2D g){

        double increx, increy, x, y, length;
        length = Math.max(Math.abs(v2.getX()-v1.getX()),Math.abs(v2.getY()-v1.getY()));
        increx = ((v2.getX() - v1.getX())/length);
        increy = ((v2.getY() - v1.getY())/length);
        x = v1.getX();
        y = v1.getY();

        for (int i = 0; i < length ;i++){
            g.drawLine((int) ((int) x+0.5), (int) ((int)y+0.5), (int) ((int) x+0.5), (int) ((int)y+0.5));
            x += increx;
            y += increy;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);

        dda(one,two,g2d);
        dda(two,three,g2d);
        dda(three,one,g2d);
    }
}
