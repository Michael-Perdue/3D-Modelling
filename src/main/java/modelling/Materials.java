package modelling;

import javafx.scene.image.Image;
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
        addMaterial("cobble","/cobble.png","/cobbleNormal.png","/cobbleSpec.png");
        addMaterial("wood","/wood.jpg","/woodNormal.jpg","/woodSpec.jpg");
    }

    public Material getWood(){
        return materials.get("wood");
    }

    public Material getCobble(){
        return materials.get("cobble");
    }

    public void addMaterial(String name, String diffusePATH, String bumpPATH, String specPATH){
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(Drawing.class.getResourceAsStream(diffusePATH)));
        material.setBumpMap(new Image(Drawing.class.getResourceAsStream(bumpPATH)));
        if(specPATH != null) {
            material.setSpecularMap(new Image(Drawing.class.getResourceAsStream(bumpPATH)));
        }
        materials.put(name,material);
    }

    public Material getMaterial(String name){
        return materials.get(name);
    }

    public HashMap<String, Material> getAllMaterials(){
        return materials;
    }

}
