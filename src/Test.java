import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        File img = new File("C:\\Users\\Administrator\\IdeaProjects\\graph\\src\\robot.bmp");
        System.out.println(Math.sin(30*(3.1415926/180)));


    }
}
