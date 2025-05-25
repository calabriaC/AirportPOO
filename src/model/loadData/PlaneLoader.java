/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.loadData;

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
public class PlaneLoader {

    public ArrayList<Plane> loadFromJsonFile(String path) throws IOException {
        String json = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        return parsePlanes(json);
    }

    private ArrayList<Plane> parsePlanes(String json) {
        ArrayList<Plane> list = new ArrayList<>();
        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String id = obj.getString("id");
            String brand = obj.getString("brand");
            String model = obj.getString("model");
            int capacity = obj.getInt("maxCapacity");
            String airline = obj.getString("airline");

            list.add(new Plane(id, brand, model, capacity, airline));
        }

        return list;
    }

}
