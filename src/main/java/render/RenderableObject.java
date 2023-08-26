package render;

import javafx.scene.Node;

public interface RenderableObject {
    public void setRotationX(double angle);
    public void setRotationY(double angle);
    public void setRotationZ(double angle);
    public void setX(double offset);
    public void setY(double offset);
    public void setZ(double offset);
}
