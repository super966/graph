package MathComponent;

public class Vector2d {
    private double x;
    private double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
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

    public Vector2d Rotate(double deg){
        return new Vector2d(Math.cos(deg) * x - Math.sin(deg) * y,
                Math.sin(deg) * x + Math.cos(deg) * y);
    }

    public Vector2d add(Vector2d v){
        return new Vector2d(x + v.x, y + v.y);
    }

    public Vector2d minus(Vector2d v){
        return new Vector2d(x - v.x, y - v.y);
    }
}
