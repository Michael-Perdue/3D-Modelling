package modelling;

import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Sphere3D extends RenderableObject{

    /**
     * Constructs a 3D sphere with the specified radius.
     * @param radius The radius of the sphere.
     */
    public Sphere3D (double radius){
        super();
        this.shape = new Sphere(radius);
        this.type = "sphere";
    }

    /**
     * This function creates an outline for the 3D box and returns it as a `Shape3D` object.
     * @return The outline of the 3D box as a `Shape3D`.
     */
    public Shape3D createOutline(){
        // Create an outline shape based on the dimensions of the sphere
        Sphere sphere = (Sphere)this.shape;
        Sphere outlineShape = new Sphere(sphere.getRadius()+1);
        outlineShape.setTranslateZ(sphere.getTranslateZ());
        outlineShape.setTranslateX(sphere.getTranslateX());
        outlineShape.setTranslateY(sphere.getTranslateY());
        outlineShape.getTransforms().add(currentTransfrom);
        // Set material properties for the outline
        PhongMaterial outlineMaterial = new PhongMaterial();
        outlineMaterial.setDiffuseColor(new Color(0.0, 0.0, 1.0, 0.1)); // Translucent blue (R, G, B, Opacity)
        outlineMaterial.setSpecularColor(new Color(0.0, 0.0, 1.0, 0.1)); // Translucent blue (R, G, B, Opacity)
        outlineShape.setMaterial(outlineMaterial);
        outlineShape.setPickOnBounds(false);
        outlineShape.setMouseTransparent(true);
        this.outline = outlineShape;
        return this.outline;
    }

    /**
     * Sets the radius of the 3D sphere to the specified value.
     * @param radius The new radius of the sphere.
     */
    public void setAllDimensions(double radius){
        Sphere3D sphere = DrawingGUI.getInstance().createSphere(radius,shape.getTranslateX(),shape.getTranslateY(),shape.getTranslateZ());
        deepCopyObject(sphere,true);
    }

}
