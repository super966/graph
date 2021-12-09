package Component;

/**
 * @author LSY
 * @date 2021/12/09 12:51
 **/
public class ViewPoint {
    private double x;
    private double y;
    private double width;
    private double height;
    private double minZ;
    private double maxZ;

    public ViewPoint(double x, double y, double width, double height, double minZ, double maxZ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.minZ = minZ;
        this.maxZ = maxZ;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public ViewPoint(ViewPoint v){
        this.x = v.x;
        this.y = v.y;
        this.width = v.width;
        this.height = v.height;
        this.minZ = v.minZ;
        this.maxZ = v.maxZ;
    }
}
