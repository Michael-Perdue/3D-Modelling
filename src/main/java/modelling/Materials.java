package modelling;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;

import java.util.HashMap;

public class Materials {

    // Singleton instance
    private static Materials instance;
    // Stores materials by name
    private HashMap<String,Material> materials = new HashMap<>();

    /**
     * Retrieves the singleton instance of the Materials class.
     * @return The singleton instance of Materials.
     */
    public static Materials getInstance() {
        if(instance == null)
            instance = new Materials();
        return instance;
    }

    /**
     * Private constructor to ensure singleton pattern and initialize base materials.
     */
    private Materials(){
        addBaseMaterials();
    }

    /**
     * Initializes the base materials by calling addMaterial and addLightBoxMaterial methods.
     */
    private void addBaseMaterials(){
        addMaterial("Cobble","/cobble.png","/cobbleNormal.png","/cobbleSpec.png");
        addMaterial("Wood","/wood.jpg","/woodNormal.jpg","/woodSpec.jpg");
        addLightBoxMaterial();
    }

    /**
     * Adds a custom material with specified textures.
     * @param name       The name of the material.
     * @param diffusePATH The path to the diffuse texture.
     * @param bumpPATH    The path to the bump texture.
     * @param specPATH    The path to the specular texture.
     */
    public void addMaterial(String name, String diffusePATH, String bumpPATH, String specPATH){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(DrawingGUI.class.getResourceAsStream(diffusePATH)));
        if(bumpPATH != null)
            material.setBumpMap(new Image(DrawingGUI.class.getResourceAsStream(bumpPATH)));
        if(specPATH != null)
            material.setSpecularMap(new Image(DrawingGUI.class.getResourceAsStream(specPATH)));
        materials.put(name,material);
    }

    /**
     * Adds a custom "Light Box" material for representing light sources.
     */
    public void addLightBoxMaterial(){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(new Color(0.0, 0.0, 0.0, 0.7));
        material.setSpecularColor(new Color(0.0, 0.0, 0.0, 0.7));
        material.setSelfIlluminationMap(null);
        materials.put("Light Box",material);
    }

    /**
     * Retrieves a material by its name.
     * @param name The name of the material to retrieve.
     * @return The material associated with the given name.
     */
    public Material getMaterial(String name){
        return materials.get(name);
    }

    /**
     * Retrieves the "Wood" material.
     * @return The "Wood" material.
     */
    public Material getWood(){
        return materials.get("Wood");
    }

    /**
     * Retrieves the "Cobble" material.
     * @return The "Cobble" material.
     */
    public Material getCobble(){
        return materials.get("Cobble");
    }

    /**
     * Retrieves all the materials stored in the manager.
     * @return A HashMap containing all materials, where the keys are material names.
     */
    public HashMap<String, Material> getAllMaterials(){
        return materials;
    }

}
