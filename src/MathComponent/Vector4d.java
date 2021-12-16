package MathComponent;

public class Vector4d {
    private double x;
    private double y;
    private double z;
    private double w;

    public Vector4d(Vector4d v){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }


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
        return new Vector4d(x - v.x, y -  v.y, z - v.z,w - v.x);
    }

    public Vector4d reverse(){
        x = -x;
        y = -y;
        z = -z;
        return this;
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

    public void divW(){
        if(w == 0) return ;
        x = x / w;
        y = y / w;
        z = z / w;
        w = 1;
    }

    //矩阵右乘
    public Vector4d matrixMul(Matrix m){
        return new Vector4d(x*m.get(0) + y * m.get(4) + z * m.get(8) + w * m.get(12),
                            x*m.get(1) + y * m.get(5) + z * m.get(9) + w * m.get(13),
                            x*m.get(2) + y * m.get(6) + z * m.get(10) + w * m.get(14),
                            x*m.get(3) + y * m.get(7) + z * m.get(11) + w * m.get(15));
    }

    public Vector4d mul(double f){
        return new Vector4d(
        x *= f,
        y *= f,
        z *= f,
        w *= f);
    }

    public void degToRadian(){
        x *= pi180;
        y *= pi180;
        z *= pi180;
        w *= pi180;
    }

    public double Length(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector4d Normalize(){
        double length = Length();

            x /= length;
            y /= length;
            z /= length;
        return this;
    }

    final double pi180 = 3.1415926 / 180;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Vector4d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
