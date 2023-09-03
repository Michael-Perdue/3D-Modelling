package modelling;

import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

public class Box3D extends RenderableObject {

    public Box3D (double depth, double height, double width){
        super();
        this.shape = new Box(depth,height,width);
        this.type = "box";
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
        outlineMaterial.setSelfIlluminationMap(null);
        outlineShape.setMaterial(outlineMaterial);
        outlineShape.setPickOnBounds(false);
        outlineShape.setMouseTransparent(true);
        this.outline = outlineShape;
        return this.outline;
    }

    public void setSize(double depth, double height, double width){
        Box3D newBox = DrawingGUI.getInstance().createBox(depth,height,width,shape.getTranslateX(),shape.getTranslateY(),shape.getTranslateZ());
        deepCopyObject(newBox,true);
    }

    public void setWidth(double width){;
        Box box = (Box)shape;
        setSize(box.getDepth(),box.getHeight(),width);
    }

    public void setHeight(double height){;
        Box box = (Box)shape;
        setSize(box.getDepth(),height,box.getWidth());
    }

    public void setDepth(double depth){;
        Box box = (Box)shape;
        setSize(depth,box.getHeight(),box.getWidth());
    }


    public void setAllDimensions(double depth, double height, double width){
        setSize(depth,height,width);
    }

}
