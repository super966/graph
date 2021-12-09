package Component;

import MathComponent.Vector4d;

/**
 * @author LSY
 * @date 2021/12/09 12:54
 **/
public class PointLight {
    private int r;
    private int g;
    private int b;
    private Vector4d dir;

    public Vector4d getDir() {
        return dir;
    }

    public void setDir(Vector4d dir) {
        this.dir = dir;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public PointLight(int r, int g, int b, Vector4d dir) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.dir = dir;
    }
}
