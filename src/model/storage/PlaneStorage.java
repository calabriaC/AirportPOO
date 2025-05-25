/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;


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
public class PlaneStorage {
 private static ArrayList<Plane> planes = new ArrayList<>();

    public PlaneStorage() {
        this.planes = new ArrayList<>();

        try {
            String json = Files.readString(Paths.get("C:\\Users\\Car\\Desktop\\AirportPOO\\json\\planes.json"), StandardCharsets.UTF_8);

            this.planes = readPlanes(json);
        } catch (Exception e) {

            this.planes = new ArrayList<>();
        }
    } 

    public ArrayList<Plane> getPlanes() {
        return planes;
    }

    private ArrayList<Plane> readPlanes(String jsonPlanes) {
        ArrayList<Plane> list = new ArrayList<>();

        JSONArray array = new JSONArray(jsonPlanes);
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String id = obj.getString("id");
            String brand = obj.getString("brand");
            String model = obj.getString("model");
            int capacity = obj.getInt("capacity");
            String airline = obj.getString("airline");

            Plane plane = new Plane(id, brand, model, capacity, airline);
            list.add(plane);
        }

        return list;
    }
}


