package Component;

import MathComponent.Matrix;
import MathComponent.Vector4d;

/**
 * @author LSY
 * @date 2021/12/08 16:10
 **/
public class Camera {
    private Vector4d pos;
    private Vector4d up;
    private Vector4d right;
    private double fovY;
    private double aspect;
    private double zNear;
    private double zFar;

    public Vector4d getPos() {
        return pos;
    }

    public void setPos(Vector4d pos) {
        this.pos = pos;
    }

    public Vector4d getUp() {
        return up;
    }

    public void setUp(Vector4d up) {
        this.up = up;
    }

    public Vector4d getRight() {
        return right;
    }

    public void setRight(Vector4d right) {
        this.right = right;
    }

    public double getFovY() {
        return fovY;
    }

    public void setFovY(double fovY) {
        this.fovY = fovY;
    }

    public double getAspect() {
        return aspect;
    }

    public void setAspect(double aspect) {
        this.aspect = aspect;
    }

    public double getzNear() {
        return zNear;
    }

    public void setzNear(double zNear) {
        this.zNear = zNear;
    }

    public double getzFar() {
        return zFar;
    }

    public void setzFar(double zFar) {
        this.zFar = zFar;
    }

    public Matrix getViewTransform() {
//        viewTransform = new Matrix().viewTrans(pos,up,right);
        return viewTransform;
    }

    public void setViewTransform(Matrix viewTransform) {
        this.viewTransform = viewTransform;
    }

    public Matrix getProjTransform() {
        return projTransform;
    }

    public void setProjTransform(Matrix projTransform) {
        this.projTransform = projTransform;
    }

    private Matrix viewTransform;
    private Matrix projTransform;

    public Camera(Vector4d pos, Vector4d up, Vector4d right, double fovY, double aspect, double zNear, double zFar) {
        this.pos = pos;
        this.up = up;
        this.right = right;
        this.fovY = fovY;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;

        viewTransform = new Matrix().viewTrans(pos,up,right);
        projTransform = new Matrix().projectTransform(fovY, aspect, zNear, zFar);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "pos=" + pos +
                '}';
    }

//    public void lr()


}
