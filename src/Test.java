import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author LSY
 * @date 2021/12/09 16:01
 **/
public class Test extends JPanel implements KeyListener {

    int x=40,y=40;
    Test(){
        JFrame frame = new JFrame();
        frame.setSize( 800, 600);
        frame.setLayout(null);
        this.setBounds(0, 0, 800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.setBackground(Color.GREEN);
        frame.addKeyListener(this);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        new Test();
    }

    public void moveleft(){
        this.x--;
    }

    public void moveright(){
        this.x++;
    }

    public void moveup(){
        this.y--;
    }
    public void movedown(){
        this.y++;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.fillOval(x, y, 20, 20);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A){
            x++;
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
           x--;
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            y++;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            y--;
        }
        System.out.println(x + " " + y);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
