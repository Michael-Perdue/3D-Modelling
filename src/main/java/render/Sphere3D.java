package render;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Sphere3D extends Sphere implements RenderableObject{

    private Transform currentTransfrom = new Rotate();
    public Sphere3D (double radius){
        super(radius);
    }

    private void rotation(Rotate rotate){
        currentTransfrom = currentTransfrom.createConcatenation(rotate);
        this.getTransforms().clear();
        this.getTransforms().add(currentTransfrom);
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
        if(accumulative)
            this.setTranslateX(this.getTranslateX()+offset);
        else
            this.setTranslateX(offset);
    }

    public void setY(double offset, boolean accumulative){
        if(accumulative)
            this.setTranslateY(this.getTranslateY()+offset);
        else
            this.setTranslateY(offset);
    }

    public void setZ(double offset, boolean accumulative){
        if(accumulative)
            this.setTranslateZ(this.getTranslateZ()+offset);
        else
            this.setTranslateZ(offset);
    }

    public Shape3D getShape3D(){
        return this;
    }

}
