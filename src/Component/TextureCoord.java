package Component;

/**
 * @author LSY
 * @date 2021/12/08 15:41
 **/
public class TextureCoord {
    private double u;
    private double v;

    public double getU() {
        return u;
    }

    public void setU(double u) {
        this.u = u;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public TextureCoord(double u, double v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
