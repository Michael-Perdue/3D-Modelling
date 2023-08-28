package modelling;

import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
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

    public PointLight createPointLight(){
        Sphere sphere = (Sphere)this.shape;
        this.pointLight = new PointLight();
        pointLight.setTranslateZ(sphere.getTranslateZ());
        pointLight.setTranslateX(sphere.getTranslateX());
        pointLight.setTranslateY(sphere.getTranslateY());
        pointLight.getTransforms().add(currentTransfrom);
        pointLight.setColor(Color.rgb(255,255,255,0.2));
        pointLight.setPickOnBounds(false);
        pointLight.setMouseTransparent(true);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(new Color(0.0, 0.0, 0.0, 0.7));
        material.setSpecularColor(new Color(0.0, 0.0, 0.0, 0.7));
        material.setSelfIlluminationMap(null);
        sphere.setMaterial(material);
        return pointLight;
    }

}
