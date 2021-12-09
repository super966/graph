package Component;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LSY
 * @date 2021/12/09 15:09
 **/
public class Scene {

    private static volatile Scene scene;
    private Camera camera;
    private ViewPoint viewPoint;
    private List<Obj> obj = new ArrayList<>();
    private AmbtLight ambtLight;
    private PointLight pointLight;
    private JFrame background;

    public JFrame getBackground() {
        return background;
    }

    public void setBackground(JFrame background) {
        this.background = background;
    }

    public AmbtLight getAmbtLight() {
        return ambtLight;
    }

    public void setAmbtLight(AmbtLight ambtLight) {
        this.ambtLight = ambtLight;
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public ViewPoint getViewPoint() {
        return viewPoint;
    }

    public void setViewPoint(ViewPoint viewPoint) {
        this.viewPoint = viewPoint;
    }

    public void addObj(Obj obj){
        this.obj.add(obj);
    }

    private Scene(){}

    public static Scene getInstance(){
        if(scene == null){
            synchronized (Scene.class){
                if(scene == null){
                    scene = new Scene();
                }
            }
        }
        return scene;
    }

    public void draw(){
        for (int i = 0; i < this.obj.size(); i++) {
            this.obj.get(i).draw();
        }
    }
}
