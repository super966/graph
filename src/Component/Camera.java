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

//    public void lr()


}
