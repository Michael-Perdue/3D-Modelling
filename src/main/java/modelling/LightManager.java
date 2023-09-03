package modelling;

import java.util.ArrayList;

public class LightManager {

    private boolean lightOnStatus = false;
    private ArrayList<RenderableObject> lights = new ArrayList<>();
    public void addLight(RenderableObject renderableObject){
        DrawingGUI.getInstance().addNode(renderableObject.createPointLight());
        lights.add(renderableObject);
    }

    public void removeLight(RenderableObject object){
        DrawingGUI.getInstance().removeNode(object.getPointLight());
        object.removePointLight();
        lights.remove(object);
    }

    public void hideLights(){
        lightOnStatus = false;
        lights.forEach(light -> {
            light.hideShape();
            try {
                if(light.getOutline() != null)
                    DrawingGUI.getInstance().renderableObjectClicked(light);
            }catch (Exception e){}
        });
    }

    public void showLights(){
        lightOnStatus = true;
        lights.forEach(light -> light.showShape());
    }

    public boolean isLightsOn(){
        return lightOnStatus;
    }
}
