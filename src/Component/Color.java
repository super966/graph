package Component;

/**
 * @author LSY
 * @date 2021/12/08 15:45
 **/
public class Color {
    private int rValue;
    private int gValue;
    private int bValue;

    public Color(int rValue, int gValue, int bValue) {
        this.rValue = rValue;
        this.gValue = gValue;
        this.bValue = bValue;
    }

    public int getrValue() {
        return rValue;
    }

    public void setrValue(int rValue) {
        this.rValue = rValue;
    }

    public int getgValue() {
        return gValue;
    }

    public void setgValue(int gValue) {
        this.gValue = gValue;
    }

    public int getbValue() {
        return bValue;
    }

    public void setbValue(int bValue) {
        this.bValue = bValue;
    }
}
