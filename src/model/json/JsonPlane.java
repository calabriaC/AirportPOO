/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import model.Plane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Car
 */
public class JsonPlane {
    
      //funcion para llenar las listas con objetos de tipo Plane
    public static ArrayList<Plane> readPlanes(String path) throws IOException {
        String content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        JSONArray array  = new JSONArray(content);
        ArrayList<Plane> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            
            String id = obj.getString("id");
            String brand = obj.getString("brand");
            String model = obj.getString("model");
            int maxCapacity = obj.getInt("maxCapacity");
            String airline = obj.getString("airline");
            //crear objeto tipo plane y guardar en la lista para devolverla
            Plane plane = new Plane(id, brand, model, maxCapacity, airline);
            list.add(plane);
        }
        
        return list;
    }
}
