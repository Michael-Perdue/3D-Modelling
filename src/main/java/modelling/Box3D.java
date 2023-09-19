package modelling;

import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

public class Box3D extends RenderableObject {


    /**
     * Constructs a 3D box with the specified dimensions.
     * @param depth  The depth of the box.
     * @param height The height of the box.
     * @param width  The width of the box.
     */
    public Box3D (double depth, double height, double width){
        super();
        this.shape = new Box(depth,height,width);
        this.type = "box";
    }

    /**
     * This function creates an outline for the 3D box and returns it as a `Shape3D` object.
     * @return The outline of the 3D box as a `Shape3D`.
     */
    public Shape3D createOutline(){
        // Create an outline shape based on the dimensions of the box
        Box box = (Box)this.shape;
        Box outlineShape = new Box(box.getWidth() + 1,box.getHeight() + 1,box.getDepth() + 1);
        outlineShape.setTranslateZ(box.getTranslateZ());
        outlineShape.setTranslateX(box.getTranslateX());
        outlineShape.setTranslateY(box.getTranslateY());
        outlineShape.getTransforms().add(currentTransfrom);

        // Set material properties for the outline
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

    /**
     * Sets the size of the 3D box to the specified dimensions.
     * @param depth  The new depth of the box.
     * @param height The new height of the box.
     * @param width  The new width of the box.
     */
    public void setSize(double depth, double height, double width){
        // Create a new box with the specified dimensions
        Box3D newBox = DrawingGUI.getInstance().createBox(depth,height,width,shape.getTranslateX(),shape.getTranslateY(),shape.getTranslateZ());
        // Perform a deep copy of the new box's properties to this object
        deepCopyObject(newBox,true);
    }

    /**
     * Sets the width of the 3D box to the specified value,
     * while keeping the other dimensions unchanged.
     * @param width The new width of the box.
     */
    public void setWidth(double width){;
        Box box = (Box)shape;
        setSize(box.getDepth(),box.getHeight(),width);
    }

    /**
     * Sets the height of the 3D box to the specified value,
     * while keeping the other dimensions unchanged.
     * @param height The new height of the box.
     */
    public void setHeight(double height){;
        Box box = (Box)shape;
        setSize(box.getDepth(),height,box.getWidth());
    }

    /**
     * Sets the depth of the 3D box to the specified value,
     * While keeping the other dimensions unchanged.
     * @param depth The new depth of the box.
     */
    public void setDepth(double depth){;
        Box box = (Box)shape;
        setSize(depth,box.getHeight(),box.getWidth());
    }

    /**
     * Sets all dimensions (depth, height, and width) of the 3D box to the specified values.
     * @param depth  The new depth of the box.
     * @param height The new height of the box.
     * @param width  The new width of the box.
     */
    public void setAllDimensions(double depth, double height, double width){
        setSize(depth,height,width);
    }

}
