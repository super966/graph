package MathComponent;

import Component.Material;
import Component.TextureCoord;

import java.awt.*;

/**
 * @author LSY
 * @date 2021/12/08 15:34
 **/
public class Vertex implements Cloneable {
    private Vector4d pos;
    private Vector4d normal;
    private Material material;
    private TextureCoord texture;
    private Color color;
    private double z_deep;

    public Vector4d getPos() {
        return this.pos;
    }

    public void setPos(Vector4d pos) {
        this.pos = pos;
    }

    public Vector4d getNormal() {
        return normal;
    }

    public void setNormal(Vector4d normal) {
        this.normal = normal;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public TextureCoord getTexture() {
        return texture;
    }

    public void setTexture(TextureCoord texture) {
        this.texture = texture;
    }

    public double getZ_deep() {
        return z_deep;
    }

    public void setZ_deep(double z_deep) {
        this.z_deep = z_deep;
    }



    public Vertex() {
        pos = new Vector4d(0.0,0.0,0.0,1);
        z_deep = 0.0;
    }

    public Vertex(Vector4d pos){
        this.pos = pos;
    }

    public Vertex(Vertex v){
        this.pos = v.pos;
        this.normal = v.normal;
        this.material = v.material;
        this.texture = v.texture;
        this.color = v.color;
        this.z_deep = v.z_deep;
    }

    public Vertex(Vector4d pos, Vector4d normal, Material material, TextureCoord texture, Color color, double z_deep) {
        this.pos = pos;
        this.normal = normal;
        this.material = material;
        this.texture = texture;
        this.color = color;
        this.z_deep = z_deep;
    }

   public void setColor(Color c){
        this.color = c;
   }

    public Color getColor() {
        return color;
    }

    public void trans(Vector4d trs){
        Matrix temp = new Matrix();
        pos = pos.matrixMul(temp.trans(trs));
    }

    public void scale(Vector4d sc){
        Matrix temp = new Matrix();
        pos = pos.matrixMul(temp.scale(sc));
    }

    public void rotatex(double deg){
        Matrix temp = new Matrix();
        pos = pos.matrixMul(temp.rotateX(deg));
    }

    public void rotatey(double deg ){
        Matrix temp = new Matrix();
        pos = pos.matrixMul(temp.rotateY(deg));
    }

    public void rotatez(double deg ){
        Matrix temp = new Matrix();
        pos = pos.matrixMul(temp.rotateZ(deg));
    }

    public void matrixMul(Matrix m){
        pos = pos.matrixMul(m);
        normal = normal.matrixMul(m);
    }

    public void divideW(){
        pos.divW();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Vertex v = (Vertex) super.clone();
        v.pos = (Vector4d) this.pos.clone();
        v.normal = (Vector4d) this.normal.clone();
        v.material = (Material) this.material.clone();
        v.texture = (TextureCoord) this.texture.clone();
        v.z_deep = this.z_deep;
        return v;
    }
}
