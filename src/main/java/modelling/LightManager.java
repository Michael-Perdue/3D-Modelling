package modelling;

import java.util.ArrayList;

public class LightManager {

    // Flags to manage the visibility and enabling status of lights
    private boolean lightVisibleStatus = true;
    private boolean lightEnabledStatus = true;

    // A list to store renderable light objects
    private ArrayList<RenderableObject> lights = new ArrayList<>();

    /**
     * Adds a light to the manager and displays it in the application.
     * @param renderableObject The renderable light object to add.
     */
    public void addLight(RenderableObject renderableObject){
        // Add the light to the DrawingGUI
        DrawingGUI.getInstance().addNode(renderableObject.createPointLight());
        // Add the light to the manager's list
        lights.add(renderableObject);
    }

    /**
     * Removes a light from the manager and the application.
     * @param object The renderable light object to remove.
     */
    public void removeLight(RenderableObject object){
        // Remove the light from DrawingGUI
        DrawingGUI.getInstance().removeNode(object.getPointLight());
        // Remove the light from the object
        object.removePointLight();
        // Remove the light from the manager's list
        lights.remove(object);
    }

    /**
     * Hides all lights in the application.
     */
    public void hideLights(){
        lightVisibleStatus = false;
        lights.forEach(light -> {
            light.hideShape();
            try {
                // If the light has an outline, trigger a click event in DrawingGUI
                if(light.getOutline() != null)
                    DrawingGUI.getInstance().renderableObjectClicked(light);
            }catch (Exception e){}
        });
    }

    /**
     * Shows all lights in the application.
     */
    public void showLights(){
        lightVisibleStatus = true;
        lights.forEach(light -> light.showShape());
    }

    /**
     * Enables all lights in the application.
     */
    public void enableLights(){
        lightEnabledStatus = true;
        lights.forEach(light -> {
            // Add the light to DrawingGUI
            DrawingGUI.getInstance().addNode(light.createPointLight());
        });
    }

    /**
     * Disables all lights in the application.
     */
    public void disableLights(){
        lightEnabledStatus = false;
        lights.forEach(light -> {
            DrawingGUI.getInstance().removeNode(light.getPointLight());
            light.removePointLight();
        });
    }

    /**
     * Checks if lights are currently visible in the application.
     * @return `true` if lights are visible, `false` otherwise.
     */
    public boolean isLightsVisable(){
        return lightVisibleStatus;
    }


    /**
     * Checks if lights are currently enabled in the application.
     * @return `true` if lights are enabled, `false` otherwise.
     */
    public boolean isLightsEnabled(){
        return lightEnabledStatus;
    }
}
