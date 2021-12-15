import Component.*;
import MathComponent.Matrix;
import MathComponent.Vector4d;
import MathComponent.Vertex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Vertex v1 = new Vertex(new Vector4d(3,0,0,1));
        Vertex v2 = new Vertex(new Vector4d(0,6,0,1));
        Vertex v3 = new Vertex(new Vector4d(0,3,0,1));
        Vertex v4 = new Vertex(new Vector4d(3,3,0,1));
        v1.setNormal(new Vector4d(0,0,-1,0));
        v2.setNormal(new Vector4d(0,0,-1,0));
        v3.setNormal(new Vector4d(0,0,-1,0));
        v4.setNormal(new Vector4d(0,0,-1,0));
        v1.setMaterial(new Material(0.2,0.4,0.6,2));
        v2.setMaterial(new Material(0.2,0.4,0.6,2));
        v3.setMaterial(new Material(0.2,0.4,0.6,2));
        v4.setMaterial(new Material(0.2,0.4,0.6,2));
        v1.setColor(Color.red);
        v2.setColor(Color.blue);
        v3.setColor(Color.green);
        v4.setColor(Color.green);

        v1.setTexture(new TextureCoord(1,0));
        v2.setTexture(new TextureCoord(0, 1));
        v3.setTexture(new TextureCoord(0,0));
        v4.setTexture(new TextureCoord(1,1 ));

        Vertex[] points = new Vertex[3];
        points[0] = v1;
        points[1] = v2;
        points[2] = v3;
        Obj obj = new Obj();
        obj.addtri(
                new Triangle(points)
        );
        points[2] = v4;
//        obj.addtri(
//                new Triangle(points)
//        );

        Vector4d campos = new Vector4d(-2,0,-2,1);
        Vector4d camright = new Vector4d(1,0,0,1);
        Vector4d camup = new Vector4d(0,1,0,1);

        Camera cam = new Camera(campos,camup,camright,60,1,1,5);

        AmbtLight ambtLight = new AmbtLight(254,67,101);
        PointLight pointLight = new PointLight(255,255,255, new Vector4d(0,0,1,0));

        ViewPoint viewPoint = new ViewPoint(0,0,250,250,0,1);

        Vector4d objPos = new Vector4d(1,0,0,1);
        Vector4d scale = new Vector4d(1,1,1,1);
        double xdeg = 0;
        double ydeg = 0;
        double zdeg = 0;

        Matrix worldMatrix = new Matrix().worldTransform(objPos,scale,xdeg,ydeg,zdeg);
        obj.setWorldmatrix(worldMatrix);


        Scene scene = Scene.getInstance();
        scene.setCamera(cam);
        scene.setAmbtLight(ambtLight);
        scene.setPointLight(pointLight);
        scene.setViewPoint(viewPoint);
        scene.addObj(obj);
        File img = new File("C:\\Users\\Administrator\\IdeaProjects\\graph\\src\\robot.bmp");
        BufferedImage bi = ImageIO.read(img);
        scene.setTexture(bi);

        JFrame frame = new JFrame("Scene");

        frame.setSize(1000, 800);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        obj.setBounds(0, 0, 1000, 800);
        frame.setVisible(true);
//        obj.setBackground(Color.black);


//        for (int i = 0; i < Scene.getInstance().getObj().size(); i++) {
        frame.addKeyListener(obj);
        frame.add(obj);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
