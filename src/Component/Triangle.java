package Component;

import MathComponent.Matrix;
import MathComponent.Vector2d;
import MathComponent.Vector4d;
import MathComponent.Vertex;

import javax.swing.*;
import java.awt.*;

public class Triangle extends JPanel {
    private Vertex[] point = new Vertex[3];


    public Triangle(Vertex[] v) {
        for (int i = 0; i < v.length; i++) {
            point[i] = v[i];
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

    public Color lightModel(Vertex ver){
        AmbtLight ambtLight = Scene.getInstance().getAmbtLight(); //环境光
        PointLight pointLight = Scene.getInstance().getPointLight(); //漫反射

        Vector4d L = new Vector4d(pointLight.getDir().revserse());
        L.Normalize();
        Vector4d normal = ver.getNormal(); //法向量
        normal.Normalize();

        Vector4d campos =Scene.getInstance().getCamera().getPos();
        Vector4d V = campos.minus(ver.getPos());
        V.Normalize();
        Vector4d H = L.add(V).div(2);
        H.Normalize();

        int Ir, Ib, Ig;

        Material m = ver.getMaterial();

        double K = m.getKd() * (L.mul(normal)) + m.getKs() * Math.pow(H.mul(normal), m.getN());
        K = K > 0 ? K : 0;
        Ir = (int) (m.getKa() * ambtLight.getR() + pointLight.getR() * K);
        Ir = Ir > 255?255: Math.max(Ir, 0);
        Ig = (int) (m.getKa() * ambtLight.getG() + pointLight.getG() * K);
        Ig = Ig > 255?255: Math.max(Ig, 0);
        Ib = (int) (m.getKa() * ambtLight.getR() + pointLight.getB() * K);
        Ib = Ib > 255?255: Math.max(Ib, 0);

        return new Color(Ir,Ig,Ib);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Vertex one = point[0];
        Vertex two = point[1];
        Vertex three = point[2];

        Vector2d oneV2 = new Vector2d(one.getPos().getX(), one.getPos().getY());
        Vector2d twoV2 = new Vector2d(two.getPos().getX(), two.getPos().getY());
        Vector2d threeV2 = new Vector2d(three.getPos().getX(), three.getPos().getY());

        dda(oneV2,twoV2,g2d,one.getColor(),two.getColor());

        dda(twoV2,threeV2,g2d,two.getColor(),three.getColor());

        dda(threeV2,oneV2,g2d,three.getColor(),one.getColor());
    }

    public void draw(Matrix worldMatrix) {
        JFrame frame = Scene.getInstance().getBackground();
        Scene scene = Scene.getInstance();
        Camera camera = scene.getCamera();
        Matrix viewTransMatrix = camera.getViewTransform();
        Matrix projTransMatrix = camera.getProjTransform();
        ViewPoint viewPoint = scene.getViewPoint();
        Matrix viewpointMatrix = new Matrix().viewPointTransform(
                viewPoint.getX(),
                viewPoint.getY(),
                viewPoint.getWidth(),
                viewPoint.getHeight(),
                viewPoint.getMinZ(),
                viewPoint.getMaxZ());
        Vertex[] temp = new Vertex[3];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = point[i];
            temp[i].matrixMul(worldMatrix);
            temp[i].setColor(lightModel(temp[i]));
            temp[i].matrixMul(viewTransMatrix);
            temp[i].setZ_deep(temp[i].getPos().getZ());
            temp[i].matrixMul(projTransMatrix);
            temp[i].matrixMul(viewpointMatrix);
        }
        this.point = temp;
        frame.getContentPane().add(this);
    }
}
