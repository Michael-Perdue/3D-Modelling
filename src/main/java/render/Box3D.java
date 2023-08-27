package render;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Box3D extends Box implements RenderableObject{
    private Transform currentTransfrom = new Rotate();
    private Box outline;

    public Box3D (double depth, double height, double width){
        super(depth,height,width);
    }

    private void rotation(Rotate rotate){
        currentTransfrom = currentTransfrom.createConcatenation(rotate);
        this.getTransforms().clear();
        this.getTransforms().add(currentTransfrom);
        if(outline!=null){
            outline.getTransforms().clear();
            outline.getTransforms().add(currentTransfrom);
        }
    }

    public void setRotationX(double angle){
        Rotate rotate = new Rotate(angle, Rotate.X_AXIS);
        rotation(rotate);
    }

    public void setRotationY(double angle){
        Rotate rotate = new Rotate(angle, Rotate.Y_AXIS);
        rotation(rotate);
    }

    public void setRotationZ(double angle){
        Rotate rotate = new Rotate(angle, Rotate.Z_AXIS);
        rotation(rotate);
    }

    public void setX(double offset, boolean accumulative){
        if(accumulative) {
            this.setTranslateX(this.getTranslateX() + offset);
            if (outline != null)
                outline.setTranslateX(outline.getTranslateX() + offset);
        }
        else {
            this.setTranslateX(offset);
            if (outline != null)
                outline.setTranslateX(offset);
        }
    }

    public void setY(double offset, boolean accumulative){
        if(accumulative) {
            this.setTranslateY(this.getTranslateY() + offset);
            if (outline != null)
                outline.setTranslateY(outline.getTranslateY() + offset);
        }
        else {
            this.setTranslateY(offset);
            if (outline != null)
                outline.setTranslateY(offset);
        }
    }

    public void setZ(double offset, boolean accumulative){
        if(accumulative) {
            this.setTranslateZ(this.getTranslateZ() + offset);
            if (outline != null)
                outline.setTranslateZ(outline.getTranslateZ() + offset);
        }
        else {
            this.setTranslateZ(offset);
            if (outline != null)
                outline.setTranslateZ(offset);
        }
    }

    public Box createOutline(){
        Box outlineShape = new Box(this.getWidth() + 1,this.getHeight() + 1,this.getDepth() + 1);
        outlineShape.setTranslateZ(this.getTranslateZ());
        outlineShape.setTranslateX(this.getTranslateX());
        outlineShape.setTranslateY(this.getTranslateY());
        outlineShape.getTransforms().add(currentTransfrom);
        PhongMaterial outlineMaterial = new PhongMaterial();
        outlineMaterial.setDiffuseColor(new Color(0.0, 0.0, 1.0, 0.1)); // Translucent blue (R, G, B, Opacity)
        outlineMaterial.setSpecularColor(new Color(0.0, 0.0, 1.0, 0.1));
        outlineShape.setMaterial(outlineMaterial);
        outlineShape.setPickOnBounds(false);
        outlineShape.setMouseTransparent(true);
        this.outline = outlineShape;
        return this.outline;
    }

    public void removeOutline(){
        this.outline = null;
    }

    public Box getOutline(){
        return outline;
    }

    public Shape3D getShape3D(){
        return this;
    }
}
