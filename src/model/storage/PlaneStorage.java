/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import model.Plane;

/**
 *
 * @author Car
 */
public class PlaneStorage {
    
     //almacenamiento general del sistema para guardar objetos de tipo Plane
    private static ArrayList <Plane> planes = new ArrayList<>();

    public PlaneStorage() throws IOException {
        //llenando la lista con el metodo para leer archivos json
        planes = readPlanes("json/planes.json");
        //ordenanlo la lista por ids
        planes.sort(Comparator.comparing(Plane::getId));
    }
        
    public ArrayList <Plane> getPlanes() {
        return planes;
    }

    private ArrayList<Plane> readPlanes(String jsonplanesjson) {
        return null;
    }
    
}
