package modelling;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;

public class Box3D extends RenderableObject {


    public Box3D (double depth, double height, double width){
        super();
        this.shape = new Box(depth,height,width);
    }

    public Shape3D createOutline(){
        Box box = (Box)this.shape;
        Box outlineShape = new Box(box.getWidth() + 1,box.getHeight() + 1,box.getDepth() + 1);
        outlineShape.setTranslateZ(box.getTranslateZ());
        outlineShape.setTranslateX(box.getTranslateX());
        outlineShape.setTranslateY(box.getTranslateY());
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

}
