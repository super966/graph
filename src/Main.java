import Component.*;
import MathComponent.Matrix;
import MathComponent.Vector4d;
import MathComponent.Vertex;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Vertex v1 = new Vertex(new Vector4d(1,0,0,1));
        Vertex v2 = new Vertex(new Vector4d(0,1,0,1));
        Vertex v3 = new Vertex(new Vector4d(0,0,0,1));
        v1.setNormal(new Vector4d(0,0,-1,0));
        v2.setNormal(new Vector4d(0,0,-1,0));
        v3.setNormal(new Vector4d(0,0,-1,0));
        v1.setMaterial(new Material(0.2,0.4,0.6,2));
        v2.setMaterial(new Material(0.2,0.4,0.6,2));
        v3.setMaterial(new Material(0.2,0.4,0.6,2));

        v1.setColor(Color.red);
        v2.setColor(Color.blue);
        v3.setColor(Color.green);
        Vertex v4 = new Vertex(new Vector4d(0,1,0,1));
        Vertex v5 = new Vertex(new Vector4d(1,0,0,1));
        Vertex v6 = new Vertex(new Vector4d(0,0,1,1));
        v4.setNormal(new Vector4d(1,1,1,0).Normalize());
        v5.setNormal(new Vector4d(1,1,1,0).Normalize());
        v6.setNormal(new Vector4d(1,1,1,0).Normalize());
        v4.setMaterial(new Material(0.2,0.4,0.6,2));
        v5.setMaterial(new Material(0.2,0.4,0.6,2));
        v6.setMaterial(new Material(0.2,0.4,0.6,2));

        v4.setColor(Color.red);
        v5.setColor(Color.blue);
        v6.setColor(Color.green);
        Vertex[] points = new Vertex[3];
        points[0] = v1;
        points[1] = v2;
        points[2] = v3;
        Obj obj = new Obj();
        obj.addtri(
                new Triangle(points)
        );
        points[0] = v4;
        points[1] = v5;
        points[2] = v6;
        obj.addtri(
                new Triangle(points)
        );

        Vector4d campos = new Vector4d(0,0,-2,1);
        Vector4d camright = new Vector4d(1,0,0,1);
        Vector4d camup = new Vector4d(0,1,0,1);

        Camera cam = new Camera(campos,camup,camright,60,1,1,5);

        AmbtLight ambtLight = new AmbtLight(254,67,101);
        PointLight pointLight = new PointLight(255,255,255, new Vector4d(0,0,1,0));

        ViewPoint viewPoint = new ViewPoint(0,0,250,250,0,1);

        Vector4d objPos = new Vector4d(1,0,0,1);
        Vector4d scale = new Vector4d(1,1,1,1);
        double xdeg = 0;
        double ydeg = 90;
        double zdeg = 0;

        Matrix worldMatrix = new Matrix().worldTransform(objPos,scale,xdeg,ydeg,zdeg);
        obj.setWorldmatrix(worldMatrix);


        Scene scene = Scene.getInstance();
        scene.setCamera(cam);
        scene.setAmbtLight(ambtLight);
        scene.setPointLight(pointLight);
        scene.setViewPoint(viewPoint);
        scene.addObj(obj);

        JFrame frame = new JFrame("Scene");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        scene.setBackground(frame);
        scene.draw();



    }
}
