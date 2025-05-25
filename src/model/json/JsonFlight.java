/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.json;

import Controller.LocationController;
import Controller.PlaneController;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
public class JsonFlight {

    public static ArrayList<Flight> readFlights(String path) throws IOException {

        String content = Files.readString(Paths.get(path), StandardCharsets.UTF_8);
        JSONArray array = new JSONArray(content);
        ArrayList<Flight> list = new ArrayList<>();

        PlaneController ac = new PlaneController();
        LocationController lc = new LocationController();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String id = obj.getString("id");
            Plane plane = ac.getPlaneByID(obj.getString("plane"));
            Location departureLocation = lc.getLocationByID(obj.getString("departureLocation"));
            Location arrivalLocation = lc.getLocationByID(obj.getString("arrivalLocation"));
            Location scaleLocation;

            if (obj.isNull("scaleLocation")) {
                scaleLocation = null;
            } else {
                scaleLocation = lc.getLocationByID(obj.getString("scaleLocation"));
            }

            LocalDateTime departureDate = LocalDateTime.parse(obj.getString("departureDate"));
            int hour1 = obj.getInt("hoursDurationArrival");
            int minutes1 = obj.getInt("minutesDurationArrival");
            int hour2 = obj.getInt("hoursDurationScale");
            int minutes2 = obj.getInt("minutesDurationScale");

            if (scaleLocation == null) {
                Flight flight = new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation, departureDate, hour1, minutes1, 0, 0);
                list.add(flight);
            } else {
                Flight flight = new Flight(id, plane, departureLocation, scaleLocation, arrivalLocation, departureDate, hour1, minutes1, hour2, minutes2);
                list.add(flight);
            }

        }
        return list;
    }

}
