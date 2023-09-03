package modelling;

import java.util.ArrayList;

public class LightManager {

    private boolean lightVisibleStatus = true;
    private boolean lightEnabledStatus = true;
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
        lightVisibleStatus = false;
        lights.forEach(light -> {
            light.hideShape();
            try {
                if(light.getOutline() != null)
                    DrawingGUI.getInstance().renderableObjectClicked(light);
            }catch (Exception e){}
        });
    }

    public void showLights(){
        lightVisibleStatus = true;
        lights.forEach(light -> light.showShape());
    }

    public void enableLights(){
        lightEnabledStatus = true;
        lights.forEach(light -> {
            DrawingGUI.getInstance().addNode(light.createPointLight());
        });
    }

    public void disableLights(){
        lightEnabledStatus = false;
        lights.forEach(light -> {
            DrawingGUI.getInstance().removeNode(light.getPointLight());
            light.removePointLight();
        });
    }

    public boolean isLightsVisable(){
        return lightVisibleStatus;
    }

    public boolean isLightsEnabled(){
        return lightEnabledStatus;
    }
}
