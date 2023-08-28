package modelling;

import javafx.scene.PointLight;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public abstract class RenderableObject {
    protected Transform currentTransfrom = new Rotate();
    protected Shape3D shape;
    protected Shape3D outline;
    protected PointLight pointLight;
    protected String type;

    private void rotation(Rotate rotate){
        currentTransfrom = currentTransfrom.createConcatenation(rotate);
        shape.getTransforms().clear();
        shape.getTransforms().add(currentTransfrom);
        if(outline!=null){
            outline.getTransforms().clear();
            outline.getTransforms().add(currentTransfrom);
        }
        if (pointLight != null) {
            pointLight.getTransforms().clear();
            pointLight.getTransforms().add(currentTransfrom);
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
            shape.setTranslateX(shape.getTranslateX() + offset);
            if (outline != null)
                outline.setTranslateX(outline.getTranslateX() + offset);
            if (pointLight != null)
                pointLight.setTranslateX(pointLight.getTranslateX() + offset);
        }
        else {
            shape.setTranslateX(offset);
            if (outline != null)
                outline.setTranslateX(offset);
            if (pointLight != null)
                pointLight.setTranslateX(offset);
        }
    }

    public void setY(double offset, boolean accumulative){
        if(accumulative) {
            shape.setTranslateY(shape.getTranslateY() + offset);
            if (outline != null)
                outline.setTranslateY(outline.getTranslateY() + offset);
            if (pointLight != null)
                pointLight.setTranslateY(pointLight.getTranslateY() + offset);
        }
        else {
            shape.setTranslateY(offset);
            if (outline != null)
                outline.setTranslateY(offset);
            if (pointLight != null)
                pointLight.setTranslateY(pointLight.getTranslateY());
        }
    }

    public void setZ(double offset, boolean accumulative){
        if(accumulative) {
            shape.setTranslateZ(shape.getTranslateZ() + offset);
            if (outline != null)
                outline.setTranslateZ(outline.getTranslateZ() + offset);
            if (pointLight != null)
                pointLight.setTranslateZ(pointLight.getTranslateZ() + offset);
        }
        else {
            shape.setTranslateZ(offset);
            if (outline != null)
                outline.setTranslateZ(offset);
            if (pointLight != null)
                pointLight.setTranslateZ(pointLight.getTranslateZ());
        }
    }

    public abstract Shape3D createOutline();

    public void removeOutline(){
        this.outline = null;
    }

    public Shape3D getOutline(){
        return outline;
    }

    public Shape3D getShape3D(){
        return shape;
    }

    public String getType(){
        return type;
    }

    public Transform getCurrentTransfrom(){
        return currentTransfrom;
    }

    public void applyTransform(Transform transform){
        shape.getTransforms().clear();
        currentTransfrom = transform;
        shape.getTransforms().add(currentTransfrom);
    }
}
