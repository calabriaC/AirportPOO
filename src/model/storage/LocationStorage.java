/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;

import Controller.LocationController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import model.Location;

/**
 *
 * @author Car
 */
public class LocationStorage {
    
     //almacenamiento general del sistema para guardar objetos de tipo Location
    private static ArrayList <Location> locations = new ArrayList<>();
    LocationController lc = new LocationController();

    public LocationStorage() throws IOException {
       //llenando la lista con el metodo para leer archivos json
       locations = readLocations("json/locations.json");
       //ordenanlo la lista por ids
       locations.sort(Comparator.comparing(Location::getAirportId));
    }
    
    public ArrayList <Location> getLocations() {
        return locations;
    }

    private ArrayList<Location> readLocations(String jsonlocationsjson) {
        return null;
    }
    
}
