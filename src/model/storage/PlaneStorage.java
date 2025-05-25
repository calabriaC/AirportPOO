/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;



import java.util.ArrayList;
import java.util.Comparator;
import model.Plane;

/**
 *
 * @author Car
 */
public class PlaneStorage {

    private ArrayList<Plane> planes;

    public PlaneStorage(ArrayList<Plane> planes) {
        this.planes = planes;
        this.planes.sort(Comparator.comparing(Plane::getId));
    }

    public ArrayList<Plane> getPlanes() {
        return planes;
    }

    public Plane findById(String id) {
        for (Plane p : planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean idExists(String id) {
        return planes.stream().anyMatch(p -> p.getId().equals(id));
    }
}


