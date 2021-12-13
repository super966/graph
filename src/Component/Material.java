package Component;

/**
 * @author LSY
 * @date 2021/12/08 15:38
 **/
public class Material {
    private double ka;
    private double kd;
    private double ks;
    private double n;

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }

    public Material(double ka, double kd, double ks, double n) {
        this.ka = ka;
        this.kd = kd;
        this.ks = ks;
        this.n  = n;
    }

    public double getKa() {
        return ka;
    }

    public void setKa(double ka) {
        this.ka = ka;
    }

    public double getKd() {
        return kd;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public double getKs() {
        return ks;
    }

    public void setKs(double ks) {
        this.ks = ks;
    }


}
