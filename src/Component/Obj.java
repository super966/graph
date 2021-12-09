package Component;

import MathComponent.Matrix;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LSY
 * @date 2021/12/09 13:05
 **/
public class Obj {

    private List<Triangle> ltri = new ArrayList<>();
    private Matrix worldmatrix;
    private JPanel jPanel = new JPanel();



    public void addtri(Triangle t){
        this.ltri.add(t);
    }

    public void setWorldmatrix(Matrix wpos){
        this.worldmatrix = wpos;
    }

    public void draw() {
        for (int i = 0; i < ltri.size(); i++) {
            ltri.get(i).draw(worldmatrix);
        }

    }
}
