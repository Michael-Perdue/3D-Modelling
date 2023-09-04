package modelling;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;

import java.util.HashMap;

public class Materials {

    private static Materials instance;

    private HashMap<String,Material> materials = new HashMap<>();

    public static Materials getInstance() {
        if(instance == null)
            instance = new Materials();
        return instance;
    }

    private Materials(){
        addBaseMaterials();
    }

    private void addBaseMaterials(){
        addMaterial("Cobble","/cobble.png","/cobbleNormal.png","/cobbleSpec.png");
        addMaterial("Wood","/wood.jpg","/woodNormal.jpg","/woodSpec.jpg");
        addMaterial("Earth","/earth.jpg",null,"/earthSpec.jpg");
        addMaterial("Sun","/sun.jpg",null,null);
        addLightBoxMaterial();
    }

    public Material getWood(){
        return materials.get("Wood");
    }

    public Material getCobble(){
        return materials.get("Cobble");
    }

    public void addMaterial(String name, String diffusePATH, String bumpPATH, String specPATH){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(DrawingGUI.class.getResourceAsStream(diffusePATH)));
        if(bumpPATH != null)
            material.setBumpMap(new Image(DrawingGUI.class.getResourceAsStream(bumpPATH)));
        if(specPATH != null)
            material.setSpecularMap(new Image(DrawingGUI.class.getResourceAsStream(specPATH)));
        materials.put(name,material);
    }

    public void addLightBoxMaterial(){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(new Color(0.0, 0.0, 0.0, 0.7));
        material.setSpecularColor(new Color(0.0, 0.0, 0.0, 0.7));
        material.setSelfIlluminationMap(null);
        materials.put("Light Box",material);
    }

    public Material getMaterial(String name){
        return materials.get(name);
    }

    public HashMap<String, Material> getAllMaterials(){
        return materials;
    }

}
