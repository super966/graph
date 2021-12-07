package Vector;

public class Vector4d {
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public Vector4d add(Vector4d v){
        return new Vector4d(v.x + x, v.y +y, v.z + z,v.w + w);
    }

    public Vector4d minus(Vector4d v){
        return new Vector4d(v.x - x, v.y - y, v.z - z,v.w - w);
    }




    public double mul(Vector4d v){
        return v.x * x + v.y * y + v.z * z ;
    }

    public double dot(Vector4d v){
        return v.x * x + v.y * y + v.z * z;
    }

    public Vector4d crossMul(Vector4d v){
        return new Vector4d(y * v.z - z * v.y,
                            z * v.x - x * v.z,
                            x * v.y - y * v.x,
                            w);
    }

    public Vector4d div(double f){
        return new Vector4d(x / f, y / f, z / f, w / f);
    }

//    public Vector4d matrixMul(Matrix m){
//        return new Vector4d();
//    }
}
