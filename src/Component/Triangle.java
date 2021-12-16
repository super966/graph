package Component;

import MathComponent.Matrix;
import MathComponent.Vector4d;
import MathComponent.Vertex;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class Triangle{
    private Vertex[] point = new Vertex[3];


    public Triangle(Vertex[] v) {
        for (int i = 0; i < v.length; i++) {
            point[i] = new Vertex(v[i]);
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

        Vector4d L = new Vector4d(pointLight.getDir().reverse());
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

    public void cutf(Triangle t, List<Triangle> l){
        if(t.point[0].getPos().getW() > 0 && t.point[1].getPos().getW() > 0 &&
                t.point[2].getPos().getW() > 0 ){
            l.add(t);
            return;
        }
        if(t.point[0].getPos().getW() < 0 && t.point[1].getPos().getW() < 0 &&
                t.point[2].getPos().getW() < 0 ){
            return;
        }
        Vertex v1 = t.point[0];
        Vertex v2 = t.point[1];
        Vertex v3 = t.point[2];

        if(v1.getPos().getW() > v2.getPos().getW()){
            Vertex temp = v2;
            v2 = v1;
            v1 = temp;
        }
        if(v1.getPos().getW() > v3.getPos().getW()){
            Vertex temp = v3;
            v3 = v1;
            v1 = temp;
        }
        if(v2.getPos().getW() > v3.getPos().getW()){
            Vertex temp = v2;
            v2 = v3;
            v3 = temp;
        }

        if(Math.abs(v3.getPos().getW()-v1.getPos().getW()) < 0.0001f) return;

        Vertex v_0 = new Vertex();
        v_0.getPos().setW(0.000001f);
        v_0.getPos().setX(v1.getPos().getX()+(v3.getPos().getX()-v1.getPos().getX())/(v3.getPos().getW()-v1.getPos().getW())
                *(0.000001f - v1.getPos().getW()));
        v_0.getPos().setY(v1.getPos().getY()+(v3.getPos().getY()-v1.getPos().getY())/(v3.getPos().getW()-v1.getPos().getW())
                *(0.000001f - v1.getPos().getW()));
        v_0.getPos().setZ(v1.getPos().getZ()+(v3.getPos().getZ()-v1.getPos().getZ())/(v3.getPos().getW()-v1.getPos().getW())
                *(0.000001f - v1.getPos().getW()));

        v_0.setNormal(new Vector4d(v1.getNormal()));
        int red = (int) (v1.getColor().getRed() +
                (v3.getColor().getRed()-v1.getColor().getRed())/(v3.getPos().getW()-v1.getPos().getW()) *
                        (0.000001f - v1.getPos().getW()));
        int green = (int) (v1.getColor().getGreen() +
                (v3.getColor().getGreen()-v1.getColor().getGreen())/(v3.getPos().getW()-v1.getPos().getW()) *
                        (0.000001f - v1.getPos().getW()));
        int blue = (int) (v1.getColor().getBlue() +
                (v3.getColor().getBlue()-v1.getColor().getBlue())/(v3.getPos().getW()-v1.getPos().getW()) *
                        (0.000001f - v1.getPos().getW()));

        v_0.setColor(new Color(red,green,blue));

        double s = (0.000001f - v1.getPos().getW())/(v3.getPos().getW()-v1.getPos().getW());
        double z1 = v1.getZ_deep();
        double z3 = v3.getZ_deep();
        double z2 = v2.getZ_deep();
        double z = 0;
        double k = s;

        if(z1 != 0 && z3 != 0) z = 1/z1 + s * (1/z3 - 1/z1);
        if(z != 0) z = 1/z;
        if(z1 != z3) k = (z - z1)/(z3 - z1);
        v_0.setZ_deep(z);

        v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
        v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

        if(v1.getPos().getW() < 0.000001f && v2.getPos().getW() > 0.000001f){
            Vertex v_1 = new Vertex();
            v_1.getPos().setW(0.000001f);
            v_1.getPos().setX(v1.getPos().getX()+(v2.getPos().getX()-v1.getPos().getX())/(v2.getPos().getW()-v1.getPos().getW())
                    *(0.000001f - v1.getPos().getW()));
            v_1.getPos().setY(v1.getPos().getY()+(v2.getPos().getY()-v1.getPos().getY())/(v2.getPos().getW()-v1.getPos().getW())
                    *(0.000001f - v1.getPos().getW()));
            v_1.getPos().setZ(v1.getPos().getZ()+(v2.getPos().getZ()-v1.getPos().getZ())/(v2.getPos().getW()-v1.getPos().getW())
                    *(0.000001f - v1.getPos().getW()));

            v_1.setNormal(v1.getNormal());
            red = (int) (v1.getColor().getRed() +
                    (v2.getColor().getRed()-v1.getColor().getRed())/(v2.getPos().getW()-v1.getPos().getW()) *
                            (0.000001f - v1.getPos().getW()));
            green = (int) (v1.getColor().getGreen() +
                    (v2.getColor().getGreen()-v1.getColor().getGreen())/(v2.getPos().getW()-v1.getPos().getW()) *
                            (0.000001f - v1.getPos().getW()));
            blue = (int) (v1.getColor().getBlue() +
                    (v2.getColor().getBlue()-v1.getColor().getBlue())/(v2.getPos().getW()-v1.getPos().getW()) *
                            (0.000001f - v1.getPos().getW()));

            v_1.setColor(new Color(red,green,blue));

            s = (0.000001f - v1.getPos().getW())/(v2.getPos().getW()-v1.getPos().getW());
            z1 = v1.getZ_deep();

            z = 0;
            k = s;

            if(z1 != 0 && z2 != 0) z = 1/z1 + s * (1/z2 - 1/z1);
            if(z != 0) z = 1/z;
            if(z1 != z2) k = (z - z1)/(z2 - z1);
            v_1.setZ_deep(z);

            v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
            v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
            l.add(new Triangle(new Vertex[]{v_0,v3,v_1}));
            l.add(new Triangle(new Vertex[]{v3,v2,v_1}));
        }else if(v1.getPos().getW() < 0.000001f && v2.getPos().getW() < 0.000001f){
            Vertex v_2 = new Vertex();
            v_2.getPos().setW(0.000001f);
            v_2.getPos().setX(v2.getPos().getX()+(v3.getPos().getX()-v2.getPos().getX())/(v3.getPos().getW()-v2.getPos().getW())
                    *(0.000001f - v2.getPos().getW()));
            v_2.getPos().setY(v2.getPos().getY()+(v3.getPos().getY()-v2.getPos().getY())/(v3.getPos().getW()-v2.getPos().getW())
                    *(0.000001f - v2.getPos().getW()));
            v_2.getPos().setZ(v2.getPos().getZ()+(v3.getPos().getZ()-v2.getPos().getZ())/(v3.getPos().getW()-v2.getPos().getW())
                    *(0.000001f - v2.getPos().getW()));

            v_2.setNormal(new Vector4d(v2.getNormal()));
            red = (int) (v2.getColor().getRed() +
                    (v3.getColor().getRed()-v2.getColor().getRed())/(v3.getPos().getW()-v2.getPos().getW()) *
                            (0.000001f - v2.getPos().getW()));
            green = (int) (v2.getColor().getGreen() +
                    (v3.getColor().getGreen()-v2.getColor().getGreen())/(v3.getPos().getW()-v2.getPos().getW()) *
                            (0.000001f - v2.getPos().getW()));
            blue = (int) (v2.getColor().getBlue() +
                    (v3.getColor().getBlue()-v2.getColor().getBlue())/(v3.getPos().getW()-v2.getPos().getW()) *
                            (0.000001f - v2.getPos().getW()));

            v_2.setColor(new Color(red,green,blue));

            s = (0.000001f - v2.getPos().getW())/(v3.getPos().getW()-v2.getPos().getW());
            z2 = v2.getZ_deep();
            z3 = v3.getZ_deep();
            z = 0;
            k = s;

            if(z3 != 0 && z2 != 0) z = 1/z2 + s * (1/z3 - 1/z2);
            if(z != 0) z = 1/z;
            if(z3 != z2) k = (z - z2)/(z3 - z2);
            v_2.setZ_deep(z);

            v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
            v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
            l.add(new Triangle(new Vertex[]{v_0,v3,v_2}));
        }
    }

    public void line(Vertex v1, Vertex v2, List<Nor> ln){
        byte bv1 = v1.getBpos();
        byte bv2 = v2.getBpos();
        byte temp = (byte) (bv1 ^ bv2);
        int p = 0;
        while(temp > 0){
            if((temp & 1) == 1){
                for (int i = 0; i < Nor.values().length; i++) {
                    if(i == p){
                        ln.add(Nor.values()[i]);
                        break;
                    }
                }
            }
            p++;
            temp >>= 1;
        }
    }

    public void cut(Triangle tri, Nor nor, List<Triangle> lt){

        Vertex v1 = tri.point[0];
        Vertex v2 = tri.point[1];
        Vertex v3 = tri.point[2];

        switch (nor){
            case TOP: {
                if (v1.getPos().getY() < v2.getPos().getY()) {
                    swapVtx(v1, v2);
                }
                if (v1.getPos().getY() < v3.getPos().getY()) {
                    swapVtx(v1, v3);
                }
                if (v2.getPos().getY() < v3.getPos().getY()) {
                    swapVtx(v2, v3);
                }
                if (Math.abs(v3.getPos().getY() - v1.getPos().getY()) < 0.00001f) return;
                Vertex v_0 = new Vertex();
                v_0.getPos().setY(1.0);
                v_0.getPos().setX(v1.getPos().getX() +
                        (v3.getPos().getX() - v1.getPos().getX()) / (v3.getPos().getY() - v1.getPos().getY())
                                * (1.0 - v1.getPos().getY()));
                v_0.getPos().setZ(v1.getPos().getZ() +
                        (v3.getPos().getZ() - v1.getPos().getZ()) / (v3.getPos().getY() - v1.getPos().getY())
                                * (1.0 - v1.getPos().getY()));
                v_0.setNormal(new Vector4d(v1.getNormal()));
                int red = (int) (v1.getColor().getRed() +
                        (v3.getColor().getRed() - v1.getColor().getRed()) / (v3.getPos().getY() - v1.getPos().getY()) *
                                (1.0f - v1.getPos().getY()));
                int green = (int) (v1.getColor().getGreen() +
                        (v3.getColor().getGreen() - v1.getColor().getGreen()) / (v3.getPos().getY() - v1.getPos().getY()) *
                                (1.0f - v1.getPos().getY()));
                int blue = (int) (v1.getColor().getBlue() +
                        (v3.getColor().getBlue() - v1.getColor().getBlue()) / (v3.getPos().getY() - v1.getPos().getY()) *
                                (1.0f - v1.getPos().getY()));

                v_0.setColor(new Color(red, green, blue));

                double s = (1.0f - v1.getPos().getY()) / (v3.getPos().getY() - v1.getPos().getY());
                double z1 = v1.getZ_deep();
                double z3 = v3.getZ_deep();
                double z2 = v2.getZ_deep();
                double z = 0;
                double k = s;

                if (z1 != 0 && z3 != 0) z = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (z != 0) z = 1 / z;
                if (z1 != z3) k = (z - z1) / (z3 - z1);
                v_0.setZ_deep(z);

                v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
                v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

                if (v1.getPos().getY() > 1.0f && v2.getPos().getY() < 1.0f) {
                    Vertex v_1 = new Vertex();
                    v_1.getPos().setY(1.0f);
                    v_1.getPos().setX(v1.getPos().getX() + (v2.getPos().getX() - v1.getPos().getX()) / (v2.getPos().getY() - v1.getPos().getY())
                            * (1.0f - v1.getPos().getY()));
                    v_1.getPos().setZ(v1.getPos().getZ() + (v2.getPos().getZ() - v1.getPos().getZ()) / (v2.getPos().getY() - v1.getPos().getY())
                            * (1.0f - v1.getPos().getY()));

                    v_1.setNormal(new Vector4d(v1.getNormal()));
                    red = (int) (v1.getColor().getRed() +
                            (v2.getColor().getRed() - v1.getColor().getRed()) / (v2.getPos().getY() - v1.getPos().getY()) *
                                    (1.0f - v1.getPos().getY()));
                    green = (int) (v1.getColor().getGreen() +
                            (v2.getColor().getGreen() - v1.getColor().getGreen()) / (v2.getPos().getY() - v1.getPos().getY()) *
                                    (1.0f - v1.getPos().getY()));
                    blue = (int) (v1.getColor().getBlue() +
                            (v2.getColor().getBlue() - v1.getColor().getBlue()) / (v2.getPos().getY() - v1.getPos().getY()) *
                                    (1.0f - v1.getPos().getY()));

                    v_1.setColor(new Color(red, green, blue));

                    s = (1.0f - v1.getPos().getY()) / (v2.getPos().getY() - v1.getPos().getY());
                    z1 = v1.getZ_deep();

                    z = 0;
                    k = s;

                    if (z1 != 0 && z2 != 0) z = 1 / z1 + s * (1 / z2 - 1 / z1);
                    if (z != 0) z = 1 / z;
                    if (z1 != z2) k = (z - z1) / (z2 - z1);
                    v_1.setZ_deep(z);

                    v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
                    v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v3, v_1}));
                    lt.add(new Triangle(new Vertex[]{v3, v2, v_1}));
                } else if (v1.getPos().getY() > 1.0f && v2.getPos().getY() > 1.0f) {
                    Vertex v_2 = new Vertex();
                    v_2.getPos().setY(1.0f);
                    v_2.getPos().setX(v2.getPos().getX() + (v3.getPos().getX() - v2.getPos().getX()) / (v3.getPos().getY() - v2.getPos().getY())
                            * (1.0f - v2.getPos().getY()));
                    v_2.getPos().setZ(v2.getPos().getZ() + (v3.getPos().getZ() - v2.getPos().getZ()) / (v3.getPos().getY() - v2.getPos().getY())
                            * (1.0f - v2.getPos().getY()));

                    v_2.setNormal(v2.getNormal());
                    red = (int) (v2.getColor().getRed() +
                            (v3.getColor().getRed() - v2.getColor().getRed()) / (v3.getPos().getY() - v2.getPos().getY()) *
                                    (1.0f - v2.getPos().getY()));
                    green = (int) (v2.getColor().getGreen() +
                            (v3.getColor().getGreen() - v2.getColor().getGreen()) / (v3.getPos().getY() - v2.getPos().getY()) *
                                    (1.0f - v2.getPos().getY()));
                    blue = (int) (v2.getColor().getBlue() +
                            (v3.getColor().getBlue() - v2.getColor().getBlue()) / (v3.getPos().getY() - v2.getPos().getY()) *
                                    (1.0f - v2.getPos().getY()));

                    v_2.setColor(new Color(red, green, blue));

                    s = (1.0f - v2.getPos().getY()) / (v3.getPos().getY() - v2.getPos().getY());
                    z2 = v2.getZ_deep();
                    z3 = v3.getZ_deep();
                    z = 0;
                    k = s;

                    if (z3 != 0 && z2 != 0) z = 1 / z2 + s * (1 / z3 - 1 / z2);
                    if (z != 0) z = 1 / z;
                    if (z3 != z2) k = (z - z2) / (z3 - z2);
                    v_2.setZ_deep(z);

                    v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
                    v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v3, v_2}));
                }
            }
            break;
            case DOWN:{
                if (v1.getPos().getY() < v2.getPos().getY()) {
                    swapVtx(v1, v2);
                }
                if (v1.getPos().getY() < v3.getPos().getY()) {
                    swapVtx(v1, v3);
                }
                if (v2.getPos().getY() < v3.getPos().getY()) {
                    swapVtx(v2, v3);
                }
                if (Math.abs(v3.getPos().getY() - v1.getPos().getY()) < 0.00001f) return;
                Vertex v_0 = new Vertex();
                v_0.getPos().setY(-1.0);
                v_0.getPos().setX(v1.getPos().getX() +
                        (v3.getPos().getX() - v1.getPos().getX()) / (v3.getPos().getY() - v1.getPos().getY())
                                * (-1.0 - v1.getPos().getY()));
                v_0.getPos().setZ(v1.getPos().getZ() +
                        (v3.getPos().getZ() - v1.getPos().getZ()) / (v3.getPos().getY() - v1.getPos().getY())
                                * (-1.0 - v1.getPos().getY()));
                v_0.setNormal(new Vector4d(v1.getNormal()));
                int red = (int) (v1.getColor().getRed() +
                        (v3.getColor().getRed() - v1.getColor().getRed()) / (v3.getPos().getY() - v1.getPos().getY()) *
                                (-1.0f - v1.getPos().getY()));
                int green = (int) (v1.getColor().getGreen() +
                        (v3.getColor().getGreen() - v1.getColor().getGreen()) / (v3.getPos().getY() - v1.getPos().getY()) *
                                (-1.0f - v1.getPos().getY()));
                int blue = (int) (v1.getColor().getBlue() +
                        (v3.getColor().getBlue() - v1.getColor().getBlue()) / (v3.getPos().getY() - v1.getPos().getY()) *
                                (-1.0f - v1.getPos().getY()));

                v_0.setColor(new Color(red, green, blue));

                double s = (-1.0f - v1.getPos().getY()) / (v3.getPos().getY() - v1.getPos().getY());
                double z1 = v1.getZ_deep();
                double z3 = v3.getZ_deep();
                double z2 = v2.getZ_deep();
                double z = 0;
                double k = s;

                if (z1 != 0 && z3 != 0) z = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (z != 0) z = 1 / z;
                if (z1 != z3) k = (z - z1) / (z3 - z1);
                v_0.setZ_deep(z);

                v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
                v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

                if (v1.getPos().getY() > -1.0f && v2.getPos().getY() < -1.0f) {
                    Vertex v_1 = new Vertex();
                    v_1.getPos().setY(-1.0f);
                    v_1.getPos().setX(v1.getPos().getX() + (v2.getPos().getX() - v1.getPos().getX()) / (v2.getPos().getY() - v1.getPos().getY())
                            * (-1.0f - v1.getPos().getY()));
                    v_1.getPos().setZ(v1.getPos().getZ() + (v2.getPos().getZ() - v1.getPos().getZ()) / (v2.getPos().getY() - v1.getPos().getY())
                            * (-1.0f - v1.getPos().getY()));

                    v_1.setNormal(new Vector4d(v1.getNormal()));
                    red = (int) (v1.getColor().getRed() +
                            (v2.getColor().getRed() - v1.getColor().getRed()) / (v2.getPos().getY() - v1.getPos().getY()) *
                                    (-1.0f - v1.getPos().getY()));
                    green = (int) (v1.getColor().getGreen() +
                            (v2.getColor().getGreen() - v1.getColor().getGreen()) / (v2.getPos().getY() - v1.getPos().getY()) *
                                    (-1.0f - v1.getPos().getY()));
                    blue = (int) (v1.getColor().getBlue() +
                            (v2.getColor().getBlue() - v1.getColor().getBlue()) / (v2.getPos().getY() - v1.getPos().getY()) *
                                    (-1.0f - v1.getPos().getY()));

                    v_1.setColor(new Color(red, green, blue));

                    s = (-1.0f - v1.getPos().getY()) / (v2.getPos().getY() - v1.getPos().getY());
                    z1 = v1.getZ_deep();

                    z = 0;
                    k = s;

                    if (z1 != 0 && z2 != 0) z = 1 / z1 + s * (1 / z2 - 1 / z1);
                    if (z != 0) z = 1 / z;
                    if (z1 != z2) k = (z - z1) / (z2 - z1);
                    v_1.setZ_deep(z);

                    v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
                    v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_1, v1}));
                } else if (v1.getPos().getY() > -1.0f && v2.getPos().getY() > -1.0f) {
                    Vertex v_2 = new Vertex();
                    v_2.getPos().setY(-1.0f);
                    v_2.getPos().setX(v2.getPos().getX() + (v3.getPos().getX() - v2.getPos().getX()) / (v3.getPos().getY() - v2.getPos().getY())
                            * (-1.0f - v2.getPos().getY()));
                    v_2.getPos().setZ(v2.getPos().getZ() + (v3.getPos().getZ() - v2.getPos().getZ()) / (v3.getPos().getY() - v2.getPos().getY())
                            * (-1.0f - v2.getPos().getY()));

                    v_2.setNormal(v2.getNormal());
                    red = (int) (v2.getColor().getRed() +
                            (v3.getColor().getRed() - v2.getColor().getRed()) / (v3.getPos().getY() - v2.getPos().getY()) *
                                    (-1.0f - v2.getPos().getY()));
                    green = (int) (v2.getColor().getGreen() +
                            (v3.getColor().getGreen() - v2.getColor().getGreen()) / (v3.getPos().getY() - v2.getPos().getY()) *
                                    (-1.0f - v2.getPos().getY()));
                    blue = (int) (v2.getColor().getBlue() +
                            (v3.getColor().getBlue() - v2.getColor().getBlue()) / (v3.getPos().getY() - v2.getPos().getY()) *
                                    (-1.0f - v2.getPos().getY()));

                    v_2.setColor(new Color(red, green, blue));

                    s = (-1.0f - v2.getPos().getY()) / (v3.getPos().getY() - v2.getPos().getY());
                    z2 = v2.getZ_deep();
                    z3 = v3.getZ_deep();
                    z = 0;
                    k = s;

                    if (z3 != 0 && z2 != 0) z = 1 / z2 + s * (1 / z3 - 1 / z2);
                    if (z != 0) z = 1 / z;
                    if (z3 != z2) k = (z - z2) / (z3 - z2);
                    v_2.setZ_deep(z);

                    v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
                    v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_2, v2}));
                    lt.add(new Triangle(new Vertex[]{v_0, v2, v1}));
                }
            }
            break;
            case LEFT:{
                if (v1.getPos().getX() < v2.getPos().getX()) {
                    swapVtx(v1, v2);
                }
                if (v1.getPos().getX() < v3.getPos().getX()) {
                    swapVtx(v1, v3);
                }
                if (v2.getPos().getX() < v3.getPos().getX()) {
                    swapVtx(v2, v3);
                }
                if (Math.abs(v3.getPos().getX() - v1.getPos().getX()) < 0.00001f) return;
                Vertex v_0 = new Vertex();
                v_0.getPos().setX(-1.0);
                v_0.getPos().setY(v1.getPos().getY() +
                        (v3.getPos().getY() - v1.getPos().getY()) / (v3.getPos().getX() - v1.getPos().getX())
                                * (-1.0 - v1.getPos().getX()));
                v_0.getPos().setZ(v1.getPos().getZ() +
                        (v3.getPos().getZ() - v1.getPos().getZ()) / (v3.getPos().getX() - v1.getPos().getX())
                                * (-1.0 - v1.getPos().getX()));
                v_0.setNormal(new Vector4d(v1.getNormal()));
                int red = (int) (v1.getColor().getRed() +
                        (v3.getColor().getRed() - v1.getColor().getRed()) / (v3.getPos().getX() - v1.getPos().getX()) *
                                (-1.0f - v1.getPos().getX()));
                int green = (int) (v1.getColor().getGreen() +
                        (v3.getColor().getGreen() - v1.getColor().getGreen()) / (v3.getPos().getX() - v1.getPos().getX()) *
                                (-1.0f - v1.getPos().getX()));
                int blue = (int) (v1.getColor().getBlue() +
                        (v3.getColor().getBlue() - v1.getColor().getBlue()) / (v3.getPos().getX() - v1.getPos().getX()) *
                                (-1.0f - v1.getPos().getX()));

                v_0.setColor(new Color(red, green, blue));

                double s = (-1.0f - v1.getPos().getX()) / (v3.getPos().getX() - v1.getPos().getX());
                double z1 = v1.getZ_deep();
                double z3 = v3.getZ_deep();
                double z2 = v2.getZ_deep();
                double z = 0;
                double k = s;

                if (z1 != 0 && z3 != 0) z = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (z != 0) z = 1 / z;
                if (z1 != z3) k = (z - z1) / (z3 - z1);
                v_0.setZ_deep(z);

                v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
                v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

                if (v1.getPos().getX() < -1.0f && v2.getPos().getX() > -1.0f) {
                    Vertex v_1 = new Vertex();
                    v_1.getPos().setX(-1.0f);
                    v_1.getPos().setY(v1.getPos().getY() + (v2.getPos().getY() - v1.getPos().getY()) / (v2.getPos().getX() - v1.getPos().getX())
                            * (-1.0f - v1.getPos().getX()));
                    v_1.getPos().setZ(v1.getPos().getZ() + (v2.getPos().getZ() - v1.getPos().getZ()) / (v2.getPos().getX() - v1.getPos().getX())
                            * (-1.0f - v1.getPos().getX()));

                    v_1.setNormal(new Vector4d(v1.getNormal()));
                    red = (int) (v1.getColor().getRed() +
                            (v2.getColor().getRed() - v1.getColor().getRed()) / (v2.getPos().getX() - v1.getPos().getX()) *
                                    (-1.0f - v1.getPos().getX()));
                    green = (int) (v1.getColor().getGreen() +
                            (v2.getColor().getGreen() - v1.getColor().getGreen()) / (v2.getPos().getX() - v1.getPos().getX()) *
                                    (-1.0f - v1.getPos().getX()));
                    blue = (int) (v1.getColor().getBlue() +
                            (v2.getColor().getBlue() - v1.getColor().getBlue()) / (v2.getPos().getX() - v1.getPos().getX()) *
                                    (-1.0f - v1.getPos().getX()));

                    v_1.setColor(new Color(red, green, blue));

                    s = (-1.0f - v1.getPos().getX()) / (v2.getPos().getX() - v1.getPos().getX());
                    z1 = v1.getZ_deep();

                    z = 0;
                    k = s;

                    if (z1 != 0 && z2 != 0) z = 1 / z1 + s * (1 / z2 - 1 / z1);
                    if (z != 0) z = 1 / z;
                    if (z1 != z2) k = (z - z1) / (z2 - z1);
                    v_1.setZ_deep(z);

                    v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
                    v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_1, v2, v3}));
                    lt.add(new Triangle(new Vertex[]{v_1, v3, v_0}));
                } else if (v1.getPos().getX() < -1.0f && v2.getPos().getX() < -1.0f) {
                    Vertex v_2 = new Vertex();
                    v_2.getPos().setX(-1.0f);
                    v_2.getPos().setY(v2.getPos().getY() + (v3.getPos().getY() - v2.getPos().getY()) / (v3.getPos().getX() - v2.getPos().getX())
                            * (-1.0f - v2.getPos().getX()));
                    v_2.getPos().setZ(v2.getPos().getZ() + (v3.getPos().getZ() - v2.getPos().getZ()) / (v3.getPos().getX() - v2.getPos().getX())
                            * (-1.0f - v2.getPos().getX()));

                    v_2.setNormal(v2.getNormal());
                    red = (int) (v2.getColor().getRed() +
                            (v3.getColor().getRed() - v2.getColor().getRed()) / (v3.getPos().getX() - v2.getPos().getX()) *
                                    (-1.0f - v2.getPos().getX()));
                    green = (int) (v2.getColor().getGreen() +
                            (v3.getColor().getGreen() - v2.getColor().getGreen()) / (v3.getPos().getX() - v2.getPos().getX()) *
                                    (-1.0f - v2.getPos().getX()));
                    blue = (int) (v2.getColor().getBlue() +
                            (v3.getColor().getBlue() - v2.getColor().getBlue()) / (v3.getPos().getX() - v2.getPos().getX()) *
                                    (-1.0f - v2.getPos().getX()));

                    v_2.setColor(new Color(red, green, blue));

                    s = (-1.0f - v2.getPos().getX()) / (v3.getPos().getX() - v2.getPos().getX());
                    z2 = v2.getZ_deep();
                    z3 = v3.getZ_deep();
                    z = 0;
                    k = s;

                    if (z3 != 0 && z2 != 0) z = 1 / z2 + s * (1 / z3 - 1 / z2);
                    if (z != 0) z = 1 / z;
                    if (z3 != z2) k = (z - z2) / (z3 - z2);
                    v_2.setZ_deep(z);

                    v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
                    v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_2, v3}));
                }
            }
            break;
            case RIGHT:{
                if (v1.getPos().getX() < v2.getPos().getX()) {
                    swapVtx(v1, v2);
                }
                if (v1.getPos().getX() < v3.getPos().getX()) {
                    swapVtx(v1, v3);
                }
                if (v2.getPos().getX() < v3.getPos().getX()) {
                    swapVtx(v2, v3);
                }
                if (Math.abs(v3.getPos().getX() - v1.getPos().getX()) < 0.00001f) return;
                Vertex v_0 = new Vertex();
                v_0.getPos().setX(1.0);
                v_0.getPos().setY(v1.getPos().getY() +
                        (v3.getPos().getY() - v1.getPos().getY()) / (v3.getPos().getX() - v1.getPos().getX())
                                * (1.0 - v1.getPos().getX()));
                v_0.getPos().setZ(v1.getPos().getZ() +
                        (v3.getPos().getZ() - v1.getPos().getZ()) / (v3.getPos().getX() - v1.getPos().getX())
                                * (1.0 - v1.getPos().getX()));
                v_0.setNormal(new Vector4d(v1.getNormal()));
                int red = (int) (v1.getColor().getRed() +
                        (v3.getColor().getRed() - v1.getColor().getRed()) / (v3.getPos().getX() - v1.getPos().getX()) *
                                (1.0f - v1.getPos().getX()));
                int green = (int) (v1.getColor().getGreen() +
                        (v3.getColor().getGreen() - v1.getColor().getGreen()) / (v3.getPos().getX() - v1.getPos().getX()) *
                                (1.0f - v1.getPos().getX()));
                int blue = (int) (v1.getColor().getBlue() +
                        (v3.getColor().getBlue() - v1.getColor().getBlue()) / (v3.getPos().getX() - v1.getPos().getX()) *
                                (1.0f - v1.getPos().getX()));

                v_0.setColor(new Color(red, green, blue));

                double s = (1.0f - v1.getPos().getX()) / (v3.getPos().getX() - v1.getPos().getX());
                double z1 = v1.getZ_deep();
                double z3 = v3.getZ_deep();
                double z2 = v2.getZ_deep();
                double z = 0;
                double k = s;

                if (z1 != 0 && z3 != 0) z = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (z != 0) z = 1 / z;
                if (z1 != z3) k = (z - z1) / (z3 - z1);
                v_0.setZ_deep(z);

                v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
                v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

                if (v1.getPos().getX() < 1.0f && v2.getPos().getX() > 1.0f) {
                    Vertex v_1 = new Vertex();
                    v_1.getPos().setX(1.0f);
                    v_1.getPos().setY(v1.getPos().getY() + (v2.getPos().getY() - v1.getPos().getY()) / (v2.getPos().getX() - v1.getPos().getX())
                            * (1.0f - v1.getPos().getX()));
                    v_1.getPos().setZ(v1.getPos().getZ() + (v2.getPos().getZ() - v1.getPos().getZ()) / (v2.getPos().getX() - v1.getPos().getX())
                            * (1.0f - v1.getPos().getX()));

                    v_1.setNormal(new Vector4d(v1.getNormal()));
                    red = (int) (v1.getColor().getRed() +
                            (v2.getColor().getRed() - v1.getColor().getRed()) / (v2.getPos().getX() - v1.getPos().getX()) *
                                    (1.0f - v1.getPos().getX()));
                    green = (int) (v1.getColor().getGreen() +
                            (v2.getColor().getGreen() - v1.getColor().getGreen()) / (v2.getPos().getX() - v1.getPos().getX()) *
                                    (1.0f - v1.getPos().getX()));
                    blue = (int) (v1.getColor().getBlue() +
                            (v2.getColor().getBlue() - v1.getColor().getBlue()) / (v2.getPos().getX() - v1.getPos().getX()) *
                                    (1.0f - v1.getPos().getX()));

                    v_1.setColor(new Color(red, green, blue));

                    s = (1.0f - v1.getPos().getX()) / (v2.getPos().getX() - v1.getPos().getX());
                    z1 = v1.getZ_deep();

                    z = 0;
                    k = s;

                    if (z1 != 0 && z2 != 0) z = 1 / z1 + s * (1 / z2 - 1 / z1);
                    if (z != 0) z = 1 / z;
                    if (z1 != z2) k = (z - z1) / (z2 - z1);
                    v_1.setZ_deep(z);

                    v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
                    v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_1, v1}));
                } else if (v1.getPos().getX() < 1.0f && v2.getPos().getX() < 1.0f) {
                    Vertex v_2 = new Vertex();
                    v_2.getPos().setX(1.0f);
                    v_2.getPos().setY(v2.getPos().getY() + (v3.getPos().getY() - v2.getPos().getY()) / (v3.getPos().getX() - v2.getPos().getX())
                            * (1.0f - v2.getPos().getX()));
                    v_2.getPos().setZ(v2.getPos().getZ() + (v3.getPos().getZ() - v2.getPos().getZ()) / (v3.getPos().getX() - v2.getPos().getX())
                            * (1.0f - v2.getPos().getX()));

                    v_2.setNormal(v2.getNormal());
                    red = (int) (v2.getColor().getRed() +
                            (v3.getColor().getRed() - v2.getColor().getRed()) / (v3.getPos().getX() - v2.getPos().getX()) *
                                    (1.0f - v2.getPos().getX()));
                    green = (int) (v2.getColor().getGreen() +
                            (v3.getColor().getGreen() - v2.getColor().getGreen()) / (v3.getPos().getX() - v2.getPos().getX()) *
                                    (1.0f - v2.getPos().getX()));
                    blue = (int) (v2.getColor().getBlue() +
                            (v3.getColor().getBlue() - v2.getColor().getBlue()) / (v3.getPos().getX() - v2.getPos().getX()) *
                                    (1.0f - v2.getPos().getX()));

                    v_2.setColor(new Color(red, green, blue));

                    s = (1.0f - v2.getPos().getX()) / (v3.getPos().getX() - v2.getPos().getX());
                    z2 = v2.getZ_deep();
                    z3 = v3.getZ_deep();
                    z = 0;
                    k = s;

                    if (z3 != 0 && z2 != 0) z = 1 / z2 + s * (1 / z3 - 1 / z2);
                    if (z != 0) z = 1 / z;
                    if (z3 != z2) k = (z - z2) / (z3 - z2);
                    v_2.setZ_deep(z);

                    v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
                    v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_2, v2}));
                    lt.add(new Triangle(new Vertex[]{v_0, v2, v1}));
                }
            }
            break;
            case NEAR:{
                if (v1.getPos().getZ() < v2.getPos().getZ()) {
                    swapVtx(v1, v2);
                }
                if (v1.getPos().getZ() < v3.getPos().getZ()) {
                    swapVtx(v1, v3);
                }
                if (v2.getPos().getZ() < v3.getPos().getZ()) {
                    swapVtx(v2, v3);
                }
                if (Math.abs(v3.getPos().getZ() - v1.getPos().getZ()) < 0.00001f) return;
                Vertex v_0 = new Vertex();
                v_0.getPos().setZ(-1.0);
                v_0.getPos().setY(v1.getPos().getY() +
                        (v3.getPos().getY() - v1.getPos().getY()) / (v3.getPos().getZ() - v1.getPos().getZ())
                                * (-1.0 - v1.getPos().getZ()));
                v_0.getPos().setX(v1.getPos().getX() +
                        (v3.getPos().getX() - v1.getPos().getX()) / (v3.getPos().getZ() - v1.getPos().getZ())
                                * (-1.0 - v1.getPos().getZ()));
                v_0.setNormal(new Vector4d(v1.getNormal()));
                int red = (int) (v1.getColor().getRed() +
                        (v3.getColor().getRed() - v1.getColor().getRed()) / (v3.getPos().getZ() - v1.getPos().getZ()) *
                                (-1.0f - v1.getPos().getZ()));
                int green = (int) (v1.getColor().getGreen() +
                        (v3.getColor().getGreen() - v1.getColor().getGreen()) / (v3.getPos().getZ() - v1.getPos().getZ()) *
                                (-1.0f - v1.getPos().getZ()));
                int blue = (int) (v1.getColor().getBlue() +
                        (v3.getColor().getBlue() - v1.getColor().getBlue()) / (v3.getPos().getZ() - v1.getPos().getZ()) *
                                (-1.0f - v1.getPos().getZ()));

                v_0.setColor(new Color(red, green, blue));

                double s = (-1.0f - v1.getPos().getZ()) / (v3.getPos().getZ() - v1.getPos().getZ());
                double z1 = v1.getZ_deep();
                double z3 = v3.getZ_deep();
                double z2 = v2.getZ_deep();
                double z = 0;
                double k = s;

                if (z1 != 0 && z3 != 0) z = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (z != 0) z = 1 / z;
                if (z1 != z3) k = (z - z1) / (z3 - z1);
                v_0.setZ_deep(z);

                v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
                v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

                if (v1.getPos().getZ() < -1.0f && v2.getPos().getZ() > -1.0f) {
                    Vertex v_1 = new Vertex();
                    v_1.getPos().setZ(-1.0f);
                    v_1.getPos().setY(v1.getPos().getY() + (v2.getPos().getY() - v1.getPos().getY()) / (v2.getPos().getZ() - v1.getPos().getZ())
                            * (-1.0f - v1.getPos().getZ()));
                    v_1.getPos().setX(v1.getPos().getX() + (v2.getPos().getX() - v1.getPos().getX()) / (v2.getPos().getZ() - v1.getPos().getZ())
                            * (-1.0f - v1.getPos().getZ()));

                    v_1.setNormal(new Vector4d(v1.getNormal()));
                    red = (int) (v1.getColor().getRed() +
                            (v2.getColor().getRed() - v1.getColor().getRed()) / (v2.getPos().getZ() - v1.getPos().getZ()) *
                                    (-1.0f - v1.getPos().getZ()));
                    green = (int) (v1.getColor().getGreen() +
                            (v2.getColor().getGreen() - v1.getColor().getGreen()) / (v2.getPos().getZ() - v1.getPos().getZ()) *
                                    (-1.0f - v1.getPos().getZ()));
                    blue = (int) (v1.getColor().getBlue() +
                            (v2.getColor().getBlue() - v1.getColor().getBlue()) / (v2.getPos().getZ() - v1.getPos().getZ()) *
                                    (-1.0f - v1.getPos().getZ()));

                    v_1.setColor(new Color(red, green, blue));

                    s = (-1.0f - v1.getPos().getZ()) / (v2.getPos().getZ() - v1.getPos().getZ());
                    z1 = v1.getZ_deep();

                    z = 0;
                    k = s;

                    if (z1 != 0 && z2 != 0) z = 1 / z1 + s * (1 / z2 - 1 / z1);
                    if (z != 0) z = 1 / z;
                    if (z1 != z2) k = (z - z1) / (z2 - z1);
                    v_1.setZ_deep(z);

                    v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
                    v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_1, v2, v3}));
                    lt.add(new Triangle(new Vertex[]{v_1, v3, v_0}));
                } else if (v1.getPos().getZ() < -1.0f && v2.getPos().getZ() < -1.0f) {
                    Vertex v_2 = new Vertex();
                    v_2.getPos().setZ(-1.0f);
                    v_2.getPos().setY(v2.getPos().getY() + (v3.getPos().getY() - v2.getPos().getY()) / (v3.getPos().getZ() - v2.getPos().getZ())
                            * (-1.0f - v2.getPos().getZ()));
                    v_2.getPos().setX(v2.getPos().getX() + (v3.getPos().getX() - v2.getPos().getX()) / (v3.getPos().getZ() - v2.getPos().getZ())
                            * (-1.0f - v2.getPos().getZ()));

                    v_2.setNormal(v2.getNormal());
                    red = (int) (v2.getColor().getRed() +
                            (v3.getColor().getRed() - v2.getColor().getRed()) / (v3.getPos().getZ() - v2.getPos().getZ()) *
                                    (-1.0f - v2.getPos().getZ()));
                    green = (int) (v2.getColor().getGreen() +
                            (v3.getColor().getGreen() - v2.getColor().getGreen()) / (v3.getPos().getZ() - v2.getPos().getZ()) *
                                    (-1.0f - v2.getPos().getZ()));
                    blue = (int) (v2.getColor().getBlue() +
                            (v3.getColor().getBlue() - v2.getColor().getBlue()) / (v3.getPos().getZ() - v2.getPos().getZ()) *
                                    (-1.0f - v2.getPos().getZ()));

                    v_2.setColor(new Color(red, green, blue));

                    s = (-1.0f - v2.getPos().getX()) / (v3.getPos().getZ() - v2.getPos().getZ());
                    z2 = v2.getZ_deep();
                    z3 = v3.getZ_deep();
                    z = 0;
                    k = s;

                    if (z3 != 0 && z2 != 0) z = 1 / z2 + s * (1 / z3 - 1 / z2);
                    if (z != 0) z = 1 / z;
                    if (z3 != z2) k = (z - z2) / (z3 - z2);
                    v_2.setZ_deep(z);

                    v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
                    v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_2, v3}));
                }
            }
            break;
            case FAR:{
                if (v1.getPos().getZ() < v2.getPos().getZ()) {
                    swapVtx(v1, v2);
                }
                if (v1.getPos().getZ() < v3.getPos().getZ()) {
                    swapVtx(v1, v3);
                }
                if (v2.getPos().getZ() < v3.getPos().getZ()) {
                    swapVtx(v2, v3);
                }
                if (Math.abs(v3.getPos().getZ() - v1.getPos().getZ()) < 0.00001f) return;
                Vertex v_0 = new Vertex();
                v_0.getPos().setZ(1.0);
                v_0.getPos().setY(v1.getPos().getY() +
                        (v3.getPos().getY() - v1.getPos().getY()) / (v3.getPos().getZ() - v1.getPos().getZ())
                                * (1.0 - v1.getPos().getZ()));
                v_0.getPos().setX(v1.getPos().getX() +
                        (v3.getPos().getX() - v1.getPos().getX()) / (v3.getPos().getZ() - v1.getPos().getZ())
                                * (1.0 - v1.getPos().getZ()));
                v_0.setNormal(new Vector4d(v1.getNormal()));
                int red = (int) (v1.getColor().getRed() +
                        (v3.getColor().getRed() - v1.getColor().getRed()) / (v3.getPos().getZ() - v1.getPos().getZ()) *
                                (1.0f - v1.getPos().getZ()));
                int green = (int) (v1.getColor().getGreen() +
                        (v3.getColor().getGreen() - v1.getColor().getGreen()) / (v3.getPos().getZ() - v1.getPos().getZ()) *
                                (1.0f - v1.getPos().getZ()));
                int blue = (int) (v1.getColor().getBlue() +
                        (v3.getColor().getBlue() - v1.getColor().getBlue()) / (v3.getPos().getZ() - v1.getPos().getZ()) *
                                (1.0f - v1.getPos().getZ()));

                v_0.setColor(new Color(red, green, blue));

                double s = (1.0f - v1.getPos().getZ()) / (v3.getPos().getZ() - v1.getPos().getZ());
                double z1 = v1.getZ_deep();
                double z3 = v3.getZ_deep();
                double z2 = v2.getZ_deep();
                double z = 0;
                double k = s;

                if (z1 != 0 && z3 != 0) z = 1 / z1 + s * (1 / z3 - 1 / z1);
                if (z != 0) z = 1 / z;
                if (z1 != z3) k = (z - z1) / (z3 - z1);
                v_0.setZ_deep(z);

                v_0.getTexture().setU(v1.getTexture().getU() + k * (v3.getTexture().getU() - v1.getTexture().getU()));
                v_0.getTexture().setV(v1.getTexture().getV() + k * (v3.getTexture().getV() - v1.getTexture().getV()));

                if (v1.getPos().getZ() < 1.0f && v2.getPos().getZ() > 1.0f) {
                    Vertex v_1 = new Vertex();
                    v_1.getPos().setZ(1.0f);
                    v_1.getPos().setY(v1.getPos().getY() + (v2.getPos().getY() - v1.getPos().getY()) / (v2.getPos().getZ() - v1.getPos().getZ())
                            * (1.0f - v1.getPos().getZ()));
                    v_1.getPos().setX(v1.getPos().getX() + (v2.getPos().getX() - v1.getPos().getX()) / (v2.getPos().getZ() - v1.getPos().getZ())
                            * (1.0f - v1.getPos().getZ()));

                    v_1.setNormal(new Vector4d(v1.getNormal()));
                    red = (int) (v1.getColor().getRed() +
                            (v2.getColor().getRed() - v1.getColor().getRed()) / (v2.getPos().getZ() - v1.getPos().getZ()) *
                                    (1.0f - v1.getPos().getZ()));
                    green = (int) (v1.getColor().getGreen() +
                            (v2.getColor().getGreen() - v1.getColor().getGreen()) / (v2.getPos().getZ() - v1.getPos().getZ()) *
                                    (1.0f - v1.getPos().getZ()));
                    blue = (int) (v1.getColor().getBlue() +
                            (v2.getColor().getBlue() - v1.getColor().getBlue()) / (v2.getPos().getZ() - v1.getPos().getZ()) *
                                    (1.0f - v1.getPos().getZ()));

                    v_1.setColor(new Color(red, green, blue));

                    s = (1.0f - v1.getPos().getZ()) / (v2.getPos().getZ() - v1.getPos().getZ());
                    z1 = v1.getZ_deep();

                    z = 0;
                    k = s;

                    if (z1 != 0 && z2 != 0) z = 1 / z1 + s * (1 / z2 - 1 / z1);
                    if (z != 0) z = 1 / z;
                    if (z1 != z2) k = (z - z1) / (z2 - z1);
                    v_1.setZ_deep(z);

                    v_1.getTexture().setU(v1.getTexture().getU() + k * (v2.getTexture().getU() - v1.getTexture().getU()));
                    v_1.getTexture().setV(v1.getTexture().getV() + k * (v2.getTexture().getV() - v1.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_1, v1}));
                } else if (v1.getPos().getZ() < 1.0f && v2.getPos().getZ() < 1.0f) {
                    Vertex v_2 = new Vertex();
                    v_2.getPos().setZ(1.0f);
                    v_2.getPos().setY(v2.getPos().getY() + (v3.getPos().getY() - v2.getPos().getY()) / (v3.getPos().getZ() - v2.getPos().getZ())
                            * (1.0f - v2.getPos().getZ()));
                    v_2.getPos().setX(v2.getPos().getX() + (v3.getPos().getX() - v2.getPos().getX()) / (v3.getPos().getZ() - v2.getPos().getZ())
                            * (1.0f - v2.getPos().getZ()));

                    v_2.setNormal(v2.getNormal());
                    red = (int) (v2.getColor().getRed() +
                            (v3.getColor().getRed() - v2.getColor().getRed()) / (v3.getPos().getZ() - v2.getPos().getZ()) *
                                    (1.0f - v2.getPos().getZ()));
                    green = (int) (v2.getColor().getGreen() +
                            (v3.getColor().getGreen() - v2.getColor().getGreen()) / (v3.getPos().getZ() - v2.getPos().getZ()) *
                                    (1.0f - v2.getPos().getZ()));
                    blue = (int) (v2.getColor().getBlue() +
                            (v3.getColor().getBlue() - v2.getColor().getBlue()) / (v3.getPos().getZ() - v2.getPos().getZ()) *
                                    (1.0f - v2.getPos().getZ()));

                    v_2.setColor(new Color(red, green, blue));

                    s = (1.0f - v2.getPos().getX()) / (v3.getPos().getZ() - v2.getPos().getZ());
                    z2 = v2.getZ_deep();
                    z3 = v3.getZ_deep();
                    z = 0;
                    k = s;

                    if (z3 != 0 && z2 != 0) z = 1 / z2 + s * (1 / z3 - 1 / z2);
                    if (z != 0) z = 1 / z;
                    if (z3 != z2) k = (z - z2) / (z3 - z2);
                    v_2.setZ_deep(z);

                    v_2.getTexture().setU(v2.getTexture().getU() + k * (v3.getTexture().getU() - v2.getTexture().getU()));
                    v_2.getTexture().setV(v2.getTexture().getV() + k * (v3.getTexture().getV() - v2.getTexture().getV()));
                    lt.add(new Triangle(new Vertex[]{v_0, v_2, v2}));
                    lt.add(new Triangle(new Vertex[]{v_0, v2, v3}));
                }
            }
            break;

        }
    }

    public void swapVtx(Vertex v1, Vertex v2){
        Vertex temp = v1;
        v1 = v2;
        v2 = temp;
    }

    public void claNor(Triangle tri, Queue<Nor> nor){
        List<Nor> ln = new ArrayList<Nor>();
        line(tri.point[0],tri.point[1],ln);
        line(tri.point[0],tri.point[2],ln);
        line(tri.point[1],tri.point[2],ln);
        int[] a = new int[6];
        for (int i = 0; i < ln.size(); i++) {
            a[ln.get(i).ordinal()] = 1;
        }
        for (int i = 0; i < 6; i++) {
            if (a[i] == 1) {
                nor.add(Nor.values()[i]);
            }
        }
    }

    public void cuts(Triangle tri, List<Triangle> lt){
        if((tri.point[0].getBpos() & tri.point[1].getBpos() & tri.point[2].getBpos()) == 1) return;
        else if((tri.point[0].getBpos() & tri.point[1].getBpos() & tri.point[2].getBpos()) == 0){
            lt.add(tri);
        }else{
            Queue<Nor> nor = new ArrayDeque<Nor>();
            claNor(tri, nor);

            List<Triangle> tempTri = new ArrayList<>();
            tempTri.add(tri);
            while(!nor.isEmpty()){
                Nor tempnor = nor.poll();
                for (Triangle triangle : tempTri) {
                    Triangle curTri = new Triangle(triangle);
                    cut(curTri, tempnor, lt);
                }
                if(nor.isEmpty()) break;
                tempTri.clear();
                for (Triangle triangle : lt) {
                    tempTri.add(triangle);
                }
                lt.clear();
            }

        }
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
        }

        Triangle teTri = new Triangle(temp);
        List<Triangle> lt = new ArrayList<>();
        cutf(teTri,lt);


        for (Triangle triangle : lt) {
            for (Vertex vertex : triangle.point) {
                vertex.divideW();
            }

            Vector4d camDir = new Vector4d(0,0,1,0);
            Vector4d N1 = triangle.point[1].getPos().minus(triangle.point[0].getPos());
            Vector4d N2 = triangle.point[2].getPos().minus(triangle.point[0].getPos());
            Vector4d frontNormal = N1.crossMul(N2).Normalize();
            if(frontNormal.dot(camDir) < 0) continue;

            for (Vertex vertex : triangle.point) {
                calpos(vertex);
            }
            List<Triangle> picTri = new ArrayList<>();
            cuts(triangle, picTri);
        }

        return temp;
    }

    private void calpos(Vertex vertex) {
        byte postem = 0;
        if(vertex.getPos().getX() < -1.0f) postem |= 1;
        if(vertex.getPos().getX() > 1.0f) postem |= 2;
        if(vertex.getPos().getY() < -1.0f) postem |= 4;
        if(vertex.getPos().getY() > 1.0f) postem |= 8;
        if(vertex.getPos().getZ() < -1.0f) postem |= 16;
        if(vertex.getPos().getZ() > 1.0f) postem |= 32;
        vertex.setBpos(postem);
    }
}
