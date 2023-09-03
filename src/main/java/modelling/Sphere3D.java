package modelling;

import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Sphere3D extends RenderableObject{

    private Transform currentTransfrom = new Rotate();
    public Sphere3D (double radius){
        super();
        this.shape = new Sphere(radius);
        this.type = "sphere";
    }

    public Shape3D createOutline(){
        Sphere sphere = (Sphere)this.shape;
        Sphere outlineShape = new Sphere(sphere.getRadius()+1);
        outlineShape.setTranslateZ(sphere.getTranslateZ());
        outlineShape.setTranslateX(sphere.getTranslateX());
        outlineShape.setTranslateY(sphere.getTranslateY());
        outlineShape.getTransforms().add(currentTransfrom);
        PhongMaterial outlineMaterial = new PhongMaterial();
        outlineMaterial.setDiffuseColor(new Color(0.0, 0.0, 1.0, 0.1)); // Translucent blue (R, G, B, Opacity)
        outlineMaterial.setSpecularColor(new Color(0.0, 0.0, 1.0, 0.1)); // Translucent blue (R, G, B, Opacity)
        outlineShape.setMaterial(outlineMaterial);
        outlineShape.setPickOnBounds(false);
        outlineShape.setMouseTransparent(true);
        this.outline = outlineShape;
        return this.outline;
    }

    public void setAllDimensions(double radius){
        Sphere3D sphere = DrawingGUI.getInstance().createSphere(radius,shape.getTranslateX(),shape.getTranslateY(),shape.getTranslateZ());
        deepCopyObject(sphere,true);
    }

}
