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
import model.Location;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Car
 */
public class LocationLoader {

    public ArrayList<Location> loadFromJsonFile(String path) throws IOException {
        String json = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        return parseLocations(json);
    }

    private ArrayList<Location> parseLocations(String json) {
        ArrayList<Location> list = new ArrayList<>();
        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String id = obj.getString("airportId");
            String name = obj.getString("airportName");
            String city = obj.getString("airportCity");
            String country = obj.getString("airportCountry");
            double latitude = obj.getDouble("airportLatitude");
            double longitude = obj.getDouble("airportLongitude");

            list.add(new Location(id, name, city, country, latitude, longitude));
        }

        return list;
    }

}
