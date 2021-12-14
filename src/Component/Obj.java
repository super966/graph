package Component;

import MathComponent.Matrix;
import MathComponent.Vector2d;
import MathComponent.Vector4d;
import MathComponent.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LSY
 * @date 2021/12/09 13:05
 **/
public class Obj extends JPanel implements KeyListener {

    private List<Triangle> ltri = new ArrayList<>();
    private Matrix worldmatrix;
    private JPanel jPanel = new JPanel();


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < ltri.size(); i++) {

            Camera c = Scene.getInstance().getCamera();
            System.out.println(c);
            Triangle t = new Triangle(ltri.get(i));
            Matrix rotateX = new Matrix().rotateX(0.01);
            Matrix rotateY = new Matrix().rotateY(0.02);
            worldmatrix  = worldmatrix.mul(rotateX);
            worldmatrix = worldmatrix.mul(rotateY);
            Vertex[] temp = t.draw(worldmatrix, c);

            Vertex one = temp[0];
            Vertex two = temp[1];
            Vertex three = temp[2];

            int x1 = (int) one.getPos().getX();
            int y1 = (int) one.getPos().getY();
            int x2 = (int) two.getPos().getX();
            int y2 = (int) two.getPos().getY();
            int x3 = (int) three.getPos().getX();
            int y3 = (int) three.getPos().getY();

            if ((x1 == x2 && x1 == x3) || (y1 == y2 && y1 == y3))
                return;

            if (y1 > y2) {
                Vertex swap = two;
                two = one;
                one = swap;
            }
            if (y1 > y3) {
                Vertex swap = three;
                three = one;
                one = swap;
            }
            if (y2 > y3) {
                Vertex swap = two;
                two = three;
                three = swap;
            }
            x1 = (int) one.getPos().getX();
            y1 = (int) one.getPos().getY();
            x2 = (int) two.getPos().getX();
            y2 = (int) two.getPos().getY();
            x3 = (int) three.getPos().getX();
            y3 = (int) three.getPos().getY();

            if (Math.abs(one.getPos().getY() - two.getPos().getY()) < 0.00001f) {
//                Vector2d oneV2 = new Vector2d(one.getPos().getX(), one.getPos().getY());
//                Vector2d twoV2 = new Vector2d(two.getPos().getX(), two.getPos().getY());
//                Vector2d threeV2 = new Vector2d(three.getPos().getX(), three.getPos().getY());
//
//
//
////                dda(oneV2,temv,g2d,one.getColor(),tempVer.getColor());
//                dda(oneV2,twoV2,g2d,one.getColor(),two.getColor());
//                dda(twoV2,threeV2,g2d,two.getColor(),three.getColor());
//
//                dda(oneV2,threeV2,g2d,one.getColor(),three.getColor());
                topTriangle(one, two, three, g2d);
//                ltri.remove(i);
//                ltri.add(t);
            } else if (Math.abs(two.getPos().getY() - three.getPos().getY()) < 0.00001f) {
                downTriangle(one, two, three, g2d);
            } else {
                int tempVer_x = (int) (x1 + 0.5 + 1.0 * (y2 - y1) * (x3 - x1) / (y3 - y1));
                int tempVer_r = (int) (one.getColor().getRed() + 1.0 * (y2 - y1) *
                        (three.getColor().getRed() - one.getColor().getRed()) / (y3 - y1));
                int tempVer_g = (int) (one.getColor().getGreen() + 1.0 * (y2 - y1) *
                        (three.getColor().getGreen() - one.getColor().getGreen()) / (y3 - y1));
                int tempVer_b = (int) (one.getColor().getBlue() + 1.0 * (y2 - y1) *
                        (three.getColor().getBlue() - one.getColor().getBlue()) / (y3 - y1));

                tempVer_r = tempVer_r < 0 ? 0 : Math.min(tempVer_r, 255);
                tempVer_b = tempVer_b < 0 ? 0 : Math.min(tempVer_b, 255);
                tempVer_g = tempVer_g < 0 ? 0 : Math.min(tempVer_g, 255);

                Color tempVer_c = new Color(tempVer_r, tempVer_g, tempVer_b);
                Vertex tempVer = new Vertex();
                tempVer.setTexture(one.getTexture());
                tempVer.setNormal(one.getNormal());
                tempVer.setMaterial(one.getMaterial());
                tempVer.setPos(new Vector4d(0, 0, 0, 0));
                tempVer.setZ_deep(one.getZ_deep());
                tempVer.getPos().setX(tempVer_x);
                tempVer.getPos().setY(one.getPos().getY());
                tempVer.getPos().setZ(one.getPos().getZ());
                tempVer.getPos().setW(one.getPos().getW());
                tempVer.setColor(tempVer_c);

                double s = (y2 - y1) / (y3 - y2);
                double z1 = one.getZ_deep();
                double z3 = three.getZ_deep();
                double zt = 0;
                double k = 0;
                if (z1 != 0 && z3 != 0) zt = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (zt != 0) zt = 1 / zt;
                if (z1 != z3) k = (zt - z1) / (z3 - z1);

                tempVer.setZ_deep(zt);

                try {
                    tempVer.getTexture().setU(one.getTexture().getU() + k * (three.getTexture().getU() - one.getTexture().getU()));
                    tempVer.getTexture().setV(one.getTexture().getV() + k * (three.getTexture().getV() - one.getTexture().getV()));
                } catch (Exception e) {
                    System.out.println(e);
                }
//                Vector2d temv = new Vector2d(tempVer.getPos().getX(),tempVer.getPos().getY());
//                Vector2d oneV2 = new Vector2d(one.getPos().getX(), one.getPos().getY());
//                Vector2d twoV2 = new Vector2d(two.getPos().getX(), two.getPos().getY());
//                Vector2d threeV2 = new Vector2d(three.getPos().getX(), three.getPos().getY());
//


//                dda(oneV2,temv,g2d,one.getColor(),tempVer.getColor());
//                dda(oneV2,twoV2,g2d,one.getColor(),tempVer.getColor());
//                dda(twoV2,threeV2,g2d,two.getColor(),three.getColor());
//
//                dda(threeV2,oneV2,g2d,three.getColor(),one.getColor());
                downTriangle(one, tempVer, two, g2d);
                topTriangle(tempVer, two, three, g2d);

            }
            repaint();

        }
    }

    private void downTriangle(Vertex one, Vertex two, Vertex three, Graphics2D g2d) {
        //three 底
        if(Math.abs(one.getPos().getY()- three.getPos().getY())<0.00001f){
            Vertex temp = two;
            two = three;
            three = temp;
        }else if(Math.abs(two.getPos().getY() - three.getPos().getY()) < 0.00001f){
            Vertex temp = three;
            three = one;
            one = temp;
        }else if(Math.abs(one.getPos().getY() - two.getPos().getY()) < 0.00001f){

        }else{
            return;
        }
        if(one.getPos().getX() > two.getPos().getX()){
            Vertex temp = two;
            two = one;
            one = temp;
        }else if(Math.abs(two.getPos().getX() - one.getPos().getX()) < 0.0001f){
            return;
        }

        double x1 = one.getPos().getX();
        double x2 = two.getPos().getX();
        double x3 = three.getPos().getX();
        double y1 = one.getPos().getY();
        double y2 = two.getPos().getY();
        double y3 = three.getPos().getY();
        int height = Scene.getInstance().getTexture().getHeight();
        int width = Scene.getInstance().getTexture().getWidth();
        Color c1 = getColor(Scene.getInstance().getTexture().getRGB((int) one.getTexture().getU() * (width-1),(int) one.getTexture().getV()*(height-1)));
        Color c2 = getColor(Scene.getInstance().getTexture().getRGB((int) two.getTexture().getU() * (width-1),(int) two.getTexture().getV()*(height-1)));
        Color c3 = getColor(Scene.getInstance().getTexture().getRGB((int) three.getTexture().getU() * (width-1),(int) three.getTexture().getV()*(height-1)));


        double xleft = (x1-x3)/(y1-y3);
        double xright = (x2-x3)/(y2-y3);
        double red_l = ((c1.getRed() - c3.getRed())/(y1-y3));
        double red_r = ((c2.getRed() - c3.getRed())/(y2-y3));
        double blue_l = ((c1.getBlue() - c3.getBlue())/(y1-y3));
        double blue_r = ((c2.getBlue() - c3.getBlue())/(y2-y3));
        double green_l = ((c1.getGreen() - c3.getGreen())/(y1-y3));
        double green_r = ((c2.getGreen() - c3.getGreen())/(y2-y3));

        double xs = x3;
        double xe = x3;
        double rs = c3.getRed();
        double re = c3.getRed();
        double gs = c3.getGreen();
        double ge = c3.getGreen();
        double bs = c3.getBlue();
        double be = c3.getBlue();

        for(double y = y3; y < y1; y++){

            double s= (y - y3)/(y1 - y3);
            double z1 = one.getZ_deep();
            double z2 = two.getZ_deep();
            double z3 = three.getZ_deep();
            double zt = 0;
            double k = s;
            if(z1 != 0 && z3 != 0) zt = 1/z3 + s * (1/z1 - 1/ z3);
            if(zt != 0) zt = 1/zt;
            if(z1 != z3) k = (zt - z3)/(z1 - z3);

            double zl = zt;
            double ul = three.getTexture().getU() + k * (one.getTexture().getU()  - three.getTexture().getU());
            double vl = three.getTexture().getV() + k * (one.getTexture().getV()  - three.getTexture().getV());

             k = s;
            if(z3 != 0 && z2 != 0) zt = 1/z3 + s * (1/z2 - 1/ z3);
            if(zt != 0) zt = 1/zt;
            if(z3 != z2) k = (zt - z3)/(z2 - z3);

            double zr = zt;
            double ur = three.getTexture().getU() + k * (two.getTexture().getU()  - three.getTexture().getU());
            double vr = three.getTexture().getV() + k * (two.getTexture().getV()  - three.getTexture().getV());

            line(xs,xe,y,zl,zr,ul,ur,vl,vr,g2d);

            xs += xleft;
            xe += xright;
            rs += red_l;
            re += red_r;
            gs += green_l;
            ge += green_r;
            bs += blue_l;
            be += blue_r;
        }

    }

    public Color getColor(int c){
        int red =  (c>>16 & 0x0000ff) ;
        int green = (c>>8  & 0x0000ff);
        int blue =  (c     & 0x0000ff);

        Color color = new Color(red,green,blue);
        return color;
    }

    //平底三角形
    private void topTriangle(Vertex one, Vertex two, Vertex three, Graphics2D g2d) {
        //one 顶
        if(Math.abs(three.getPos().getY()- one.getPos().getY())<0.00001f){
            Vertex temp = two;
            two = one;
            one = temp;
        }else if(Math.abs(one.getPos().getY() - two.getPos().getY()) < 0.00001f){
            Vertex temp = three;
            three = one;
            one = temp;
        }else if(Math.abs(three.getPos().getY() - two.getPos().getY()) < 0.00001f){

        }else{
            return;
        }
        if(three.getPos().getX() > two.getPos().getX()){
            Vertex temp = two;
            two = three;
            three = temp;
        }

        Vertex temp = one;
        one = three;
        three = temp;

        double x1 = one.getPos().getX();
        double x2 = two.getPos().getX();
        double x3 = three.getPos().getX();
        double y1 = one.getPos().getY();
        double y2 = two.getPos().getY();
        double y3 = three.getPos().getY();
        int height = Scene.getInstance().getTexture().getHeight();
        int width = Scene.getInstance().getTexture().getWidth();
        Color c1 = getColor(Scene.getInstance().getTexture().getRGB((int) one.getTexture().getU() * (width-1),(int) one.getTexture().getV()*(height-1)));
        Color c2 = getColor(Scene.getInstance().getTexture().getRGB((int) two.getTexture().getU() * (width-1),(int) two.getTexture().getV()*(height-1)));
        Color c3 = getColor(Scene.getInstance().getTexture().getRGB((int) three.getTexture().getU() * (width-1),(int) three.getTexture().getV()*(height-1)));

        double xleft = (x3-x1)/(y3-y1);
        double xright = (x3-x2)/(y3-y2);
        double red_l = ((c3.getRed() - c1.getRed())/(y3-y1));
        double red_r = ((c3.getRed() - c2.getRed())/(y3-y2));
        double blue_l = ((c3.getBlue() - c1.getBlue())/(y3-y1));
        double blue_r = ((c3.getBlue() - c2.getBlue())/(y3-y2));
        double green_l = ((c3.getGreen() - c1.getGreen())/(y3-y1));
        double green_r = ((c3.getGreen() - c2.getGreen())/(y3-y2));

        double xs = x1;
        double xe = x2;
        double rs = c1.getRed();
        double re = c2.getRed();
        double gs = c1.getGreen();
        double ge = c2.getGreen();
        double bs = c1.getBlue();
        double be = c2.getBlue();

        for(double y = y1; y < y3; y++){

            double s= (y - y1)/(y3 - y1);
            double z1 = one.getZ_deep();
            double z2 = two.getZ_deep();
            double z3 = three.getZ_deep();
            double zt = 0;
            double k = s;
            if(z1 != 0 && z3 != 0) zt = 1/z1 + s * (1/z3 - 1/ z1);
            if(zt != 0) zt = 1/zt;
            if(z1 != z3) k = (zt - z1)/(z3 - z1);

            double zl = zt;
            double ul = one.getTexture().getU() + k * (three.getTexture().getU()  - one.getTexture().getU());
            double vl = one.getTexture().getV() + k * (three.getTexture().getV()  - one.getTexture().getV());

            k = s;
            if(z3 != 0 && z2 != 0) zt = 1/z2 + s * (1/z3 - 1/ z2);
            if(zt != 0) zt = 1/zt;
            if(z3 != z2) k = (zt - z2)/(z3 - z2);

            double zr = zt;
            double ur = two.getTexture().getU() + k * (three.getTexture().getU()  - two.getTexture().getU());
            double vr = two.getTexture().getV() + k * (three.getTexture().getV()  - two.getTexture().getV());

//            line(xs,xe,y,rs,gs,bs,re,ge,be,zl,zr,ul,ur,vl,vr,g2d);
            line(xs,xe,y,zl,zr,ul,ur,vl,vr,g2d);

            xs += xleft;
            xe += xright;
            rs += red_l;
            re += red_r;
            gs += green_l;
            ge += green_r;
            bs += blue_l;
            be += blue_r;
        }
    }

    private void line(double xs, double xe, double y, double zl, double zr, double ul, double ur, double vl, double vr, Graphics2D g2d) {
        double deltaR = 0.0;
        double deltaG = 0.0;
        double deltaB = 0.0;

//        if(Math.abs(xe - xs) > 0.0001f){
//            deltaR = (re - rs) * 1.0 / (xe - xs);
//            deltaG = (ge - gs) * 1.0 / (xe - xs);
//            deltaB = (be - bs) * 1.0 / (xe - xs);
//        }
        int textheight = Scene.getInstance().getTexture().getHeight();
        int textwidth = Scene.getInstance().getTexture().getWidth();
        double x = 0;
        for( x = xs;x < xe;x++){
            double fac_x = (x - xs)/(xe - xs);
            double new_z = 0.0;
            double fac_t = fac_x;
            if(zl != 0.0 && zr != 0.0) new_z = 1/zl + fac_x * (1/zr - 1/ zl);
            if(new_z != 0) new_z = 1 / new_z;
            if(zl != zr) fac_t = (new_z - zl)/(zr - zl);
            double u = ul + fac_t * (ur - ul);
            double v = vl + fac_t * (vr - vl);

//            double u = ul;
//            double v = vl;

            v = (v > 1.0) ? 1.0 : Math.max(v, 0.0);
            u = (u > 1.0) ? 1.0 : Math.max(u, 0.0);

            ;
            int c = 0;
            try {
                c = Scene.getInstance().getTexture().getRGB((int) ((textwidth-1) * u), (int) ((textheight - 1) * v));
            }catch (Exception e){
                System.out.println(e);
            }

//            int red = (int)   (rs * (c>>16 & 0x0000ff) / 255) ;
//            int green = (int)  (gs * (c>>8  & 0x0000ff) / 255);
//            int blue = (int)   (bs * (c     & 0x0000ff) / 255);

//            red  = red < 0? 0 : Math.min(red, 255);
//            blue  = blue < 0? 0 : Math.min(blue, 255);
//            green  = green < 0? 0 : Math.min(green, 255);
//
////            System.out.println(red + " " + blue + " " + green);
//            Color color = new Color(red,green,blue);

            g2d.setColor(getColor(c));
            g2d.drawLine((int) x, (int) y, (int) x, (int) y);
//            rs += deltaR;
//            bs += deltaB;
//            gs += deltaG;
        }
    }

    public void dda(Vector2d v1, Vector2d v2, Graphics2D g, Color c1, Color c2){

        double increx, increy, x, y, length;
        length = Math.max(Math.abs(v2.getX()-v1.getX()),Math.abs(v2.getY()-v1.getY()));
        increx = ((v2.getX()- v1.getX())/length);
        increy = ((v2.getY() - v1.getY())/length);
        x = v1.getX();
        y = v1.getY();
        double dis = Math.sqrt(Math.pow(v2.getX() - v1.getX(),2) + Math.pow(v2.getY()-v1.getY(),2));
        int red, green, blue;
        for (int i = 0; i < length ;i++){

            double t = Math.sqrt(Math.pow(x - v1.getX(),2) + Math.pow(y-v1.getY(),2))/dis;
            if(c1.getRed() > c2.getRed()) {
                red = (int) (c1.getRed() - t * (Math.abs(c1.getRed() - c2.getRed())));
            }else{
                red = (int) (c1.getRed() + t * (Math.abs(c1.getRed() - c2.getRed())));
            }

            if(c1.getBlue() > c2.getBlue()) {
                blue = (int) (c1.getBlue() - t * (Math.abs(c1.getBlue() - c2.getBlue())));
            }else{
                blue = (int) (c1.getBlue() + t * (Math.abs(c1.getBlue() - c2.getBlue())));
            }

            if(c1.getGreen() > c2.getGreen()) {
                green = (int) (c1.getGreen() - t * (Math.abs(c1.getGreen() - c2.getGreen())));
            }else{
                green = (int) (c1.getGreen() + t * (Math.abs(c1.getGreen() - c2.getGreen())));
            }

            g.setColor(new Color(red,blue,green));

            g.drawLine((int) ((int) x+0.5), (int) ((int)y+0.5), (int) ((int) x+0.5), (int) ((int)y+0.5));
            x += increx;
            y += increy;
        }
    }


    public void addtri(Triangle t){
        this.ltri.add(t);
    }

    public void setWorldmatrix(Matrix wpos){
        this.worldmatrix = wpos;
    }

    public Vector4d moveLeft(double step) {
        Vector4d pos = Scene.getInstance().getCamera().getPos();
        Vector4d n_right = new Vector4d(Scene.getInstance().getCamera().getRight());
        pos = pos.add(n_right.mul(step));
        return pos;
    }

    public Vector4d moveRight(double step) {
        Vector4d pos = Scene.getInstance().getCamera().getPos();
        Vector4d n_right = new Vector4d(Scene.getInstance().getCamera().getRight());

        pos = pos.minus(n_right.mul(step));
        return pos;
    }

    public Vector4d moveUp(double step) {
        Vector4d pos = Scene.getInstance().getCamera().getPos();
        Vector4d n_up = new Vector4d(Scene.getInstance().getCamera().getUp());
        pos = pos.add(n_up.mul(step));
        return pos;
    }

    public Vector4d moveDown(double step) {
        Vector4d pos = Scene.getInstance().getCamera().getPos();
        Vector4d n_up = new Vector4d(Scene.getInstance().getCamera().getUp());
        pos = pos.minus(n_up.mul(step));
        return pos;
    }

    public Vector4d forword(double step){
        Vector4d pos = Scene.getInstance().getCamera().getPos();
        Vector4d n_up = new Vector4d(Scene.getInstance().getCamera().getUp());
        Vector4d n_right = new Vector4d(Scene.getInstance().getCamera().getRight());
        pos = pos.add(n_right.crossMul(n_up).mul(step));
        return pos;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Vector4d pos = null;
        if (e.getKeyCode() == KeyEvent.VK_A) {
            pos = moveRight(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            pos = moveLeft(1);
        }

        if (e.getKeyCode() == KeyEvent.VK_W) {
            pos = moveUp(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            pos = moveDown(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            pos = forword(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            pos = forword(-1);
        }
        Vector4d n_up = new Vector4d(Scene.getInstance().getCamera().getUp());
        Vector4d n_right = new Vector4d(Scene.getInstance().getCamera().getRight());
        Matrix n_viewTransform = new Matrix().viewTrans(pos,n_up,n_right);
        Scene.getInstance().getCamera().setPos(pos);
        Scene.getInstance().getCamera().setViewTransform(n_viewTransform);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
