/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.storage;

import java.util.ArrayList;
import java.util.Comparator;
import model.Passenger;

/**
 *
 * @author Car
 */
public class PassengerStorage {

    private ArrayList<Passenger> passengers;

    public PassengerStorage(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
        this.passengers.sort(Comparator.comparing(Passenger::getId));
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        passengers.sort(Comparator.comparing(Passenger::getId));
    }

    public Passenger findById(long id) {
        for (Passenger p : passengers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean idExists(long id) {
        return passengers.stream().anyMatch(p -> p.getId() == id);
    }
}
