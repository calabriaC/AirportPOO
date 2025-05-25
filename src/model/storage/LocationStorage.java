/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;

import java.util.ArrayList;
import java.util.Comparator;
import model.Location;

/**
 *
 * @author Car
 */
public class LocationStorage {

    private ArrayList<Location> locations;

    public LocationStorage(ArrayList<Location> locations) {
        this.locations = locations;
        this.locations.sort(Comparator.comparing(Location::getId));
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public Location findById(String id) {
        for (Location loc : locations) {
            if (loc.getId().equals(id)) {
                return loc;
            }
        }
        return null;
    }

    public boolean idExists(String id) {
        return locations.stream().anyMatch(l -> l.getId().equals(id));
    }
}

    

