package Component;

import MathComponent.Matrix;
import MathComponent.Vector4d;
import MathComponent.Vertex;

import java.awt.*;

public class Triangle{
    private Vertex[] point = new Vertex[3];


    public Triangle(Vertex[] v) {
        for (int i = 0; i < v.length; i++) {
            point[i] = v[i];
        }
    }

    public Triangle(Triangle t){
        for (int i = 0; i < t.point.length; i++) {
            point[i] = new Vertex(t.point[i]);
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



    public Vertex[] draw(Matrix worldMatrix, Camera c) {
        Scene scene = Scene.getInstance();
        Camera camera = c;
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
//            temp[i].setColor((temp[i]).getColor());
            temp[i].setColor(lightModel(temp[i]));
//
            temp[i].matrixMul(viewTransMatrix);
            temp[i].setZ_deep(temp[i].getPos().getZ());
            temp[i].matrixMul(projTransMatrix);
            temp[i].matrixMul(viewpointMatrix);
        }
        return temp;
    }
}
