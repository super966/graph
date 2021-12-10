import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        try {
            BufferedImage bi = ImageIO.read(img);
            int width = bi.getWidth();
            int height = bi.getHeight();
            int rgb = bi.getRGB(100, 100);
            int blue = rgb&0x000000ff;
            int green = rgb>>8&0x000000ff;
            int red = rgb>>16&0x000000ff;
            System.out.println(red +" " + green + " " + blue);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
