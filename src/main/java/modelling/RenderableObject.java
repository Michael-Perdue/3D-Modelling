package modelling;

import javafx.scene.PointLight;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public abstract class RenderableObject {
    // The current transformation applied to the object
    protected Transform currentTransfrom = new Rotate();
    // The 3D shape of the object
    protected Shape3D shape;
    // An optional outline for the object
    protected Shape3D outline;
    // An optional point light associated with the object
    protected PointLight pointLight;
    // The type of the object
    protected String type;
    // The material applied to the object
    protected String material = "";


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

    /**
     * Applies a rotation around the X-axis to the object.
     * @param angle The rotation angle in degrees.
     */
    public void setRotationX(double angle){
        Rotate rotate = new Rotate(angle, Rotate.X_AXIS);
        rotation(rotate);
    }

    /**
     * Applies a rotation around the Y-axis to the object.
     * @param angle The rotation angle in degrees.
     */
    public void setRotationY(double angle){
        Rotate rotate = new Rotate(angle, Rotate.Y_AXIS);
        rotation(rotate);
    }

    /**
     * Applies a rotation around the Z-axis to the object.
     * @param angle The rotation angle in degrees.
     */
    public void setRotationZ(double angle){
        Rotate rotate = new Rotate(angle, Rotate.Z_AXIS);
        rotation(rotate);
    }

    /**
     * Sets the X-coordinate position of the object.
     * @param offset The new X-coordinate value.
     * @param accumulative If true, the offset is added to the current X-coordinate; otherwise, it's set as is.
     */
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

    /**
     * Sets the Y-coordinate position of the object.
     * @param offset The new Y-coordinate value.
     * @param accumulative If true, the offset is added to the current Y-coordinate; otherwise, it's set as is.
     */
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

    /**
     * Sets the Z-coordinate position of the object.
     * @param offset The new Z-coordinate value.
     * @param accumulative If true, the offset is added to the current Z-coordinate; otherwise, it's set as is.
     */
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

    /**
     * Creates an outline representation of the object.
     * @return The outline shape.
     */
    public abstract Shape3D createOutline();

    /**
     * Removes the outline representation of the object.
     */
    public void removeOutline(){
        this.outline = null;
    }

    /**
     * Retrieves the outline shape of the object.
     * @return The outline shape.
     */
    public Shape3D getOutline(){
        return outline;
    }

    /**
     * Retrieves the 3D shape of the object.
     * @return The 3D shape.
     */
    public Shape3D getShape3D(){
        return shape;
    }

    /**
     * Retrieves the type of the object.
     * @return The object's type.
     */
    public String getType(){
        return type;
    }

    /**
     * Retrieves the current transformation applied to the object.
     * @return The current transformation.
     */
    public Transform getCurrentTransfrom(){
        return currentTransfrom;
    }

    /**
     * Applies a material to the object.
     * @param material The name of the material to apply.
     */
    public void applyMaterial(String material){
        shape.setMaterial(Materials.getInstance().getMaterial(material));
        this.material = material;
    }

    /**
     * Retrieves the material applied to the object.
     * @return The material applied to the object.
     */
    public String getMaterial(){
        return material;
    }

    /**
     * Applies a transformation to the 3D shape of the object.
     * @param transform The transformation to apply.
     */
    public void applyTransform(Transform transform){
        // Clear existing transformations
        shape.getTransforms().clear();
        // Set the new transformation
        currentTransfrom = transform;
        // Apply the new transformation
        shape.getTransforms().add(currentTransfrom);
    }

    /**
     * Creates a point light associated with the object.
     * @return The created point light.
     */
    public PointLight createPointLight(){
        this.pointLight = new PointLight();
        pointLight.setTranslateZ(shape.getTranslateZ());
        pointLight.setTranslateX(shape.getTranslateX());
        pointLight.setTranslateY(shape.getTranslateY());
        pointLight.getTransforms().add(currentTransfrom);
        pointLight.setColor(Color.rgb(255,255,255,0.2));
        pointLight.setPickOnBounds(false);
        pointLight.setMouseTransparent(true);
        return pointLight;
    }

    /**
     * Removes the point light associated with the object.
     */
    public void removePointLight(){
        pointLight = null;
    }

    /**
     * Retrieves the point light associated with the object.
     * @return The point light associated with the object.
     */
    public PointLight getPointLight(){
        return pointLight;
    }

    /**
     * Sets the point light associated with the object.
     * @param pointLight The point light to associate with the object.
     */
    public void setPointLight(PointLight pointLight){
        this.pointLight = pointLight;
    }

    /**
     * Hides the 3D shape of the object.
     */
    public void hideShape(){
        shape.setVisible(false);
    }

    /**
     * Shows the 3D shape of the object.
     */
    public void showShape(){
        shape.setVisible(true);
    }

    /**
     * Creates a deep copy of the object, optionally deleting the original object.
     * @param object The object to copy to.
     * @param deleteOriginal If true, the original object is deleted.
     */
    protected void deepCopyObject(RenderableObject object, boolean deleteOriginal){
        // Apply the current transformation to the copied object
        object.applyTransform(currentTransfrom);
        // Apply the same material to the copied object
        object.getShape3D().setMaterial(shape.getMaterial());
        // If there is a point light associated with the original object then it copies it
        if(pointLight != null) {
            object.setPointLight(pointLight);
            // Add the copied object's light to the scene
            DrawingGUI.getInstance().addLight(object);
            if(deleteOriginal) {
                // Remove the original object's light
                DrawingGUI.getInstance().removeLight(this);
            }
        }
        if(deleteOriginal) {
            // Remove the original object from the scene
            DrawingGUI.getInstance().removeObject(this);
            // Triggers the outline to be made for the copied object
            DrawingGUI.getInstance().renderableObjectClicked(object);
        }
    }
}
