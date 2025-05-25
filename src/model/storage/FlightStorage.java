/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;



import java.util.ArrayList;
import java.util.Comparator;
import model.Flight;



/**
 *
 * @author Car
 */
public class FlightStorage {

    private ArrayList<Flight> flights;

    public FlightStorage(ArrayList<Flight> flights) {
        this.flights = flights;
        this.flights.sort(Comparator.comparing(Flight::getId)); // Asegúrate de tener getId() en Flight
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Flight findById(String id) {
        for (Flight f : flights) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public boolean idExists(String id) {
        return flights.stream().anyMatch(f -> f.getId().equals(id));
    }

}



