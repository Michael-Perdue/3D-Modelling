package render;

import javafx.scene.Node;
import javafx.scene.shape.Shape3D;

public interface RenderableObject {
    public void setRotationX(double angle);
    public void setRotationY(double angle);
    public void setRotationZ(double angle);
    public void setX(double offset);
    public void setY(double offset);
    public void setZ(double offset);
    public Shape3D getShape3D();
}
