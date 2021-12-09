package Component;

/**
 * @author LSY
 * @date 2021/12/09 12:55
 **/
public class AmbtLight {
    private int r;
    private int g;
    private int b;

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

    public AmbtLight(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
