package Component;

import javax.swing.*;
import java.awt.image.BufferedImage;
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

    public List<Obj> getObj() {
        return obj;
    }

    public void setObj(List<Obj> obj) {
        this.obj = obj;
    }

    private List<Obj> obj = new ArrayList<>();
    private AmbtLight ambtLight;
    private PointLight pointLight;
    private JFrame background;
    private BufferedImage texture;

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

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

    }
}
