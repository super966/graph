import javax.swing.*;
import java.awt.*;

/**
 * @author LSY
 * @date 2021/12/09 16:01
 **/
public class Test {
    public static class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.orange);
            g.fillRect(20, 50, 100, 100);
        }
    }
    public static class MyDrawPanel2 extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.red);
            g.fillRect(50, 50, 100, 100);
        }
    }


    public static class MyDrawPanel3 extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            g.fillRect(150, 150, 100, 100);
        }
    }

    public static class Scene extends JPanel{
        JFrame frame;
        public Scene(JFrame frame){
            this.frame = frame;
        }

        public void draw(JPanel jPanel){
//            MyDrawPanel panel = new MyDrawPanel();
//            JLabel label = new JLabel("aaa");
//            panel.setBackground(Color.blue);
//            panel.add(label);
            frame.getContentPane().add(jPanel);
        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();

        MyDrawPanel panel1 = new MyDrawPanel();
        MyDrawPanel2 panel2 = new MyDrawPanel2();
        MyDrawPanel3 panel3 = new MyDrawPanel3();
        panel2.setOpaque(true);
        panel3.setOpaque(true);
        panel1.add(panel2);
        panel1.add(panel3);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        Scene s = new Scene(frame);
        s.draw(panel1);


    }
}
