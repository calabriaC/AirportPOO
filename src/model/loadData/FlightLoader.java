/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.loadData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Flight;
import model.Location;
import model.Plane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Car
 */
public class FlightLoader {

    public ArrayList<Flight> loadFromJsonFile(String path, ArrayList<Plane> planes, ArrayList<Location> locations) throws IOException {
        String json = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        return parseFlights(json, planes, locations);
    }

    private ArrayList<Flight> parseFlights(String json, ArrayList<Plane> planes, ArrayList<Location> locations) {
        ArrayList<Flight> list = new ArrayList<>();
        JSONArray array = new JSONArray(json);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String id = obj.getString("id");
            String departureDateStr = obj.getString("departureDate");
            LocalDateTime departureDate = LocalDateTime.parse(departureDateStr, formatter);

            int hoursDurationArrival = obj.getInt("hoursDurationArrival");
            int minutesDurationArrival = obj.getInt("minutesDurationArrival");

            Plane plane = findPlaneById(planes, obj.getString("plane"));
            Location departure = findLocationById(locations, obj.getString("departureLocation"));
            Location arrival = findLocationById(locations, obj.getString("arrivalLocation"));

            // Verificar si hay escala
            if (obj.has("scaleLocationId") && !obj.isNull("scaleLocationId") && !obj.getString("scaleLocationId").isEmpty()) {
                Location scale = findLocationById(locations, obj.getString("scaleLocationId"));
                int hoursDurationScale = obj.getInt("hoursDurationScale");
                int minutesDurationScale = obj.getInt("minutesDurationScale");

                list.add(new Flight(id, plane, departure, scale, arrival, departureDate,
                        hoursDurationArrival, minutesDurationArrival,
                        hoursDurationScale, minutesDurationScale));
            } else {
                list.add(new Flight(id, plane, departure, arrival, departureDate,
                        hoursDurationArrival, minutesDurationArrival));
            }
        }

        return list;
    }

    private Plane findPlaneById(ArrayList<Plane> planes, String id) {
        return planes.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    private Location findLocationById(ArrayList<Location> locations, String id) {
        return locations.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }
}
    

