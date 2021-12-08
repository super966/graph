package MathComponent;

public class Vector3d {
    private double x;
    private double y;
    private double z;

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

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d add(Vector3d v){
        return new Vector3d(x + v.x, y + v.y,z + v.z);
    }

    public Vector3d minus(Vector3d v){
        return new Vector3d(x - v.x, y - v.y,z - v.z);
    }


    public double Dot(Vector3d v){
        return v.x * x + v.y * y + v.z * z;
    }

    public double Length(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void Normalize(){
        double length = Length();
        x /= length;
        y /= length;
        z /= length;
    }
}
